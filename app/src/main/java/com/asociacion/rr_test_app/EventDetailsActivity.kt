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
            openUrl.data = Uri.parse(evt.routeUrl)
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

        markers = evt.locations!!
    }
}
