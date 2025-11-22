package com.tripian.trpprovider.util.extensions

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.tripian.trpprovider.base.*
import com.tripian.trpprovider.domain.model.BaseModel
import com.tripian.trpprovider.util.AlertType
import com.tripian.trpprovider.util.UseCaseListener
import com.tripian.trpprovider.util.ViewListener
import com.tripian.trpprovider.util.dialog.DGContent
import com.tripian.trpprovider.util.fragment.FragmentFactory
import javax.inject.Inject
import kotlin.reflect.KClass

/**
 * Created by semihozkoroglu on 2019-12-06.
 */
fun BaseViewModel.setUseCasesListener() {
    val viewModel = this

    this::class.java.fields.forEach {
        if (it != null && it.isAnnotationPresent(Inject::class.java)) {
            val obj = it.get(this)
            if (obj is BaseUseCase<*, *>) {
                useCases.add(obj)
            }
        }
    }

    for (useCase in useCases) {
        useCase.useCaseListener = object : UseCaseListener {
            override fun showSnackBarMessage(message: String) {
                viewListener?.showSnackBarMessage(message)
            }

            override fun showDialog(dgContent: DGContent) {
                viewModel.showDialog(dgContent.title, dgContent.content, dgContent.positiveBtn, dgContent.negativeBtn)
            }
        }
    }
}

fun BaseActivity<*,*>.setViewListener() {
    val baseActivity = this

    viewModel.viewListener = object : ViewListener {
        override fun finishActivity() {
            baseActivity.finish()
        }

        override fun showSnackBarMessage(message: String) {
            baseActivity.showSnackBarMessage(message)
        }

        override fun startActivity(kClass: KClass<out FragmentActivity>, bundle: Bundle?) {
            baseActivity.startActivity(kClass, bundle)
        }

        override fun returnPage(clazz: KClass<out Fragment>) {
            baseActivity.returnPage(clazz)
        }

        override fun <T : BaseModel> returnResult(data: T) {
            baseActivity.returnResult(data)
        }

        override fun goBack() {
            onBackPressed()
        }

        override fun showLoading() {
            baseActivity.showLoading()
        }

        override fun hideLoading() {
            baseActivity.hideLoading()
        }

        override fun showFragment(factory: FragmentFactory) {
            baseActivity.showFragment(factory)
        }

        override fun showAlert(type: AlertType, message: String, duration: Long) {
            baseActivity.showAlert(type, message, duration)
        }
    }
}

fun BaseFragment<*,*>.setViewListener() {
    viewModel.viewListener = object : ViewListener {
        override fun finishActivity() {
            activity?.finish()
        }

        override fun showSnackBarMessage(message: String) {
            activity?.showSnackBarMessage(message)
        }

        override fun startActivity(kClass: KClass<out FragmentActivity>, bundle: Bundle?) {
            activity?.startActivity(kClass, bundle)
        }

        override fun returnPage(clazz: KClass<out Fragment>) {
            activity?.returnPage(clazz)
        }

        override fun <T : BaseModel> returnResult(data: T) {
            activity?.returnResult(data)
        }

        override fun goBack() {
            activity?.onBackPressed()
        }

        override fun showLoading() {
            if (activity != null) {
                (activity as BaseActivity<*,*>).showLoading()
            }
        }

        override fun hideLoading() {
            if (activity != null) {
                (activity as BaseActivity<*,*>).hideLoading()
            }
        }

        override fun showFragment(factory: FragmentFactory) {
            if (factory.mViewId == -1) {
                factory.mViewId = container?.id!!
            }

            activity?.showFragment(factory)
        }

        override fun showAlert(type: AlertType, message: String, duration: Long) {
            (activity as BaseActivity<*,*>).showAlert(type, message,duration)
        }
    }
}

fun BaseDialogFragment<*,*>.setViewListener() {
    viewModel.viewListener = object : ViewListener {
        override fun finishActivity() {
            activity?.finish()
        }

        override fun showSnackBarMessage(message: String) {
            activity?.showSnackBarMessage(message)
        }

        override fun startActivity(kClass: KClass<out FragmentActivity>, bundle: Bundle?) {
            activity?.startActivity(kClass, bundle)
        }

        override fun returnPage(clazz: KClass<out Fragment>) {

        }

        override fun <T : BaseModel> returnResult(data: T) {
            activity?.returnResult(data)
        }

        override fun goBack() {
            dismiss()
        }

        override fun showLoading() {
            (activity as BaseActivity<*,*>).showLoading()
        }

        override fun hideLoading() {
            (activity as BaseActivity<*,*>).hideLoading()
        }

        override fun showFragment(factory: FragmentFactory) {
            activity?.showFragment(factory)
        }

        override fun showAlert(type: AlertType, message: String, duration: Long) {
            (activity as BaseActivity<*,*>).showAlert(type, message, duration)
        }
    }
}