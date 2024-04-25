package com.truck.weighbridge.ui.list

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.truck.weighbridge.R
import com.truck.weighbridge.databinding.FragmentAddBinding
import com.truck.weighbridge.persistence.Truck
import com.truck.weighbridge.ui.MainActivity
import com.truck.weighbridge.ui.TruckViewModel
import com.truck.weighbridge.util.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import java.math.BigInteger
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class AddFragment : DaggerFragment() {

    @Inject
    lateinit var viewmodelProviderFactory: ViewModelProviderFactory

    lateinit var truckViewModel: TruckViewModel

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    // Method #1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Method #2
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()

        initView()

    }

    // Method #3
    private fun saveTruckToDatabase() {

        (activity as MainActivity?)?.showFloatingButton()

        if (validations()) {
            Toast.makeText(activity, "Ticket is saved", Toast.LENGTH_SHORT).show()
            saveTruck()
        } else
            Toast.makeText(activity, "Ticket is Discarded", Toast.LENGTH_SHORT).show()

    }

    // Method #4
    override fun onDestroyView() {
        super.onDestroyView()
        saveTruckToDatabase()
        _binding = null
    }

    // Method #5
    private fun saveTruck() {

        val truck = Truck(
            0,
            binding.driverName.text.toString(),
            binding.licenseNumber.text.toString(),
            binding.date.text.toString(),
            binding.time.text.toString(),
            binding.inboundTonnage.text.toString(),
            binding.outboundTonnage.text.toString(),
            binding.netWeight.text.toString()
        )

        //Call viewmodel to save the data
        truckViewModel.insert(truck)

    }

    // Method #6
    private fun validations(): Boolean {
        return !(binding.driverName.text.isNullOrEmpty()
                || binding.licenseNumber.text.isNullOrEmpty()
                || binding.date.text.isNullOrEmpty()
                || binding.time.text.isNullOrEmpty()
                || binding.inboundTonnage.text.isNullOrEmpty()
                || binding.outboundTonnage.text.isNullOrEmpty()
                || binding.netWeight.text.isNullOrEmpty())
    }

    // Method #7
    private fun setupViewModel() {
        truckViewModel =
            ViewModelProvider(this, viewmodelProviderFactory).get(TruckViewModel::class.java)
    }

    // Method #8
    private fun initView() {

        binding.btnAdd.setOnClickListener {
            validateAddData()
        }

        binding.date.isClickable = true;
        binding.date.isLongClickable = false;
        binding.date.setInputType(InputType.TYPE_NULL);
        binding.date.setOnClickListener {
            showDatePicker()
        }

        binding.time.isClickable = true;
        binding.time.isLongClickable = false;
        binding.time.setInputType(InputType.TYPE_NULL);
        binding.time.setOnClickListener {
            showTimePicker()
        }

        binding.inboundTonnage.addTextChangedListener {
            it?.let {
                if (it.isEmpty() || it.toString().toBigInteger() < BigInteger.ONE) {
                    binding.netWeight.setText("-")
                } else {
                    if (!binding.outboundTonnage.text.isNullOrEmpty()) {
                        val netWeightValue =
                            it.toString().toBigInteger() - binding.outboundTonnage.text.toString()
                                .toBigInteger()
                        binding.netWeight.setText(netWeightValue.toString())
                    } else {
                        binding.netWeight.setText(it.toString())
                    }
                }
            }
        }


        binding.outboundTonnage.addTextChangedListener {
            it?.let {
                if (it.isEmpty() || it.toString().toBigInteger() < BigInteger.ONE) {
                    binding.netWeight.text = binding.inboundTonnage.text
                } else {
                    if (!binding.inboundTonnage.text.isNullOrEmpty()) {
                        val netWeightValue =
                            binding.inboundTonnage.text.toString().toBigInteger() - it.toString()
                                .toBigInteger()
                        binding.netWeight.setText(netWeightValue.toString())
                    } else {
                        binding.netWeight.setText("-")
                    }
                }
            }
        }

    }

    // Method #9
    private fun showDatePicker() {
        val mcurrentDate = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(), { DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)
                binding.date.setText(formattedDate)
            },
            mcurrentDate.get(Calendar.YEAR),
            mcurrentDate.get(Calendar.MONTH),
            mcurrentDate.get(Calendar.DAY_OF_MONTH)
        )
        // Show the DatePicker dialog
        datePickerDialog.show()
    }

    // Method #10
    private fun showTimePicker() {
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime[Calendar.HOUR_OF_DAY]
        val minute = mcurrentTime[Calendar.MINUTE]
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { timePicker, selectedHour, selectedMinute ->
                binding.time.setText("$selectedHour:$selectedMinute");
            }, hour, minute, false
        )
        timePickerDialog.show()
    }

    // Method #11
    private fun validateAddData() {
        if (validations()) {
            Navigation.findNavController(requireActivity(), R.id.container).popBackStack()
        } else
            Toast.makeText(activity, "Please Fill The Form", Toast.LENGTH_SHORT).show()
    }

}