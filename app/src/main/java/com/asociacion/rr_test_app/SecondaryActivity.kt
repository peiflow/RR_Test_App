package com.asociacion.rr_test_app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SecondaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secondary)

        val btnId = intent.getIntExtra("BTN_ID",0)

        //var txt:TextView = findViewById(R.id.SecActTxt)
        //txt.text = "Button pressed: $btnId"
    }
}
