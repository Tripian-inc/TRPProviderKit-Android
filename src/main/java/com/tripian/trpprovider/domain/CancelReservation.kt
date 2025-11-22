package com.tripian.trpprovider.domain

import com.tripian.trpprovider.base.BaseUseCase
import com.tripian.trpprovider.repository.services.Service
import com.tripian.trpprovider.repository.services.response.YelpCancelResponse
import com.tripian.trpprovider.repository.services.response.YelpHoldResponse
import javax.inject.Inject

/**
 * Created by semihozkoroglu on 5.08.2020.
 */
class CancelReservation @Inject constructor(val service: Service) : BaseUseCase<YelpCancelResponse, CancelReservation.Params>() {

    class Params(val reservationId: String)

    override fun on(params: Params?) {
        sendRequest {
            service.cancel(params!!.reservationId)
        }
    }
}