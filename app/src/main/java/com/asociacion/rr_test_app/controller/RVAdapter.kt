package com.asociacion.rr_test_app.controller

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.asociacion.rr_test_app.R
import com.asociacion.rr_test_app.model.Event

class RVAdapter(val items: List<Event>) : Adapter<RVAdapter.ViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.main_recycler_view_layout, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val event:Event = items[p1]

        p0.textViewName.text = event.name
        p0.textViewDateTime.text = event.dateTime
        ImageUtils(p0.cardImage).execute(event.imgUrl)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val textViewName = itemView.findViewById(R.id.event_name_txt) as TextView
        val textViewDateTime = itemView.findViewById(R.id.event_datetime_txt) as TextView
        val cardImage = itemView.findViewById(R.id.card_image_btn)as ImageButton
    }
}