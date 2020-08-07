package com.gumsiz.words.ui.mainf

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.gumsiz.words.R
import com.gumsiz.words.data.Word
import java.util.*

class MainWordAdapter(private var list: List<Word>) : RecyclerView.Adapter<TextItemViewHolder>(),
    Filterable {

    var data = listOf<Word>()
    lateinit var mcontext: Context

    init {
        data = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val view: TextView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.text_item, parent, false) as TextView
        mcontext = parent.context
        return TextItemViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.name
        holder.itemView.setOnClickListener {
            val madap = Gson()
            val bundle= bundleOf("word" to madap.toJson(item))
            it.findNavController().navigate(
                R.id.action_global_detayFragment,bundle
            )
        }
    }

    //filer recyclerview for search characters
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val charSearch = p0.toString()
                if (charSearch.isEmpty()) {
                    data = list
                } else {
                    val resultList = mutableListOf<Word>()
                    for (row in list) {
                        if (row.name.contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    data = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = data
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                data = p1?.values as List<Word>
                notifyDataSetChanged()
            }
        }
    }

    fun addWords(words: List<Word>) {
        this.data.apply {
            data = words
        }
    }

}


class TextItemViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
