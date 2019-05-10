package com.asociacion.rr_test_app

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Toast
import com.asociacion.rr_test_app.controller.DatabaseManager
import com.asociacion.rr_test_app.model.Event
import com.google.android.gms.vision.barcode.Barcode

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var dbm: DatabaseManager
    lateinit var eventsList: MutableList<Event>
    var REQUEST_CODE: Int = 100
    var PERMISSION_REQUEST: Int = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        SetOnClickListeners()

        CheckPermissions()

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        dbm = DatabaseManager()
        eventsList = mutableListOf()
    }

    override fun onStart() {
        super.onStart()
        dbm.ReadDatabase(this, eventsList)
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
                var intent = Intent(this, ScanActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE)
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_tools -> {
                dbm.PopEventCollection(this)
            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val barcode: Barcode = data.getParcelableExtra("barcode")
                Toast.makeText(this, barcode.displayValue, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun SetOnClickListeners() {
        val imgBtn1: ImageButton = findViewById(R.id.imbBtn1)
        imgBtn1.setOnClickListener {
            if (eventsList.size >= 1)
                LoadIntent(eventsList[0])
        }
        val imgBtn2: ImageButton = findViewById(R.id.imbBtn2)
        imgBtn2.setOnClickListener {
            if (eventsList.size >= 2)
                LoadIntent(eventsList[1])
        }
        val imgBtn3: ImageButton = findViewById(R.id.imbBtn3)
        imgBtn3.setOnClickListener {
            if (eventsList.size >= 3)
                LoadIntent(eventsList[2])
        }
        val imgBtn4: ImageButton = findViewById(R.id.imbBtn4)
        imgBtn4.setOnClickListener {
            if (eventsList.size >= 4)
                LoadIntent(eventsList[3])
        }
        val imgBtn5: ImageButton = findViewById(R.id.imbBtn5)
        imgBtn5.setOnClickListener {
            //if (eventsList.size >= 5)
              //  LoadIntent(eventsList[4])
            var intent = Intent(this, RecyclerViewActivity::class.java)
            intent.putExtra("Events", ArrayList(eventsList))
            startActivity(intent)
        }
    }

    fun LoadIntent(evt: Event) {
        var intent = Intent(this, EventDetailsActivity::class.java)
        intent.putExtra("Event", evt)
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun CheckPermissions() {
        val camPerm: Int = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        val gpsPerm: Int = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
        val readStoragePerm: Int = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
        val writeStoragePerm: Int = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val internetPerm: Int = ContextCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET)

        val permissions: Array<String> = arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.INTERNET
        )


        if (camPerm != PackageManager.PERMISSION_GRANTED
            || gpsPerm != PackageManager.PERMISSION_GRANTED
            || readStoragePerm != PackageManager.PERMISSION_GRANTED
            || writeStoragePerm != PackageManager.PERMISSION_GRANTED
            || internetPerm != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST)
        }
    }
}
