package com.truck.weighbridge.ui.list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.truck.weighbridge.R
import com.truck.weighbridge.databinding.FragmentListBinding
import com.truck.weighbridge.persistence.Truck
import com.truck.weighbridge.session.SessionManager
import com.truck.weighbridge.ui.TruckViewModel
import com.truck.weighbridge.ui.TruckViewModel.Companion.ASC
import com.truck.weighbridge.ui.TruckViewModel.Companion.DESC
import com.truck.weighbridge.ui.TruckViewModel.Companion.PATH_DATE
import com.truck.weighbridge.ui.TruckViewModel.Companion.PATH_LICENSE
import com.truck.weighbridge.ui.TruckViewModel.Companion.PATH_NAME
import com.truck.weighbridge.ui.adapter.TruckAdapter
import com.truck.weighbridge.ui.dialog.FilterDialog
import com.truck.weighbridge.ui.dialog.SortDialog
import com.truck.weighbridge.ui.login.LoginActivity
import com.truck.weighbridge.util.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class ListFragment : DaggerFragment(),
    TruckAdapter.Interaction {

    private lateinit var truckAdapter: TruckAdapter

    private lateinit var truckViewModel: TruckViewModel

    @Inject
    lateinit var viewmodelProviderFactory: ViewModelProviderFactory

    lateinit var allTrucksInitialize: List<Truck>
    lateinit var allTrucks: List<Truck>

    @Inject
    lateinit var firebaseDatabase: FirebaseDatabase

    @Inject
    lateinit var sessionManager: SessionManager

    var token: String? = null

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    // Method #1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        allTrucks = arrayListOf()
        allTrucksInitialize = arrayListOf()
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Method #2
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        setupViewModel()
        initRecyclerView()
        getUserIdAndSetFireBaseReference()
        observerLiveData()
        initView()
    }

    // Method #3
    private fun observerLiveData() {
        truckViewModel.getAllTrucks().observe(viewLifecycleOwner, Observer { lisOfTrucks ->
            lisOfTrucks?.let {
                allTrucks = it
                allTrucksInitialize = it
                truckAdapter.swap(it)
                firebaseDatabase.reference.setValue(allTrucks)
                    .addOnSuccessListener { Log.d("On Success", "Add Data") }
                    .addOnFailureListener { Log.d("On Failure", it.message.toString()) }
            }
        })
        sessionManager.getToken().observe(viewLifecycleOwner, Observer {
            token = it.token
            Log.d("Debug token ", token.toString())
        })
    }

    // Method #4
    private fun initRecyclerView() {
        binding.recyclerView.apply {
            truckAdapter = TruckAdapter(
                allTrucks,
                this@ListFragment
            )
            layoutManager = LinearLayoutManager(this@ListFragment.context)
            adapter = truckAdapter
            val swipe = ItemTouchHelper(initSwipeToDelete())
            swipe.attachToRecyclerView(binding.recyclerView)
        }
    }

    // Method #5
    private fun setupViewModel() {
        truckViewModel =
            ViewModelProvider(this, viewmodelProviderFactory).get(TruckViewModel::class.java)
    }

    // Method #6
    private fun initSwipeToDelete(): ItemTouchHelper.SimpleCallback {
        return object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                truckViewModel.delete(allTrucks.get(position))
            }
        }
    }

    // Method #7
    override fun onItemSelected(position: Int, item: Truck) {
        val navDirection =
            ListFragmentDirections.actionListFragmentToEditFragment(
                item
            )
        findNavController().navigate(navDirection)
    }


    // Method #8
    private fun getUserIdAndSetFireBaseReference() {
        sessionManager.getToken().observe(viewLifecycleOwner, Observer {
            token = it.token
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Method #9
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    // Method #10
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.logout -> {
                sessionManager.deleteToken()
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Method 11
    private fun initView() {
        binding.fabFilter.setOnClickListener {
            val fragmentTransaction = hideDialog(TAG_FILTER_DIALOG)
            FilterDialog().apply {
                onFilterData = { filterBy, value ->
                    truckAdapter.filter(allTrucks, filterBy, value)
                }
                onResetData = {
                    truckAdapter.sort(allTrucksInitialize, ASC, PATH_NAME)
                }
            }.show(fragmentTransaction, TAG_FILTER_DIALOG)
        }

        binding.fabSort.setOnClickListener {
            val fragmentTransaction = hideDialog(TAG_SORT_DIALOG)
            SortDialog().apply {
                onSortNameAscData = {
                    truckAdapter.sort(allTrucks, ASC, PATH_NAME)
                }
                onSortNameDescData = {
                    truckAdapter.sort(allTrucks, DESC, PATH_NAME)
                }
                onSortDateAscData = {
                    truckAdapter.sort(allTrucks, ASC, PATH_DATE)
                }
                onSortDateDescData = {
                    truckAdapter.sort(allTrucks, DESC, PATH_DATE)
                }
                onSortLicenseAscData = {
                    truckAdapter.sort(allTrucks, ASC, PATH_LICENSE)
                }
                onSortLicenseDescData = {
                    truckAdapter.sort(allTrucks, DESC, PATH_LICENSE)
                }
            }.show(fragmentTransaction, TAG_SORT_DIALOG)
        }

    }

    // Method 12
    private fun hideDialog(strTag: String): FragmentTransaction {
        val ft = parentFragmentManager.beginTransaction()
        val prev = parentFragmentManager.findFragmentByTag(strTag)
        if (prev != null) {
            (prev as DialogFragment).dismiss()
            ft.remove(prev)
        }
        return ft
    }

    companion object {
        const val TAG_FILTER_DIALOG = "TAG_FILTER_DIALOG"
        const val TAG_SORT_DIALOG = "TAG_SORT_DIALOG"
    }
}