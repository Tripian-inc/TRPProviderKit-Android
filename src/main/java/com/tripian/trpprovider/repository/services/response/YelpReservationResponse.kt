package com.tripian.trpprovider.repository.services.response

import com.google.gson.annotations.SerializedName
import com.tripian.trpprovider.repository.base.ResponseModelBase

/**
 * Created by semihozkoroglu on 5.08.2020.
 */
class YelpReservationResponse : ResponseModelBase() {

    @SerializedName("confirmation_url")
    var confirmationUrl: String? = null

    @SerializedName("reservation_id")
    var reservationId: String? = null

    @SerializedName("notes")
    var notes: String? = null
}