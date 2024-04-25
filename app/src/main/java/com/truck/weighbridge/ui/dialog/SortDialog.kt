package com.truck.weighbridge.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.truck.weighbridge.databinding.FragmentSortDialogBinding

class SortDialog : BottomSheetDialogFragment() {

    private var binding: FragmentSortDialogBinding? = null
    internal var onSortNameAscData: (() -> Unit)? = null
    internal var onSortNameDescData: (() -> Unit)? = null
    internal var onSortDateAscData: (() -> Unit)? = null
    internal var onSortDateDescData: (() -> Unit)? = null
    internal var onSortLicenseAscData: (() -> Unit)? = null
    internal var onSortLicenseDescData: (() -> Unit)? = null

    init {
        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSortDialogBinding.inflate(inflater, container, false)
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
            buttonNameAsc.setOnClickListener {
                onSortNameAscData?.invoke()
                dismissAllowingStateLoss()
            }
            buttonNameDesc.setOnClickListener {
                onSortNameDescData?.invoke()
                dismissAllowingStateLoss()
            }
            buttonDateAsc.setOnClickListener {
                onSortDateAscData?.invoke()
                dismissAllowingStateLoss()
            }
            buttonDateDesc.setOnClickListener {
                onSortDateDescData?.invoke()
                dismissAllowingStateLoss()
            }
            buttonLicenseAsc.setOnClickListener {
                onSortLicenseAscData?.invoke()
                dismissAllowingStateLoss()
            }
            buttonLicenseDesc.setOnClickListener {
                onSortLicenseDescData?.invoke()
                dismissAllowingStateLoss()
            }
        }
    }
}
