package com.tripian.trpprovider.repository.services.response

import com.google.gson.annotations.SerializedName
import com.tripian.trpprovider.domain.model.OpeningHourModel
import com.tripian.trpprovider.repository.base.ResponseModelBase

/**
 * Created by semihozkoroglu on 5.08.2020.
 */
class YelpOpeningHoursResponse : ResponseModelBase() {

    @SerializedName("reservation_times")
    var reservationTimes: List<OpeningHourModel>? = null
}