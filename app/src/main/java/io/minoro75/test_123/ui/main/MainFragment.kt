package io.minoro75.test_123.ui.main

import android.icu.text.SimpleDateFormat
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.progressindicator.LinearProgressIndicator
import dagger.hilt.android.AndroidEntryPoint
import io.minoro75.test_123.R
import io.minoro75.test_123.ui.data.entities.NbuResponse
import io.minoro75.test_123.ui.utils.Status
import java.util.*

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val mainViewModel : MainViewModel by viewModels()
    private lateinit var adapter: MainAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val root = inflater.inflate(R.layout.main_fragment, container, false)
        val calendarButton = root.findViewById<ImageButton>(R.id.ib_calendar)
        val recyclerView = root.findViewById<RecyclerView>(R.id.rv_nbu)
        val nbuProgressIndicator = root.findViewById<LinearProgressIndicator>(R.id.nbu_progress_indicator)
        val p24ProgressIndicator = root.findViewById<LinearProgressIndicator>(R.id.p24_progress_indicator)
        //constraint layouts in p24 container, used only to hide\show on loads
        val cl_p24_1 = root.findViewById<ConstraintLayout>(R.id.cl_p24_1)
        val cl_p24_2 = root.findViewById<ConstraintLayout>(R.id.cl_p24_2)
        val cl_p24_3 = root.findViewById<ConstraintLayout>(R.id.cl_p24_3)
        val cl_p24_4 = root.findViewById<ConstraintLayout>(R.id.cl_p24_4)


        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = MainAdapter(nbuResponse = NbuResponse())
        recyclerView.adapter = adapter


        calendarButton.setOnClickListener {
            mainViewModel.picker.show(parentFragmentManager,"tag")
            mainViewModel.datePickerShow()

            mainViewModel.picker.addOnPositiveButtonClickListener {
                recyclerView.visibility=View.GONE
                nbuProgressIndicator.visibility = View.VISIBLE
                cl_p24_1.visibility = View.GONE
                cl_p24_2.visibility = View.GONE
                cl_p24_3.visibility = View.GONE
                cl_p24_4.visibility = View.GONE
                p24ProgressIndicator.visibility = View.VISIBLE
                // show progress indicators in both windows and fetch rates
                mainViewModel.fetchRates()
            }
        }



        //observing livedata
        mainViewModel.p24Rates.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS ->{
                    //hide progress indicator and show all layouts
                    p24ProgressIndicator.visibility = View.GONE
                    cl_p24_1.visibility = View.VISIBLE
                    cl_p24_2.visibility = View.VISIBLE
                    cl_p24_3.visibility = View.VISIBLE
                    cl_p24_4.visibility = View.VISIBLE
                    //set rates in their fields
                    root.findViewById<TextView>(R.id.tv_cur_1).text = it.data!!.exchangeRate[8].currency
                    root.findViewById<TextView>(R.id.tv_buy_1).text = it.data.exchangeRate[8].purchaseRate.toString()
                    root.findViewById<TextView>(R.id.tv_sell_1).text = it.data.exchangeRate[8].saleRate.toString()

                    root.findViewById<TextView>(R.id.tv_cur_2).text = it.data.exchangeRate[17].currency
                    root.findViewById<TextView>(R.id.tv_buy_2).text = it.data.exchangeRate[17].purchaseRate.toString()
                    root.findViewById<TextView>(R.id.tv_sell_2).text = it.data.exchangeRate[17].saleRate.toString()


                    root.findViewById<TextView>(R.id.tv_cur_3).text = it.data.exchangeRate[23].currency
                    root.findViewById<TextView>(R.id.tv_buy_3).text = it.data.exchangeRate[23].purchaseRate.toString()
                    root.findViewById<TextView>(R.id.tv_sell_3).text = it.data.exchangeRate[23].saleRate.toString()

                }
                Status.ERROR ->{
                }
                Status.LOADING ->{
                    cl_p24_1.visibility = View.GONE
                    cl_p24_2.visibility = View.GONE
                    cl_p24_3.visibility = View.GONE
                    cl_p24_4.visibility = View.GONE
                    p24ProgressIndicator.visibility = View.VISIBLE
                }
            }
        })

        mainViewModel.nbuRates.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS ->{

                    it.data?.let { news ->
                        renderList(news)
                        nbuProgressIndicator.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                    }
                }
                Status.LOADING -> {
                    recyclerView.visibility=View.GONE
                    nbuProgressIndicator.visibility = View.VISIBLE

                }
            }
        })

        mainViewModel.dateNbu.observe(viewLifecycleOwner, Observer {
            mainViewModel.fetchRates()
        })
        return root


    }



    private fun renderList(nbuResponse: NbuResponse){
        adapter.addItems(nbuResponse)
        adapter.notifyDataSetChanged()
    }




}