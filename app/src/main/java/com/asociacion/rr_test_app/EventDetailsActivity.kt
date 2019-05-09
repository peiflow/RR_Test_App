package com.asociacion.rr_test_app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import com.asociacion.rr_test_app.model.Event
import com.asociacion.rr_test_app.model.LatLng
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import java.text.SimpleDateFormat
import java.util.*

class EventDetailsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var markers: MutableList<LatLng>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_details_activity)

        val mapFragment: SupportMapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment1) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val opnMapsBtn: Button = findViewById(R.id.open_maps_button)

        val evt = intent.getExtras().getSerializable("Event") as Event
        processMapInfo(evt)

        opnMapsBtn.setOnClickListener{
            val openUrl = Intent(Intent.ACTION_VIEW)
            openUrl.data = Uri.parse("https://www.google.es/maps/dir/Toro+(Zamora),+Toro/47530+San+Román+de+Hornija,+Valladolid/Castronuño/La+Bóveda+de+Toro/Venialbo/@41.433834,-5.5421648,11z/data=!3m1!4b1!4m32!4m31!1m5!1m1!1s0xd38959c132a704f:0xa2b7a1b491bfb470!2m2!1d-5.3913375!2d41.5274417!1m5!1m1!1s0xd389620bacd852d:0x797ca0d3abb920fc!2m2!1d-5.2847709!2d41.480658!1m5!1m1!1s0xd38bb76f5686111:0x9bf614a77db387a6!2m2!1d-5.2671378!2d41.387238!1m5!1m1!1s0xd38c79765e54131:0xd2d704f21b666fe2!2m2!1d-5.4109725!2d41.3430855!1m5!1m1!1s0xd38c361b0eaff17:0x789cd4ef0164bb81!2m2!1d-5.5367936!2d41.3895764!3e0")
            startActivity(openUrl)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        var lats = 0.0
        var lons = 0.0

        mMap = googleMap
        for (i in 0 until markers.size) {
            var title: String = ""
            if (i == 0) {
                title = "Inicio"

            } else if (i == markers.size-1) {
                title = "Fin"
            }
            mMap.addMarker(
                MarkerOptions().position(
                    com.google.android.gms.maps.model.LatLng(
                        markers[i].lat,
                        markers[i].lng
                    )
                ).title(title)
            )

            lats += markers[i].lat
            lons += markers[i].lng

        }
        if (markers.size > 1) {
            var lat: Double = lats / markers.size
            var lon: Double = lons / markers.size

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(com.google.android.gms.maps.model.LatLng(lat, lon), 10f))
        } else {
            mMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    com.google.android.gms.maps.model.LatLng(
                        markers[0].lat,
                        markers[0].lng
                    ), 15f
                )
            )
        }
        val mapSettings = mMap.uiSettings
        mapSettings.setAllGesturesEnabled(true)
    }

    private fun processMapInfo(evt:Event)
    {
        val eventTxt: TextView = findViewById(R.id.EventTxt)
        val descTxt: TextView = findViewById(R.id.DescTxt)
        val dateTxt: TextView = findViewById(R.id.DateTxt)
        val timeTxt: TextView = findViewById(R.id.TimeTxt)
        val locTxt: TextView = findViewById(R.id.LocationTxt)

        val fmt = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
        val date: Date = fmt.parse(evt.dateTime)
        val fmtOut = SimpleDateFormat("dd/MM/yyyy")
        val frmtDate: String = fmtOut.format(date)
        val timeOut = SimpleDateFormat("hh:mm:ss")
        val frmtTime: String = timeOut.format(date)

        eventTxt.text = evt.name
        descTxt.text = evt.description
        dateTxt.text = frmtDate
        timeTxt.text = frmtTime
        locTxt.text = evt.address

        markers = evt.location!!
    }
}
