package com.asociacion.rr_test_app

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.asociacion.rr_test_app.controller.RVAdapter
import com.asociacion.rr_test_app.model.Event

import kotlinx.android.synthetic.main.activity_recycler_view.*

class RecyclerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        setSupportActionBar(toolbar)

        val rv: RecyclerView = findViewById(R.id.content_recycler_view_test)
        rv.setHasFixedSize(true)
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        var events:ArrayList<Event> = intent.extras.getSerializable("Events")as ArrayList<Event>

        val adapter = RVAdapter(events.toList())
        rv.adapter = adapter
    }
}
