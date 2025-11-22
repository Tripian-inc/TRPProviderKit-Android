package com.tripian.trpprovider.ui

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.tripian.trpprovider.R
import com.tripian.trpprovider.base.BaseViewModel
import com.tripian.trpprovider.domain.DoReservation
import com.tripian.trpprovider.domain.ReservationStatus
import com.tripian.trpprovider.domain.model.ReservationDetailModel
import com.tripian.trpprovider.domain.model.ReservationStatusModel
import com.tripian.trpprovider.util.extensions.hideLoading
import com.tripian.trpprovider.util.extensions.returnResult
import com.tripian.trpprovider.util.extensions.showLoading
import javax.inject.Inject

class FRYelpPersonVM @Inject constructor(val doReservation: DoReservation, val reservationStatus: ReservationStatus) : BaseViewModel(doReservation, reservationStatus) {

    @Inject
    lateinit var pageData: PageData

    var onSetFirstNameListener = MutableLiveData<String>()
    var onSetLastNameListener = MutableLiveData<String>()
    var onSetEmailListener = MutableLiveData<String>()

    override fun onViewCreated(savedInstanceState: Bundle?) {
        super.onViewCreated(savedInstanceState)

        onSetFirstNameListener.postValue(pageData.providerData!!.firstName)
        onSetLastNameListener.postValue(pageData.providerData!!.lastName)
        onSetEmailListener.postValue(pageData.providerData!!.email)
    }

    fun onClickedContinue(phone: String, firstName: String, lastName: String, email: String) {
        showLoading()

        doReservation.on(DoReservation.Params(pageData.providerData!!.businessId!!, pageData.holdId, pageData.time, pageData.covers,
                pageData.date, phone, firstName, lastName, email),
                success = { reservation ->
                    reservationStatus.on(ReservationStatus.Params(reservation.reservationId!!), success = { status ->
                        hideLoading()

                        val reservationStatus = ReservationStatusModel()
                        reservationStatus.active = status.active!!
                        reservationStatus.covers = status.covers
                        reservationStatus.date = status.date
                        reservationStatus.time = status.time

                        val reservationDetail = ReservationDetailModel()
                        reservationDetail.reservationId = reservation.reservationId
                        reservationDetail.confirmationUrl = reservation.confirmationUrl
                        reservationDetail.notes = reservation.notes
                        reservationDetail.reservationStatus = reservationStatus

                        returnResult(reservationDetail)
                    }, error = {
                        hideLoading()
                    })
                }, error = {
            hideLoading()

            showDialog(contentText = it.errorDesc, positiveBtn = strings.getString(R.string.ok))
        })
    }
}
