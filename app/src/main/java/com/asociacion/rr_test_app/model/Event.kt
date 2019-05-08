package com.asociacion.rr_test_app.model

import java.io.Serializable

class Event : Serializable
{
    var uuid: String? = null
    var name: String? = null
    var description: String? = null
    var location: String? = null
    var address: String? = null
    var dateTime: String? = null
    var imgUrl: String? = null

    constructor(){}

    constructor(uuid: String, name: String, description:String, location:String, address:String, dateTime:String, imgUrl:String){
        this.uuid        = uuid
        this.name        = name
        this.description = description
        this.location    = location
        this.address     = address
        this.dateTime    = dateTime
        this.imgUrl      = imgUrl
    }
}
