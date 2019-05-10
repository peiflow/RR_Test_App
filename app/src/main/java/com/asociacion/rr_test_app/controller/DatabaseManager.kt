package com.asociacion.rr_test_app.controller

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.asociacion.rr_test_app.model.Event
import com.asociacion.rr_test_app.model.LatLng
import com.google.firebase.database.*
import com.google.firebase.database.DataSnapshot


class DatabaseManager {

    var dbRef: DatabaseReference

    constructor() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(false)
        dbRef = FirebaseDatabase.getInstance().getReference("events")
    }

    fun ReadDatabase(context: Context, eventsList: MutableList<Event>) {
        eventsList.clear()
        val eventsListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.e(TAG, "onCancelled: Failed to read message")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.value != "Disconected") {
                    for (childSnapshot in dataSnapshot.children) {
                        val evt = childSnapshot.getValue(Event::class.java)
                        if (evt != null) {
                            eventsList.add(evt)
                        }
                    }
                }
            }
        }
        dbRef!!.addValueEventListener(eventsListener)
    }

    fun PopEventCollection(context: Context) {

        dbRef.setValue(null)
        var key = dbRef.push().key
        val event: Event
        if (key != null) {

            /*event = Event(
                key,
                "Ruta sabado",
                "Ruta del sábado por la mañana a Tiedra",
                "41.521052, -5.393818",
                "Plaza mayor",
                "27/07/2019 11:00:00",
                "https://image.shutterstock.com/image-vector/colorful-seamless-geometric-pattern-450w-161998277.jpg"
            )*/

            event = Event()
            event.uuid = key
            event.name = "Ruta sabado"
            event.description = "Ruta del sábado por la mañana a Tiedra"
            event.address =  "Plaza mayor"
            event.locations = mutableListOf(LatLng(41.521052,-5.393818),LatLng(41.650273,-5.269575))
            event.routeUrl = "https://www.google.es/maps/dir/Toro+(Zamora),+Toro/47530+San+Román+de+Hornija,+Valladolid/Castronuño/La+Bóveda+de+Toro/Venialbo/@41.433834,-5.5421648,11z/data=!3m1!4b1!4m32!4m31!1m5!1m1!1s0xd38959c132a704f:0xa2b7a1b491bfb470!2m2!1d-5.3913375!2d41.5274417!1m5!1m1!1s0xd389620bacd852d:0x797ca0d3abb920fc!2m2!1d-5.2847709!2d41.480658!1m5!1m1!1s0xd38bb76f5686111:0x9bf614a77db387a6!2m2!1d-5.2671378!2d41.387238!1m5!1m1!1s0xd38c79765e54131:0xd2d704f21b666fe2!2m2!1d-5.4109725!2d41.3430855!1m5!1m1!1s0xd38c361b0eaff17:0x789cd4ef0164bb81!2m2!1d-5.5367936!2d41.3895764!3e0"
            event.dateTime = "27/07/2019 11:00:00"
            event.imgUrl = "https://image.shutterstock.com/image-vector/colorful-seamless-geometric-pattern-450w-161998277.jpg"


            dbRef.child(key).setValue(event)
        }

        key = dbRef.push().key
        val event2: Event
        if (key != null) {
            event2 = Event(
                key,
                "Exhibición trial",
                "Exhibición de trial",
                mutableListOf(LatLng(41.527033, -5.397596)),
                "https://www.google.es/maps/place/41%C2%B031'37.4%22N+5%C2%B023'51.5%22W/@41.527049,-5.3981752,19z/data=!3m1!4b1!4m14!1m7!3m6!1s0xd38eb2cca7804b5:0x8c74b4293c1af881!2sPlaza+Mayor,+49800+Toro,+Zamora!3b1!8m2!3d41.5212425!4d-5.3942007!3m5!1s0x0:0x0!7e2!8m2!3d41.5270484!4d-5.3976278",
                "N-122",
                "27/07/2019 19:00:00",
                "https://image.shutterstock.com/image-vector/pixel-monsters-cartoon-vector-pattern-450w-584585053.jpg"
            )

            dbRef.child(key).setValue(event2)
        }

            key = dbRef.push().key
            val event3: Event
            if (key != null) {
                event3= Event(
                    key,
                    "Concierto Xeria",
                    "Concierto de Xeria en la plaza mayor",
                    mutableListOf(LatLng(41.521052, -5.393818)),
                    "https://www.google.es/maps/place/Plaza+Mayor,+49800+Toro,+Zamora/@41.5212449,-5.3955021,17z/data=!3m1!4b1!4m5!3m4!1s0xd38eb2cca7804b5:0x8c74b4293c1af881!8m2!3d41.5212425!4d-5.3942007",
                    "Plaza Mayor",
                    "27/07/2019 00:00:00",
                    "https://image.shutterstock.com/image-vector/colorful-seamless-geometric-pattern-450w-161998277.jpg"
                )
                dbRef.child(key).setValue(event3)

                Toast.makeText(context, "DB reset", Toast.LENGTH_LONG).show()
            }
        }
    }