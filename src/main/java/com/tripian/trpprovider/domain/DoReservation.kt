package com.tripian.trpprovider.domain

import android.app.Application
import android.text.TextUtils
import com.tripian.trpprovider.R
import com.tripian.trpprovider.base.BaseUseCase
import com.tripian.trpprovider.repository.base.ErrorModel
import com.tripian.trpprovider.repository.services.Service
import com.tripian.trpprovider.repository.services.response.YelpReservationResponse
import com.tripian.trpprovider.util.extensions.getDate
import com.tripian.trpprovider.util.extensions.getUniqueId
import com.tripian.trpprovider.util.extensions.isValidEmail
import javax.inject.Inject

/**
 * Created by semihozkoroglu on 5.08.2020.
 */
class DoReservation @Inject constructor(val service: Service, val app: Application) : BaseUseCase<YelpReservationResponse, DoReservation.Params>() {

    class Params(val businessId: String, val holdId: String, val time: String, val covers: String,
                 val date: Long, val phone: String, val firstName: String, val lastName: String, val email: String)

    override fun on(params: Params?) {
        when {
            TextUtils.isEmpty(params!!.firstName) -> {
                onSendError(ErrorModel(strings.getString(R.string.please_enter_name)))
            }
            TextUtils.isEmpty(params.lastName) -> {
                onSendError(ErrorModel(strings.getString(R.string.please_enter_last_name)))
            }
            TextUtils.isEmpty(params.email) -> {
                onSendError(ErrorModel(strings.getString(R.string.please_enter_email)))
            }
            !isValidEmail(params.email) -> {
                onSendError(ErrorModel(strings.getString(R.string.please_enter_valid_email)))
            }
            TextUtils.isEmpty(params.phone) -> {
                onSendError(ErrorModel(strings.getString(R.string.please_enter_phone)))
            }
            else -> {
                sendRequest {
                    service.reservations(params!!.businessId, params.time,
                            getUniqueId(app.applicationContext),
                            params.holdId,
                            params.covers, params.phone, params.firstName, params.lastName,
                            getDate(params.date), params.email)
                }
            }
        }
    }
}