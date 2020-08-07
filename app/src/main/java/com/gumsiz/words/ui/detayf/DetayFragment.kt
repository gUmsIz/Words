package com.gumsiz.words.ui.detayf


import android.R.drawable.btn_star_big_off
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.UnderlineSpan
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.gson.Gson
import com.gumsiz.words.R
import com.gumsiz.words.data.Word
import com.gumsiz.words.data.db.WordDB
import com.gumsiz.words.data.db.WordsDatabase
import com.gumsiz.words.data.db.toWord
import com.gumsiz.words.data.toWordDB
import com.gumsiz.words.databinding.DetayFragmentBinding
import java.util.*


class DetayFragment : Fragment() {

    private lateinit var viewModel: DetayViewModel
    private lateinit var viewModelFactory: DetayViewModelFactory
    private lateinit var word: Word
    private lateinit var wordDB: WordDB
    private lateinit var menu: Menu

    private var _binding: DetayFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetayFragmentBinding.inflate(inflater, container, false)
        val db = WordsDatabase.getInstance(this.requireActivity().application).WordsDAO
        viewModelFactory = DetayViewModelFactory(db, requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetayViewModel::class.java)

        val args = arguments?.getString("word")
        val gson = Gson()
        word = gson.fromJson(args, Word::class.java)
        viewModel.getWord(word.name).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            wordDB = it
            setUI(it.toWord())
        })


        binding.translateButton.setOnClickListener {
            val wordJson = Gson()
            it.findNavController().navigate(
                DetayFragmentDirections.actionDetayFragmentToTranslationFragment(
                    wordJson.toJson(wordDB.toWord()).toString()
                )
            )
        }

        viewModel.favUpdate.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it == 1) {
                menu.getItem(0).setIcon(android.R.drawable.btn_star_big_on)
            } else {
                menu.getItem(0).setIcon(btn_star_big_off)
            }

        })



        return binding.root
    }


    fun setUI(word: Word) {
        binding.nameTxt.text = word.name.toUpperCase(Locale.getDefault())
        if (word.firstSg!!.trim().isNotEmpty()) {
            val ss = SpannableStringBuilder("Ich ")
            ss.append(word.firstSg)
            ss.setSpan(
                UnderlineSpan(),
                ss.length - word.firstSg.length,
                ss.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            binding.cekim1Txt.text = ss
        }
        if (word.secondSg!!.trim().isNotEmpty()) {
            val ss = SpannableStringBuilder("Du ")
            ss.append(word.secondSg)
            ss.setSpan(
                UnderlineSpan(),
                ss.length - word.secondSg.length,
                ss.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            binding.cekim2Txt.text = ss
        }
        if (word.imp!!.trim().isNotEmpty()) {
            val ss = SpannableStringBuilder(word.imp)

            ss.setSpan(UnderlineSpan(), 0, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            ss.append("  (Imperativ)")
            binding.impTxt.text = ss
        }
        if (word.pret!!.trim().isNotEmpty()) {
            val ss = SpannableStringBuilder("Ich / Er-Sie-Es ")
            ss.append(word.pret)
            ss.setSpan(
                UnderlineSpan(),
                ss.length - word.pret.length,
                ss.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            ss.append(" (Prät.)")
            binding.pretTxt.text = ss
        }
        if (word.perfSg!!.trim().isNotEmpty()) {
            val ss = SpannableStringBuilder("Ich ")
            ss.append(word.perfSg)
            ss.setSpan(
                UnderlineSpan(),
                ss.length - word.perfSg.length,
                ss.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            ss.append(" (Perf.)")
            binding.perfTxt.text = ss
        }
        if (word.konj2FSg!!.trim().isNotEmpty()) {
            val ss = SpannableStringBuilder(word.konj2FSg)

            ss.setSpan(UnderlineSpan(), 0, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            ss.append("  (Konjunktiv 2)")
            binding.konjTxt.text = ss
        }
        binding.translationLayout.removeAllViews()
        binding.translationLayout.addView(binding.translateButton)
        binding.strukturLayout.removeAllViews()
        binding.beispielLayout.removeAllViews()
        for (i in word.translation) {
            val text = TextView(this.requireContext())
            text.text = "- " + Html.fromHtml(i)
            text.textSize = 22F
            text.setTextColor(Color.BLACK)
            text.setTypeface(null, Typeface.ITALIC)
            binding.translationLayout.addView(text)
        }
        for (i in word.structure!!) {
            val text = TextView(this.requireContext())
            text.text = "- " + Html.fromHtml(i)
            text.textSize = 22F
            text.setTextColor(Color.BLACK)
            text.setTypeface(null, Typeface.ITALIC)
            binding.strukturLayout.addView(text)
        }
        for (i in word.sampleSentence!!) {
            val text = TextView(this.requireContext())
            text.textSize = 22F
            val text1 = TextView(this.requireContext())
            if (i!!.trim().isNotEmpty()) {
                val word = i?.trim().replace("&#8211;", " - ", false)
                text.text = "* " + word
                text1.text = ""
                binding.beispielLayout.addView(text)
                binding.beispielLayout.addView(text1)
            }

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detay_menu, menu)
        this.menu = menu
        if (word.favorite) {
            menu.getItem(0).setIcon(android.R.drawable.btn_star_big_on)
        } else {
            menu.getItem(0).setIcon(android.R.drawable.btn_star_big_off)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.fav_menu_btn -> {
                if (word.favorite) word.favorite = false else word.favorite = true
                viewModel.addFav(word.toWordDB())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
