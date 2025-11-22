package com.tripian.trpprovider.util.extensions

import androidx.fragment.app.Fragment
import com.tripian.trpprovider.R
import com.tripian.trpprovider.base.BaseViewModel
import com.tripian.trpprovider.domain.model.BaseModel
import com.tripian.trpprovider.util.fragment.AnimationType
import com.tripian.trpprovider.util.fragment.FragmentFactory
import com.tripian.trpprovider.util.fragment.TransitionType
import kotlin.reflect.KClass

/**
 * fragmentManager BaseActivity'den ve BaseFragment'dan her bir view'in ViewModel'ine set edilmektedir.
 *
 * @see BaseFragment.onCreateView
 * @see BaseActivity.onCreate
 *
 * Activity supportFragmentManager kullanirken, Fragment'lar childFragmentManager kullanmaktadir.
 * inner fragment kullanim durumunda eğer backstack kullanilmayacak ise fragmentManagerEnable true gonderilmesi gerekmektedir.
 * backPress override manuel pop edilmelidir
 *
 * @see BaseViewModel.fragmentManager
 */
fun BaseViewModel.navigateToFragment(fragment: Fragment,
                                     addToBackStack: Boolean = true,
                                     fragmentManagerEnable: Boolean = false,
                                     clearBackStack: Boolean = false,
                                     viewId: Int = R.id.container,
                                     transitionType: TransitionType = TransitionType.REPLACE,
                                     animation: AnimationType = AnimationType.ENTER_WITH_ALPHA) {

    val factory = FragmentFactory.Builder(fragment)
        .addToBackStack(addToBackStack)
        .setFragmentManager(if (fragmentManagerEnable) fragmentManager else null)
        .setClearBackStack(clearBackStack)
        .setViewId(viewId)
        .setTransitionType(transitionType)
        .setAnimation(animation)
        .build()

    viewListener?.showFragment(factory)
}

fun BaseViewModel.showLoading() {
    viewListener?.showLoading()
}

fun BaseViewModel.hideLoading() {
    viewListener?.hideLoading()
}

fun BaseViewModel.goBack() {
    viewListener?.goBack()
}

fun BaseViewModel.returnPage(clazz: KClass<out Fragment>) {
    viewListener?.returnPage(clazz)
}

fun <T: BaseModel> BaseViewModel.returnResult(data: T) {
    viewListener?.returnResult(data)
}

fun BaseViewModel.showSnackBarMessage(message: String) {
    viewListener?.showSnackBarMessage(message)
}