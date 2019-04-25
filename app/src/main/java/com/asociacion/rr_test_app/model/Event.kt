package com.asociacion.rr_test_app.model

import java.io.Serializable

class Event : Serializable
{
    var uuid: String? = null
    var name: String? = null
    var description: String? = null
    var location: String? = null
    var  dateTime: String? = null

    constructor(){}

    constructor(uuid: String, name: String, description:String, location:String, dateTime:String){
        this.uuid = uuid
        this.name = name
        this.description = description
        this.location= location
        this.dateTime= dateTime
    }
}
