package com.asociacion.rr_test_app.model

import java.io.Serializable

class Event : Serializable
{
    var uuid: String? = null
    var name: String? = null
    var description: String? = null
    var locations: MutableList<LatLng>? = null
    var routeUrl: String? = null
    var address: String? = null
    var dateTime: String? = null
    var imgUrl: String? = null

    constructor(){}

    constructor(uuid: String, name: String, description:String, locations:MutableList<LatLng>, routeUrl:String, address:String, dateTime:String, imgUrl:String){
        this.uuid        = uuid
        this.name        = name
        this.description = description
        this.locations   = locations
        this.routeUrl    = routeUrl
        this.address     = address
        this.dateTime    = dateTime
        this.imgUrl      = imgUrl
    }
}
