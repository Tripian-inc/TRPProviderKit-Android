package com.tripian.trpprovider.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.tripian.trpprovider.domain.model.BaseModel
import com.tripian.trpprovider.util.fragment.FragmentFactory
import kotlin.reflect.KClass

/**
 * Created by semihozkoroglu on 2019-08-06.
 */
interface ViewListener {

    fun showFragment(factory: FragmentFactory)

    fun showAlert(type: AlertType, message: String, duration: Long)

    fun showLoading()

    fun hideLoading()

    fun goBack()

    fun returnPage(clazz: KClass<out Fragment>)

    fun <T: BaseModel> returnResult(data: T)

    fun startActivity(kClass: KClass<out FragmentActivity>, bundle: Bundle? = null)

    fun finishActivity()

    fun showSnackBarMessage(message: String)
}