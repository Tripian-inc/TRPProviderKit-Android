package com.tripian.trpprovider.ui

import com.tripian.trpprovider.R
import com.tripian.trpprovider.base.BaseFragment
import com.tripian.trpprovider.databinding.FrYelpPersonBinding
import com.tripian.trpprovider.util.Navigation
import com.tripian.trpprovider.util.ToolbarProperties
import com.tripian.trpprovider.util.extensions.observe
import javax.inject.Inject

/**
 * Created by semihozkoroglu on 28.07.2020.
 */
class FRYelpPerson :
    BaseFragment<FrYelpPersonBinding, FRYelpPersonVM>(FrYelpPersonBinding::inflate) {

    @Inject
    lateinit var pageData: PageData

    companion object {
        fun newInstance(): FRYelpPerson {
            return FRYelpPerson()
        }
    }

    override fun getToolbarProperties(): ToolbarProperties {
        return ToolbarProperties(pageData.providerData?.title, Navigation.BACK)
    }

    override fun setListeners() {
        super.setListeners()

        binding.btnContinue.setOnClickListener {
            viewModel.onClickedContinue(
                binding.etPhone.text.toString(),
                binding.etName.text.toString(),
                binding.etLastName.text.toString(),
                binding.etEmail.text.toString()
            )
        }
    }

    override fun setReceivers() {
        super.setReceivers()

        observe(viewModel.onSetFirstNameListener) {
            binding.etName.setText(it)
        }

        observe(viewModel.onSetLastNameListener) {
            binding.etLastName.setText(it)
        }

        observe(viewModel.onSetEmailListener) {
            binding.etEmail.setText(it)
        }
    }
}