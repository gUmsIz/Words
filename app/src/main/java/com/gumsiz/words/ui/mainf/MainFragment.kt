package com.gumsiz.words.ui.mainf

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gumsiz.words.R
import com.gumsiz.words.data.db.WordsDatabase
import com.gumsiz.words.data.utils.Status
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
        viewModel.data.observe(viewLifecycleOwner, Observer {
            adapter= MainWordAdapter(it)
            binding.recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()

        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.about->{
                val alertDialog:AlertDialog?=activity?.let {
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setMessage(getString(R.string.txt_about))
                    builder.setPositiveButton("OK",DialogInterface.OnClickListener{dialog, which ->
                        dialog?.dismiss()
                    })
                        .setNeutralButton("d-seite.de",DialogInterface.OnClickListener{dialog, which ->
                            val intent=Intent(Intent.ACTION_VIEW, Uri.parse("http://www.d-seite.de/vis/hinweise.html"))
                            startActivity(intent)
                        })
                    builder.create()
                }
                alertDialog?.show()
                true
            }
            R.id.update->{
                viewModel.update()
                true
            }
            else->super.onOptionsItemSelected(item)}
    }
}
