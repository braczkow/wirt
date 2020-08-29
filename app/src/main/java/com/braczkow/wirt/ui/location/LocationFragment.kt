package com.braczkow.wirt.ui.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.braczkow.wirt.ui.common.BaseFragment
import com.braczkow.wirt.utils.startStopScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_location.view.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber


@AndroidEntryPoint
class LocationFragment : BaseFragment() {
    val viewModel by viewModels<LocationFragmentViewModel>()

    val adapter: NoFilterAdapter<String> by lazy {
        NoFilterAdapter<String>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val result = inflater.inflate(com.braczkow.wirt.R.layout.fragment_location, container, false)

        return result
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<AutoCompleteTextView>(com.braczkow.wirt.R.id.location_location_edit).run {
            addTextChangedListener(
                afterTextChanged = { editable ->
                    editable?.let { viewModel.onLocationNameChanged(editable.toString()) }
                }
            )

            setAdapter(this@LocationFragment.adapter)
        }

        view.location_location_edit.addTextChangedListener(
            afterTextChanged = { editable ->
                editable?.let { viewModel.onLocationNameChanged(editable.toString()) }
            }
        )

        view.location_use_gps.setOnClickListener {
            viewModel.onUseLocationButtonClicked()
        }
    }

    override fun onStart() {
        super.onStart()

        this
            .startStopScope()
            .launch {
                viewModel
                    .suggestionsFlow
                    .cancellable()
                    .collect {
                        Timber.d("Got a list: $it")
                        Timber.d("isActive: $isActive")

                        adapter.items = it
                    }
            }

        this
            .startStopScope()
            .launch {
                viewModel
                    .directions
                    .collect {
                        Timber.d("Got direction: $it")
                        when (it) {
                            is LocationFragmentDirections.MoveToSettings -> {
                                requireFragmentNavigation().setSettingsScreen()
                            }
                        }
                    }
            }


    }
}
