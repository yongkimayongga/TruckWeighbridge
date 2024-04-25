package com.truck.weighbridge.ui.dialog

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.truck.weighbridge.databinding.FragmentFilterDialogBinding
import com.truck.weighbridge.ui.TruckViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FilterDialog : BottomSheetDialogFragment() {

    private var binding: FragmentFilterDialogBinding? = null

    internal var onFilterData: ((filterBy: String, value: String) -> Unit)? = null
    internal var onResetData: (() -> Unit)? = null

    private var isResetFilter = false

    init {
        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFilterDialogBinding.inflate(inflater, container, false)
        binding?.lifecycleOwner = viewLifecycleOwner
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setOnShowListener {
            val dialog = it as BottomSheetDialog
            val bottomSheet =
                dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            bottomSheet?.let { sheet ->
                dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
                sheet.parent.parent.requestLayout()
                sheet.setBackgroundResource(android.R.color.transparent)
            }
        }
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


    private fun initView() {
        binding?.apply {
            buttonApply.setOnClickListener {
                when {
                    (textName.text?.isNotEmpty() ?: false) -> {
                        onFilterData?.invoke(TruckViewModel.PATH_NAME, textName.text.toString())
                        dismissAllowingStateLoss()
                    }

                    (textLicense.text?.isNotEmpty() ?: false) -> {
                        onFilterData?.invoke(
                            TruckViewModel.PATH_LICENSE,
                            textLicense.text.toString()
                        )
                        dismissAllowingStateLoss()
                    }

                    (textDate.text?.isNotEmpty() ?: false) -> {
                        onFilterData?.invoke(
                            TruckViewModel.PATH_DATE, textDate.text.toString()
                        )
                        dismissAllowingStateLoss()
                    }

                    else -> {
                        if (isResetFilter) {
                            onResetData?.invoke()
                            dismissAllowingStateLoss()
                        } else {
                            Toast.makeText(
                                activity,
                                "Please Choose 1 Filter at Least",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

            checkReset.setOnCheckedChangeListener { buttonView, isChecked ->
                isResetFilter = true
            }

            selectedDate.setOnClickListener {
                binding?.textDate?.let {
                    showDatePicker(
                        it
                    )
                }
            }

            textName.doAfterTextChanged {
                if (it.toString().isNotEmpty()) {
                    textLicense.setText("")
                    textDate.text = ""
                }
            }
            textLicense.doAfterTextChanged {
                if (it.toString().isNotEmpty()) {
                    textName.setText("")
                    textDate.text = ""
                }
            }
            textDate.doAfterTextChanged {
                if (it.toString().isNotEmpty()) {
                    textName.setText("")
                    textLicense.setText("")
                }
            }
        }
    }

    private fun showDatePicker(textView: TextView) {
        val mcurrentDate = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(), { DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)
                textView.text = formattedDate
            },
            mcurrentDate.get(Calendar.YEAR),
            mcurrentDate.get(Calendar.MONTH),
            mcurrentDate.get(Calendar.DAY_OF_MONTH)
        )
        // Show the DatePicker dialog
        datePickerDialog.show()
    }

}
