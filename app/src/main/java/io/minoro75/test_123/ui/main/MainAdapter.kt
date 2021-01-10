package io.minoro75.test_123.ui.main

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.minoro75.test_123.R
import io.minoro75.test_123.ui.data.entities.NbuResponse
import io.minoro75.test_123.ui.data.entities.NbuResponseItem

class MainAdapter(
	private val nbuResponse: NbuResponse
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

	var selectedPosition = 33

	class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

		fun bind(nbuResponseItem: NbuResponseItem) {
			itemView.findViewById<TextView>(R.id.tv_nbu_currency_code).text =
				nbuResponseItem.cc
			itemView.findViewById<TextView>(R.id.tv_nbu_currency_val).text =
				nbuResponseItem.rate.toString()
			itemView.findViewById<TextView>(R.id.tv_nbu_currency_name).text =
				nbuResponseItem.txt
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder =
		MainViewHolder(
			LayoutInflater.from(parent.context).inflate(
				R.layout.rv_item, parent, false
			)
		)

	override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

		if (selectedPosition == position) {
			holder.itemView.setBackgroundColor(Color.parseColor("#b2ebf2"))
		} else {
			holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"))
		}
		holder.bind(nbuResponseItem = nbuResponse[position])
	}

	override fun getItemCount(): Int =
		nbuResponse.size

	fun addItems(list: NbuResponse) {
		nbuResponse.addAll(list)
	}

	fun clearAll() {
		nbuResponse.clear()
	}


}