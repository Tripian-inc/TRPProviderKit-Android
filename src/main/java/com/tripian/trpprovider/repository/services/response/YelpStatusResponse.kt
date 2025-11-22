package com.tripian.trpprovider.repository.services.response

import com.google.gson.annotations.SerializedName
import com.tripian.trpprovider.repository.base.ResponseModelBase

/**
 * Created by semihozkoroglu on 5.08.2020.
 */
class YelpStatusResponse : ResponseModelBase() {

    @SerializedName("covers")
    var covers: Int? = null

    @SerializedName("time")
    var time: String? = null

    @SerializedName("date")
    var date: String? = null

    @SerializedName("active")
    var active: Boolean? = null

}