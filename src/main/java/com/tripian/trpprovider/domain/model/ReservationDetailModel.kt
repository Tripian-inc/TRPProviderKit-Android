package com.tripian.trpprovider.domain.model

/**
 * Created by semihozkoroglu on 7.08.2020.
 */
class ReservationDetailModel : BaseModel() {
    var reservationId: String? = null
    var confirmationUrl: String? = null
    var reservationStatus: ReservationStatusModel? = null
    var notes: String? = null
}