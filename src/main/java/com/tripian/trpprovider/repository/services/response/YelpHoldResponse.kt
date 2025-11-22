package com.tripian.trpprovider.repository.services.response

import com.google.gson.annotations.SerializedName
import com.tripian.trpprovider.repository.base.ResponseModelBase

/**
 * Created by semihozkoroglu on 5.08.2020.
 */
class YelpHoldResponse : ResponseModelBase() {

    @SerializedName("cancellation_policy")
    var cancellationPolicy: String? = null

    @SerializedName("hold_id")
    var holdId: String? = null

    @SerializedName("notes")
    var notes: String? = null

    @SerializedName("reserve_url")
    var reserveUrl: String? = null

    @SerializedName("credit_card_hold")
    var creditCardHold: Boolean? = null

    @SerializedName("is_editable")
    var isEditable: Boolean? = null
}