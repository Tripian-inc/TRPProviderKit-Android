package com.tripian.trpprovider.domain.model

import com.google.gson.annotations.SerializedName

/**
 * Created by semihozkoroglu on 7.08.2020.
 */
class TimesModel : BaseModel() {
    @SerializedName("credit_card_required")
    var cardRequired: String? = null
    var time: String? = null
}