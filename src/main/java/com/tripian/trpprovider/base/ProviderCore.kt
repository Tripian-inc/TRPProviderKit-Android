package com.tripian.trpprovider.base

import android.app.Activity
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.tripian.trpprovider.di.DaggerAppComponent
import com.tripian.trpprovider.domain.CancelReservation
import com.tripian.trpprovider.domain.ReservationStatus
import com.tripian.trpprovider.domain.model.ReservationStatusModel
import com.tripian.trpprovider.repository.services.response.YelpCancelResponse
import com.tripian.trpprovider.util.Callback
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Created by semihozkoroglu on 23.07.2020.
 */
class ProviderCore : HasActivityInjector {

    companion object {
        lateinit var core: ProviderCore

        fun inject(activity: AppCompatActivity) {
            core.activityInjector().inject(activity)
        }

        fun reservationStatus(reservationId: String, callback: Callback<ReservationStatusModel>) {
            core.reservationStatus(reservationId, callback)
        }

        fun cancelReservation(reservationId: String, callback: Callback<YelpCancelResponse>) {
            core.cancelReservation(reservationId, callback)
        }
    }

    @Inject
    lateinit var actInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var reservationDomain: ReservationStatus

    @Inject
    lateinit var cancelReservationDomain: CancelReservation

    fun init(app: Application, yelpApiKey: String): ProviderCore {
        core = this

        DaggerAppComponent.builder()
            .configurations(object : AppConfig() {
                override fun serviceUrl(): String {
                    return "https://api.yelp.com/v3/"
                }

                override fun apiKey(): String {
                    return yelpApiKey // Sandbox
                }
            })
            .application(app)
            .build()
            .inject(this)

        return this
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return actInjector
    }

    fun reservationStatus(reservationId: String, callback: Callback<ReservationStatusModel>) {
        reservationDomain.on(ReservationStatus.Params(reservationId), success = { status ->
            val response = ReservationStatusModel()
            response.active = status.active!!
            response.covers = status.covers
            response.date = status.date
            response.time = status.time

            callback.onResponse(response)
        }, error = {
            callback.onResponse(null)
        })
    }

    fun cancelReservation(reservationId: String, callback: Callback<YelpCancelResponse>) {
        cancelReservationDomain.on(CancelReservation.Params(reservationId), success = {
            callback.onResponse(it)
        })
    }
}