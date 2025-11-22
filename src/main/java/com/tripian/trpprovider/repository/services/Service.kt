package com.tripian.trpprovider.repository.services

import com.tripian.trpprovider.repository.services.response.*
import io.reactivex.Observable
import retrofit2.http.*

interface Service {

    @FormUrlEncoded
    @POST("bookings/{businessId}/holds")
    fun hold(@Path("businessId") businessId: String, @Field("date") date: String,
             @Field("covers") covers: String, @Field("time") time: String,
             @Field("unique_id") unique_id: String): Observable<YelpHoldResponse>

    @FormUrlEncoded
    @POST("bookings/{businessId}/reservations")
    fun reservations(@Path("businessId") businessId: String, @Field("time") time: String,
                     @Field("unique_id") unique_id: String, @Field("hold_id") hold_id: String,
                     @Field("covers") covers: String, @Field("phone") phone: String,
                     @Field("first_name") first_name: String, @Field("last_name") last_name: String,
                     @Field("date") date: String, @Field("email") email: String): Observable<YelpReservationResponse>

    @GET("bookings/reservation/{reservationId}/status")
    fun status(@Path("reservationId") reservationId: String): Observable<YelpStatusResponse>

    @GET("bookings/reservation/{reservationId}/cancel")
    fun cancel(@Path("reservationId") reservationId: String): Observable<YelpCancelResponse>

    @GET("bookings/{businessId}/openings")
    fun getOpeningHours(@Path("businessId") businessId: String, @Query("covers") covers: String,
                        @Query("date") date: String, @Query("time") time: String): Observable<YelpOpeningHoursResponse>
}