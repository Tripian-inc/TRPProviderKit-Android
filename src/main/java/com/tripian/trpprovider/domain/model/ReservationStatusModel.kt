package com.tripian.trpprovider.domain.model

/**
 * Created by semihozkoroglu on 7.08.2020.
 */
class ReservationStatusModel : BaseModel() {
    var covers: Int? = null
    var active = false
    var time: String? = null
    var date: String? = null
}