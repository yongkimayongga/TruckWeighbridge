package com.truck.weighbridge.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.truck.weighbridge.R
import com.truck.weighbridge.databinding.FragmentEditBinding
import com.truck.weighbridge.persistence.Truck
import com.truck.weighbridge.ui.MainActivity
import com.truck.weighbridge.ui.TruckViewModel
import com.truck.weighbridge.util.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import java.math.BigInteger
import javax.inject.Inject

class EditFragment : DaggerFragment() {

    @Inject
    lateinit var viewmodelProviderFactory: ViewModelProviderFactory

    lateinit var truckViewModel: TruckViewModel

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Method #1
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity?)?.hideFloatingButton()

        prepareTruckForEditing()
        setupViewModel()
        initView()
    }

    // Method #2
    private fun saveTruckToDatabase() {

        (activity as MainActivity?)?.showFloatingButton()

        if (validations()) {
            Toast.makeText(activity, "Truck Data is saved", Toast.LENGTH_SHORT).show()
            saveTruck()
            val id: Int = EditFragmentArgs.fromBundle(
                requireArguments()
            ).truck?.id!!
            Log.e("DEBUG", "saving truck $id")

        } else {
            Toast.makeText(activity, "Ticket is Discarded", Toast.LENGTH_SHORT).show()
            //Delete the truck if all fields are empty (this is done by user)
            val id: Int = EditFragmentArgs.fromBundle(
                // before arguments!!
                requireArguments()
            ).truck?.id!!
            truckViewModel.deleteById(id)
            Log.e("DEBUG", "deleting truck")
        }
    }

    // Method #3
    override fun onDestroyView() {
        super.onDestroyView()
        saveTruckToDatabase()
        _binding = null
    }

    // Method #4
    private fun saveTruck() {

        val id: Int? = EditFragmentArgs.fromBundle(
            requireArguments()
        ).truck?.id

        val truck = Truck(
            id!!,
            binding.editDriverName.text.toString(),
            binding.editLicenseNumber.text.toString(),
            binding.editDate.text.toString(),
            binding.editTime.text.toString(),
            binding.editInboundTonnage.text.toString(),
            binding.editOutboundTonnage.text.toString(),
            binding.editNetWeight.text.toString()
        )

        //Call viewmodel to save the data
        truckViewModel.update(truck)

    }

    // Method #5
    private fun validations(): Boolean {
        return !(binding.editDriverName.text.isNullOrEmpty()
                || binding.editLicenseNumber.text.isNullOrEmpty()
                || binding.editDate.text.isNullOrEmpty()
                || binding.editTime.text.isNullOrEmpty()
                || binding.editInboundTonnage.text.isNullOrEmpty()
                || binding.editOutboundTonnage.text.isNullOrEmpty()
                || binding.editNetWeight.text.isNullOrEmpty())
    }


    // Method #6
    private fun setupViewModel() {
        truckViewModel =
            ViewModelProvider(this, viewmodelProviderFactory).get(TruckViewModel::class.java)
    }


    // Method #7
    private fun prepareTruckForEditing() {
        arguments?.let {
            val safeArgs =
                EditFragmentArgs.fromBundle(
                    it
                )
            val truck = safeArgs.truck
            binding.editDriverName.setText(truck?.driverName.toString())
            binding.editLicenseNumber.setText(truck?.licenseNumber.toString())
            binding.editDate.setText(truck?.date.toString())
            binding.editTime.setText(truck?.time.toString())
            binding.editInboundTonnage.setText(truck?.inboundTonnage.toString())
            binding.editOutboundTonnage.setText(truck?.outboundTonnage.toString())
            binding.editNetWeight.setText(truck?.netWeight.toString())
        }

    }

    // Method 8
    private fun validateEditData() {
        if (validations()) {
            Navigation.findNavController(requireActivity(), R.id.container).popBackStack()
        } else
            Toast.makeText(activity, "Please Check Edited Data", Toast.LENGTH_SHORT).show()
    }

    // Method 9
    private fun initView() {

        binding.btnEdit.setOnClickListener {
            validateEditData()
        }

        binding.editInboundTonnage.addTextChangedListener {
            it?.let {
                if (it.isEmpty() || it.toString().toBigInteger() < BigInteger.ONE) {
                    binding.editNetWeight.setText("-")
                } else {
                    if (!binding.editOutboundTonnage.text.isNullOrEmpty()) {
                        val netWeightValue =
                            it.toString()
                                .toBigInteger() - binding.editOutboundTonnage.text.toString()
                                .toBigInteger()
                        binding.editNetWeight.setText(netWeightValue.toString())
                    } else {
                        binding.editNetWeight.setText(it.toString())
                    }
                }
            }
        }


        binding.editOutboundTonnage.addTextChangedListener {
            it?.let {
                if (it.isEmpty() || it.toString().toBigInteger() < BigInteger.ONE) {
                    binding.editNetWeight.text = binding.editInboundTonnage.text
                } else {
                    if (!binding.editInboundTonnage.text.isNullOrEmpty()) {
                        val netWeightValue = binding.editInboundTonnage.text.toString()
                            .toBigInteger() - it.toString().toBigInteger()
                        binding.editNetWeight.setText(netWeightValue.toString())
                    } else {
                        binding.editNetWeight.setText("-")
                    }
                }
            }
        }
    }

}

