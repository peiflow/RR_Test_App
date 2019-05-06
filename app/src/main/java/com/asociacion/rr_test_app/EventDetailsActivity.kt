package com.asociacion.rr_test_app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.asociacion.rr_test_app.model.Event
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.text.SimpleDateFormat
import java.util.*

class EventDetailsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private val LOCATION_REQUEST_CODE = 101
    private lateinit var marker:LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_details_activity)

        val mapFragment:SupportMapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment1) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val evt = intent.getExtras().getSerializable("Event")as Event

        val eventTxt: TextView = findViewById(R.id.EventTxt)
        val descTxt: TextView = findViewById(R.id.DescTxt)
        val dateTxt: TextView = findViewById(R.id.DateTxt)
        val timeTxt: TextView = findViewById(R.id.TimeTxt)
        val locTxt: TextView = findViewById(R.id.LocationTxt)


        val fmt= SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
        val date:Date = fmt.parse(evt.dateTime)
        val fmtOut = SimpleDateFormat("dd/MM/yyyy")
        val frmtDate:String = fmtOut.format(date)
        val timeOut= SimpleDateFormat("hh:mm:ss")
        val frmtTime:String = timeOut.format(date)

        eventTxt.text = evt.name
        descTxt.text = evt.description
        dateTxt.text = frmtDate
        timeTxt.text = frmtTime
        locTxt.text = evt.address

        var spl = evt.location!!.split(",")

        var lat:Double = spl[0].toDouble()
        var lon:Double = spl[1].toDouble()

        marker = LatLng(lat, lon)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.addMarker(MarkerOptions().position(this.marker).title("Marker in Toro"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(this.marker, 15f))

        val mapSettings = mMap.uiSettings
        mapSettings.setAllGesturesEnabled(true)
    }
}
