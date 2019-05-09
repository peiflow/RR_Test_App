package com.asociacion.rr_test_app

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import android.widget.Toast
import com.asociacion.rr_test_app.controller.RVAdapter
import com.asociacion.rr_test_app.model.Event

import kotlinx.android.synthetic.main.activity_recycler_view.*
import kotlinx.android.synthetic.main.content_recycler_view.*

class RecyclerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        setSupportActionBar(toolbar)

        var events:ArrayList<Event> = intent.extras.getSerializable("Events")as ArrayList<Event>

        var recyclerView:RecyclerView = findViewById(R.id.content_recycler_view_test)
        val linearLayout = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        val adapter = RVAdapter(events.toList()) { event: Event -> eventItemClicked(event)}

        recyclerView.layoutManager =linearLayout
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
    }

    private fun eventItemClicked(eventItem: Event)
    {
        Toast.makeText(this, "Click! $eventItem.name$", Toast.LENGTH_LONG).show()
    }
}
