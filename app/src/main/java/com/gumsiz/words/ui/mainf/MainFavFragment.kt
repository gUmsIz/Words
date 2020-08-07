package com.gumsiz.words.ui.mainf

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gumsiz.words.data.db.WordsDatabase
import com.gumsiz.words.data.utils.Status
import com.gumsiz.words.databinding.MainFavFragmentBinding
import com.gumsiz.words.databinding.MainFragmentBinding

class MainFavFragment : Fragment(){
    // ViewModel impl.
    private lateinit var viewModel: MainViewModel
    private lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var adapter: MainWordAdapter

    //ViewBinding impl.
    private var _binding: MainFavFragmentBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFavFragmentBinding.inflate(inflater, container, false)
        // Room instance
        val db = WordsDatabase.getInstance(this.requireActivity().application).WordsDAO

        adapter = MainWordAdapter(mutableListOf())


        //creation of viewmodel
        viewModelFactory = MainViewModelFactory(db, requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        //observe db data

        viewModel.prepare().observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    binding.loadCard.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.searchView.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {
                    binding.statusText.text = resource.message
                    binding.loadCard.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.searchView.visibility = View.GONE
                }
            }
        })


        //seracview listener
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.favoritedata.observe(viewLifecycleOwner, Observer {
            adapter= MainWordAdapter(it)
            binding.recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()

        })
    }

}