package io.minoro75.test_123.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import io.minoro75.test_123.R
import io.minoro75.test_123.ui.data.entities.NbuResponse
import io.minoro75.test_123.ui.utils.Status

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val mainViewModel : MainViewModel by viewModels()
    private lateinit var adapter: MainAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val root = inflater.inflate(R.layout.main_fragment, container, false)

        val recyclerView = root.findViewById<RecyclerView>(R.id.rv_nbu)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = MainAdapter(nbuResponse = NbuResponse())
        recyclerView.adapter = adapter


        //observing livedata
        mainViewModel.p24Rates.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS ->{
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
            }
        })

        mainViewModel.nbuRates.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS ->{
                    it.data?.let { news ->
                        renderList(news)
                    }
                }
            }
        })

        return root
    }

    private fun renderList(nbuResponse: NbuResponse){
        adapter.addItems(nbuResponse)
        adapter.notifyDataSetChanged()
    }


}