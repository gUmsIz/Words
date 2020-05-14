package com.gumsiz.words.ui.mainf

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gumsiz.words.data.db.WordsDatabase
import com.gumsiz.words.databinding.MainFragmentBinding


class MainFragment : Fragment() {
    // ViewModel impl.
    private lateinit var viewModel: MainViewModel
    private lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var adapter: MainWordAdapter

    //ViewBinding impl.
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)

        val db = WordsDatabase.getInstance(this.requireActivity().application).WordsDAO
        viewModelFactory = MainViewModelFactory(db, requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.data.observe(viewLifecycleOwner, Observer {
            adapter = MainWordAdapter(it)
            binding.recyclerView.adapter = adapter
        })
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                adapter.filter.filter(p0)
                return false
            }
        })
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
