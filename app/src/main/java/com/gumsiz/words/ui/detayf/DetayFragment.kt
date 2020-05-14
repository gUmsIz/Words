package com.gumsiz.words.ui.detayf


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gumsiz.words.databinding.DetayFragmentBinding


class DetayFragment : Fragment() {

    companion object {
        fun newInstance() = DetayFragment()
    }

    private lateinit var viewModel: DetayViewModel

    private var _binding: DetayFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetayFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetayViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
