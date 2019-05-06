package com.asociacion.rr_test_app.controller

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.asociacion.rr_test_app.model.Event
import com.google.firebase.database.*
import com.google.firebase.database.DataSnapshot


class DatabaseManager {

    var dbRef: DatabaseReference

    constructor() {
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        dbRef = FirebaseDatabase.getInstance().getReference("events")
        //dbRef.onDisconnect().setValue("Disconected")
    }

    fun ReadDatabase(context: Context, eventsList:MutableList<Event>) {
        val eventsListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.e(TAG, "onCancelled: Failed to read message")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.value != "Disconected") {
                    for (childSnapshot in dataSnapshot.children) {
                        val evt = childSnapshot.getValue(Event::class.java)
                        if(evt!=null)
                        {
                            eventsList.add(evt)
                        }
                    }
                }
            }
        }
        dbRef!!.addValueEventListener(eventsListener)
    }

    fun PopEventCollection(context: Context) {
        dbRef.onDisconnect().setValue("")
        var key = dbRef.push().key
        val event: Event
        if (key != null) {
            event = Event(
                key,
                "Ruta sabado",
                "Ruta del sábado por la mañana a Tiedra",
                "41.521052, -5.393818",
                "Plaza mayor",
                "27/07/2019 11:00:00"
            )
            dbRef.child(key).setValue(event)
            Toast.makeText(context, "Event saved in db", Toast.LENGTH_LONG).show()
        }

        key = dbRef.push().key
        val event2: Event
        if (key != null) {
            event2 = Event(
                key,
                "Exhibición trial",
                "Exhibición de trial",
                "41.527033, -5.397596",
                "N-122",
                "27/07/2019 19:00:00"
            )
            dbRef.child(key).setValue(event2)
            Toast.makeText(context, "DB reset", Toast.LENGTH_LONG).show()
        }
    }
}