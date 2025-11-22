package com.tripian.trpprovider.ui

import android.app.Application
import android.os.Bundle
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.tripian.trpprovider.R
import com.tripian.trpprovider.base.BaseViewModel
import com.tripian.trpprovider.domain.GetOpeningHours
import com.tripian.trpprovider.domain.HoldReservation
import com.tripian.trpprovider.util.extensions.getDate
import com.tripian.trpprovider.util.extensions.hideLoading
import com.tripian.trpprovider.util.extensions.navigateToFragment
import com.tripian.trpprovider.util.extensions.showLoading
import com.tripian.trpprovider.util.fragment.AnimationType
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class FRYelpInputVM @Inject constructor(val holdReservation: HoldReservation, val getOpeningHours: GetOpeningHours, val app: Application) : BaseViewModel(holdReservation, getOpeningHours) {

    @Inject
    lateinit var pageData: PageData

    var onSetDateListener = MutableLiveData<String>()
    var onSetCoversListener = MutableLiveData<String>()
    var onSetTimeListener = MutableLiveData<String>()
    var onSetTimesListener = MutableLiveData<List<String>>()

    var selectedDate: Long = -1L
        set(value) {
            field = value

            setTime()
        }
    var covers: String = "1"
    var time: String = "16:00"

    override fun onViewCreated(savedInstanceState: Bundle?) {
        super.onViewCreated(savedInstanceState)

        if (selectedDate == -1L) {
            selectedDate = Calendar.getInstance().timeInMillis
        }

        onSetDateListener.postValue(getDate(selectedDate))
        onSetCoversListener.postValue(covers)
        onSetTimeListener.postValue(time)
    }

    fun onClickedNext() {
        showLoading()

        holdReservation.on(HoldReservation.Params(pageData.providerData!!.businessId!!, selectedDate, covers, time), success = {
            hideLoading()

            pageData.holdId = it.holdId!!
            pageData.date = selectedDate
            pageData.covers = covers
            pageData.time = time

            navigateToFragment(fragment = FRYelpPerson.newInstance(), animation = AnimationType.ENTER_FROM_RIGHT)
        }, error = {
            hideLoading()

            showDialog(contentText = it.errorDesc, positiveBtn = strings.getString(R.string.ok))
        })
    }

    private fun setTime() {
        showLoading()

        if (TextUtils.isEmpty(time)) {
            time = "16:00"
            onSetTimeListener.postValue(time)
        }

        if (TextUtils.isEmpty(covers)) {
            covers = "1"
            onSetCoversListener.postValue(covers)
        }

        onSetTimesListener.postValue(ArrayList())

        getOpeningHours.on(GetOpeningHours.Params(pageData.providerData!!.businessId!!, covers, selectedDate, time), success = {
            onSetTimesListener.postValue(it)

            if (it.isNotEmpty()) {
                if (it.contains(time)) {
                    onSetTimeListener.postValue(it[it.indexOf(time)])
                } else {
                    onSetTimeListener.postValue(it[0])
                }
            }

            hideLoading()
        }, error = {
            hideLoading()
        })
    }

    fun onCoverChanged(str: String) {
        covers = str
    }

    fun onTimeChanged(str: String) {
        time = str
    }
}
