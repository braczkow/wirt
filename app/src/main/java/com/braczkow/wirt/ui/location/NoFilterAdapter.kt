package com.braczkow.wirt.ui.location

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter
import androidx.annotation.LayoutRes
import timber.log.Timber

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