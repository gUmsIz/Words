package com.gumsiz.words.ui.detayf

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.get
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.gumsiz.words.R
import com.gumsiz.words.data.Word
import com.gumsiz.words.data.db.WordsDatabase
import com.gumsiz.words.data.toWordDB
import com.gumsiz.words.databinding.TranslationFragmentBinding

class TranslationFragment : DialogFragment(){

    private lateinit var viewModel: DetayViewModel
    private lateinit var viewModelFactory: DetayViewModelFactory
    lateinit var  _binding: TranslationFragmentBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = WordsDatabase.getInstance(this.requireActivity().application).WordsDAO
        viewModelFactory = DetayViewModelFactory(db,requireActivity().application)
        viewModel = ViewModelProvider(this,viewModelFactory).get(DetayViewModel::class.java)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            _binding = TranslationFragmentBinding.inflate(LayoutInflater.from(requireContext()))
            val builder=AlertDialog.Builder(it)
            val args=TranslationFragmentArgs.fromBundle(requireArguments())
            val gson=Gson()
            val word=gson.fromJson(args.word,Word::class.java)
            if (word.translation.size>0){
                for (translation in word.translation){
                    val editText=EditText(requireContext())
                    editText.setText(" "+translation)
                    editText.setBackgroundResource(R.drawable.custom_edittext)
                    binding.translationLayout.addView(editText)
                }
                val editText=EditText(requireContext())
                editText.setHint(R.string.btn_translation)
                editText.setBackgroundResource(R.drawable.custom_edittext)
                binding.translationLayout.addView(editText)
            }else{
                val editText=AppCompatEditText(requireContext())
                editText.setHint(R.string.btn_translation)
                editText.setBackgroundResource(R.drawable.custom_edittext)
                binding.translationLayout.addView(editText)
            }
            builder.setView(binding.root)
                .setPositiveButton(R.string.btn_save,DialogInterface.OnClickListener{dialog, id ->
                    word.translation.clear()
                    for (i in 0 until binding.translationLayout.childCount){
                        val ed=binding.translationLayout.get(i) as EditText
                        if (ed.text.toString().trim().length > 0){ word.translation.add(ed.text.toString().trim())}
                    }
                    viewModel.doAction(word.toWordDB())
                    dialog?.cancel()
                })
            builder.create()
        }?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onStart() {
        super.onStart()
        (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.primaryDarkColor))
    }
}