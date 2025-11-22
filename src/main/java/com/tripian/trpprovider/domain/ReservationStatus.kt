package com.tripian.trpprovider.domain

import com.tripian.trpprovider.base.BaseUseCase
import com.tripian.trpprovider.repository.services.Service
import com.tripian.trpprovider.repository.services.response.YelpStatusResponse
import javax.inject.Inject

/**
 * Created by semihozkoroglu on 5.08.2020.
 */
class ReservationStatus @Inject constructor(val service: Service) : BaseUseCase<YelpStatusResponse, ReservationStatus.Params>() {

    class Params(val reservationId: String)

    override fun on(params: Params?) {
        sendRequest {
            service.status(params!!.reservationId)
        }
    }
}