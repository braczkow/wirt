package com.braczkow.wirt

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Filter
import androidx.annotation.LayoutRes
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.braczkow.wirt.openweather.WillItRainUseCase
import com.braczkow.wirt.utils.tillStopScope
import com.braczkow.wirt.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.android.synthetic.main.fragment_location.*
import kotlinx.android.synthetic.main.fragment_location.view.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@OptIn(ExperimentalCoroutinesApi::class)
class LocationFragmentViewModel @ViewModelInject constructor(
    @ApplicationContext private val context: Context,
    private val willItRainUseCase: WillItRainUseCase
) : ViewModel() {

    fun onUseLocationButtonClicked() {
    }

    fun onLocationNameChanged(s: String) {
        Timber.d("onLocationNameChanged")
        (suggestionsFlow as MutableStateFlow).value = listOf(
            "${s}aa",
            "$s $s"
        )

        Handler().postDelayed({
            (suggestionsFlow as MutableStateFlow).value = listOf(
                "${s}aa",
                "$s $s",
                "$s olaboga"
            )
        }, 3000)


    }

    val suggestionsFlow: Flow<List<String>> = MutableStateFlow(listOf("initial one", "initial two"))
}

class NoFilterAdapter<T>(context: Context, @LayoutRes layout: Int) :
    ArrayAdapter<T>(context, layout) {
    var items = listOf<T>()
    set(value) {
        field = value

        this.clear()
        this.addAll(value)
        notifyDataSetChanged()
    }



    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val result = FilterResults()
                result.values = this@NoFilterAdapter.items
                result.count = this@NoFilterAdapter.items.count()

                Timber.d("returning result: ${result}")
                return result
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                Timber.d("publishing result")
                notifyDataSetChanged()
            }

        }
    }
}

@AndroidEntryPoint
class LocationFragment : Fragment() {
    val viewModel by viewModels<LocationFragmentViewModel>()

    val adapter: NoFilterAdapter<String> by lazy {
        NoFilterAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val result = inflater.inflate(R.layout.fragment_location, container, false)

        result.location_location_edit.addTextChangedListener(
            afterTextChanged = { editable ->
                editable?.let { viewModel.onLocationNameChanged(editable.toString()) }
            }
        )

        result.location_use_gps.setOnClickListener {
            viewModel.onUseLocationButtonClicked()
        }

        viewModel.onUseLocationButtonClicked()

        return result
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<AutoCompleteTextView>(R.id.location_location_edit).run {
            addTextChangedListener(
                afterTextChanged = { editable ->
                    editable?.let { viewModel.onLocationNameChanged(editable.toString()) }
                }
            )

            setAdapter(this@LocationFragment.adapter)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.d("onDestroyView")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("onStop")
    }

    override fun onStart() {
        super.onStart()

        viewLifecycleOwner
            .lifecycle
            .tillStopScope()
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

        viewModel
            .suggestionsFlow
            .asLiveData()
            .observe(this) {

            }
    }
}
