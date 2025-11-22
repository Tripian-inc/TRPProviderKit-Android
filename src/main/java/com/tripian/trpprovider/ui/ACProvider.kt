package com.tripian.trpprovider.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.tripian.trpprovider.R
import com.tripian.trpprovider.base.BaseActivity
import com.tripian.trpprovider.databinding.AcProviderBinding
import com.tripian.trpprovider.util.Navigation
import com.tripian.trpprovider.util.ToolbarProperties
import com.tripian.trpprovider.util.extensions.dp2Px
import com.tripian.trpprovider.util.extensions.hideKeyboard

/**
 * Created by semihozkoroglu on 22.07.2020.
 */
class ACProvider : BaseActivity<AcProviderBinding, ACProviderVM>() {

    private var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>? = null
    private var navigation: Navigation? = null
    private var isFinished = false

    override fun getViewBinding(): AcProviderBinding {
        return AcProviderBinding.inflate(layoutInflater)
    }

    override fun onResume() {
        super.onResume()

        overridePendingTransition(0, 0)
    }

    override fun onPause() {
        overridePendingTransition(0, 0)

        super.onPause()
    }

    override fun setToolbarProperties(properties: ToolbarProperties) {
        super.setToolbarProperties(properties)

        binding.tvTitle.text = properties.title

        navigation = properties.type

        if (properties.type == Navigation.CLOSE) {
            val padding = dp2Px(8f).toInt()
            binding.imNavigation.setPadding(padding, padding, padding, padding)

            binding.imNavigation.setImageResource(R.drawable.ic_close)
            binding.imNavigation.setOnClickListener {
                onBackPressed()
            }
        } else {
            val padding = dp2Px(4f).toInt()
            binding.imNavigation.setPadding(padding, padding, padding, padding)

            binding.imNavigation.setImageResource(R.drawable.ic_chevron_left_grey600_24dp)
            binding.imNavigation.setOnClickListener {
                viewModel.onClickedBack()
            }
        }

        DrawableCompat.setTint(binding.imNavigation.drawable, ContextCompat.getColor(applicationContext, R.color.blue_400))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bottomSheetBehavior = BottomSheetBehavior.from(binding.llBottom)
        bottomSheetBehavior?.isHideable = true
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
        bottomSheetBehavior?.setBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    hideKeyboard()

                    isFinished = true
                    finish()
                }
            }
        })

        binding.llBottom.postDelayed({
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
            binding.viBackground.animate().setDuration(400).alpha(1f).start()
        }, 300)
    }

    override fun setListeners() {
        binding.viBackground.setOnClickListener {
            binding.viBackground.visibility = View.GONE
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

    override fun setReceivers() {
    }

    override fun onBackPressed() {
        if (navigation == Navigation.BACK) {
            super.onBackPressed()
        } else {
            binding.viBackground.visibility = View.GONE
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

    override fun finish() {
        if (isFinished) {
            super.finish()
        } else {
            isFinished = true

            binding.viBackground.visibility = View.GONE
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }
}