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
        val tv1:TextView = root.findViewById(R.id.tvTest1)
        //observing livedata
        mainViewModel.p24Rates.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS ->{
                    tv1.text = it.data?.exchangeRate?.size.toString()
                }
                Status.ERROR ->{
                    tv1.text = it.message.toString()
                }
            }
        })
        return root
    }


}