package com.asociacion.rr_test_app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class EventDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_details_activity)

        val btnId = intent.getIntExtra("BTN_ID",0)

        var eventTxt: TextView = findViewById(R.id.EventTxt)
        var descTxt: TextView = findViewById(R.id.DescTxt)
        var dateTxt: TextView = findViewById(R.id.DateTxt)
        var timeTxt: TextView = findViewById(R.id.TimeTxt)
        var locTxt: TextView = findViewById(R.id.LocationTxt)


        eventTxt.text = "Button pressed: $btnId"
    }
}
