package com.tripian.trpprovider.base

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.tripian.trpprovider.R
import com.tripian.trpprovider.databinding.FrWarningDialogBinding
import com.tripian.trpprovider.util.dialog.DGActionListener
import com.tripian.trpprovider.util.dialog.DGContent
import com.tripian.trpprovider.util.extensions.observe

/**
 * Created by semihozkoroglu on 11.03.2020.
 */
class FRWarningFragment : BaseDialogFragment<FrWarningDialogBinding, FRWarningDialogVM>(FrWarningDialogBinding::inflate) {

    var positiveListener: DGActionListener? = null
    var negativeListener: DGActionListener? = null

    companion object {
        fun newInstance(
            title: String? = null, contentText: String? = "",
            positiveBtn: String? = null, negativeBtn: String? = null,
            isCloseEnable: Boolean
        ): FRWarningFragment {
            val fragment = FRWarningFragment()

            val data = Bundle()

            if (!TextUtils.isEmpty(title)) {
                data.putString("title", title)
            }

            if (!TextUtils.isEmpty(positiveBtn)) {
                data.putString("positiveBtn", positiveBtn)
            }

            if (!TextUtils.isEmpty(negativeBtn)) {
                data.putString("negativeBtn", negativeBtn)
            }

            data.putString("contentText", contentText)
            data.putBoolean("isCloseEnable", isCloseEnable)

            fragment.arguments = data

            return fragment
        }
    }

    private fun getDialogContent(): DGContent {
        var title = arguments?.getString("title")

        if (TextUtils.isEmpty(title)) {
            title = getString(R.string.warning_page_title)
        }

        val positiveBtn = arguments?.getString("positiveBtn")
        val negativeBtn = arguments?.getString("negativeBtn")

        return DGContent(
            title = title,
            positiveBtn = positiveBtn,
            negativeBtn = negativeBtn,
            positiveListener = positiveListener,
            negativeListener = negativeListener
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dgContent = getDialogContent()

        if (dgContent != null) {
            if (!TextUtils.isEmpty(dgContent.title)) {
                binding.tvTitle.text = dgContent.title
            }

            if (!TextUtils.isEmpty(dgContent.negativeBtn)) {
                binding.btnNegative.visibility = View.VISIBLE
                binding.btnNegative.text = dgContent.negativeBtn
                binding.btnNegative.setOnClickListener {

                    if (dgContent.negativeListener == null) {
                        dismiss()
                    }

                    dgContent.negativeListener?.onClicked(this)
                }
            } else {
                binding.btnNegative.visibility = View.GONE
            }

            if (!TextUtils.isEmpty(dgContent.positiveBtn)) {
                binding.btnPositive.visibility = View.VISIBLE
                binding.btnPositive.text = dgContent.positiveBtn
                binding.btnPositive.setOnClickListener {
                    if (dgContent.positiveListener == null) {
                        dismiss()
                    }

                    dgContent.positiveListener?.onClicked(this)
                }
            } else {
                binding.btnPositive.visibility = View.GONE
            }

            isCancelable = false
        }
    }

    override fun setReceivers() {
        super.setReceivers()

        observe(viewModel.onSetDescriptionListener) {
            binding.tvDescription.text = it
        }
    }
}