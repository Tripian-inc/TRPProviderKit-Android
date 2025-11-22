package com.tripian.trpprovider.domain

import android.text.TextUtils
import com.tripian.trpprovider.base.BaseUseCase
import com.tripian.trpprovider.domain.model.OpeningHourModel
import com.tripian.trpprovider.repository.services.Service
import com.tripian.trpprovider.util.extensions.getDate
import javax.inject.Inject

/**
 * Created by semihozkoroglu on 5.08.2020.
 */
class GetOpeningHours @Inject constructor(val service: Service) : BaseUseCase<List<String>, GetOpeningHours.Params>() {

    var reservationTimes: List<OpeningHourModel>? = null
    val times = ArrayList<String>()

    class Params(val businessId: String, val covers: String, val date: Long, val time: String)

    override fun on(params: Params?) {
        val date = getDate(params!!.date)

        parseTimes(date)

        if (times.size == 0) {
            sendRequest {
                service.getOpeningHours(params.businessId, params.covers, date, params.time).map {
                    reservationTimes = it.reservationTimes

                    parseTimes(date)

                    return@map times
                }
            }
        } else {
            parseTimes(date)

            onSendSuccess(times)
        }
    }

    private fun parseTimes(date: String) {
        times.clear()

        if (reservationTimes != null && reservationTimes!!.isNotEmpty()) {
            reservationTimes?.forEach {
                if (TextUtils.equals(date, it.date)) {
                    it.times?.forEach { t ->
                        if (t.time != null) {
                            times.add(t.time!!)
                        }
                    }
                }
            }
        }
    }
}