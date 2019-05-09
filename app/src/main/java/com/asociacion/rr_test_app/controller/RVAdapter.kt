package com.asociacion.rr_test_app.controller

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.asociacion.rr_test_app.R
import com.asociacion.rr_test_app.model.Event
import kotlinx.android.synthetic.main.main_recycler_view_layout.view.*

class RVAdapter(val items: List<Event>, val clickListener: (Event) -> Unit) : Adapter<RVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.main_recycler_view_layout, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        println("Item count = ${items.size.toString()}")
        return items.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bind(items[p1], clickListener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(event: Event, clickListener: (Event) -> Unit) {
            itemView.event_name_txt.text = event.name
            itemView.event_datetime_txt.text = event.dateTime
            ImageUtils(itemView.card_image_btn).execute(event.imgUrl)
            itemView.setOnClickListener { clickListener(event) }
        }
    }
}