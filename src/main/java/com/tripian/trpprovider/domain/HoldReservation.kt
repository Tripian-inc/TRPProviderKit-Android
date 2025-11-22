package com.tripian.trpprovider.domain

import android.app.Application
import android.text.TextUtils
import com.tripian.trpprovider.R
import com.tripian.trpprovider.base.BaseUseCase
import com.tripian.trpprovider.repository.base.ErrorModel
import com.tripian.trpprovider.repository.services.Service
import com.tripian.trpprovider.repository.services.response.YelpHoldResponse
import com.tripian.trpprovider.util.extensions.getDate
import com.tripian.trpprovider.util.extensions.getUniqueId
import javax.inject.Inject

/**
 * Created by semihozkoroglu on 5.08.2020.
 */
class HoldReservation @Inject constructor(val service: Service, val app: Application) : BaseUseCase<YelpHoldResponse, HoldReservation.Params>() {

    class Params(val businessId: String, val date: Long, val covers: String, val time: String)

    override fun on(params: Params?) {
        when {
            TextUtils.isEmpty(params!!.covers) -> {
                onSendError(ErrorModel(strings.getString(R.string.please_select_cover)))
            }
            params.covers.toInt() < 1 -> {
                onSendError(ErrorModel(strings.getString(R.string.please_enter_valid_cover)))
            }
            params.date == -1L -> {
                onSendError(ErrorModel(strings.getString(R.string.please_select_date)))
            }
            TextUtils.isEmpty(params.time) -> {
                onSendError(ErrorModel(strings.getString(R.string.please_select_time)))
            }
            else -> {
                sendRequest {
                    service.hold(params.businessId, getDate(params.date),
                            params.covers,
                            params.time,
                            getUniqueId(app.applicationContext))
                }
            }
        }
    }
}