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
import dagger.hilt.android.AndroidEntryPoint
import io.minoro75.test_123.R
import io.minoro75.test_123.ui.utils.Status

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val mainViewModel : MainViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val root = inflater.inflate(R.layout.main_fragment, container, false)
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


                    root.findViewById<TextView>(R.id.tv_cur_4).text = it.data.exchangeRate[9].currency
                    root.findViewById<TextView>(R.id.tv_buy_4).text = it.data.exchangeRate[9].purchaseRate.toString()
                    root.findViewById<TextView>(R.id.tv_sell_4).text = it.data.exchangeRate[9].saleRate.toString()
                }
                Status.ERROR ->{
                }
            }
        })
        return root
    }


}