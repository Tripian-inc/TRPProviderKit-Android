package com.tripian.trpprovider.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tripian.trpprovider.R
import com.tripian.trpprovider.base.BaseFragment
import com.tripian.trpprovider.databinding.FrYelpInputBinding
import com.tripian.trpprovider.util.Navigation
import com.tripian.trpprovider.util.ToolbarProperties
import com.tripian.trpprovider.util.extensions.observe
import java.util.*
import javax.inject.Inject

/**
 * Created by semihozkoroglu on 23.07.2020.
 */
class FRYelpInput : BaseFragment<FrYelpInputBinding,FRYelpInputVM>(FrYelpInputBinding::inflate) {

    @Inject
    lateinit var pageData: PageData

    companion object {
        fun newInstance(): FRYelpInput {
            return FRYelpInput()
        }
    }

    override fun getToolbarProperties(): ToolbarProperties {
        return ToolbarProperties(pageData.providerData?.title, Navigation.CLOSE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvAlternativeTimes.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
    }

    override fun setListeners() {
        super.setListeners()

        binding.etPartySize.doAfterTextChanged { viewModel.onCoverChanged(it.toString()) }
        binding.etTime.doAfterTextChanged { viewModel.onTimeChanged(it.toString()) }

        binding.btnContinue.setOnClickListener { viewModel.onClickedNext() }

        binding.etDate.setOnClickListener {
            showDatePicker({ _: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val calendar = Calendar.getInstance()
                calendar[Calendar.YEAR] = year
                calendar[Calendar.MONTH] = monthOfYear
                calendar[Calendar.DAY_OF_MONTH] = dayOfMonth

                viewModel.selectedDate = calendar.timeInMillis

                val month = if (monthOfYear < 10) {
                    "0${monthOfYear + 1}"
                } else {
                    "${monthOfYear + 1}"
                }
                val date = "$year-${month}-$dayOfMonth"
                binding.etDate.setText(date)
            }, viewModel.selectedDate)
        }
    }

    override fun setReceivers() {
        super.setReceivers()

        observe(viewModel.onSetTimeListener) {
            binding.etTime.setText(it)
        }

        observe(viewModel.onSetCoversListener) {
            binding.etPartySize.setText(it)
        }

        observe(viewModel.onSetTimesListener) {
            binding.rvAlternativeTimes.adapter = object : AdapterTimeSelection(requireContext(), it!!) {
                override fun onClickedTime(time: String) {
                    binding.etTime.setText(time)
                }
            }
        }
    }

    private fun showDatePicker(listener: DatePickerDialog.OnDateSetListener, selectedDate: Long) {
        val currentCalendar = Calendar.getInstance()
        currentCalendar.timeInMillis = selectedDate
        val datePicker = DatePickerDialog(requireContext(), listener,
            currentCalendar[Calendar.YEAR], currentCalendar[Calendar.MONTH], currentCalendar[Calendar.DAY_OF_MONTH])
        val minCalendar = Calendar.getInstance()
        datePicker.datePicker.minDate = minCalendar.timeInMillis
        datePicker.show()
    }
}