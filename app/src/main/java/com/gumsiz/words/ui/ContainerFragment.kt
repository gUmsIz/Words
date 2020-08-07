package com.gumsiz.words.ui

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.*
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.gumsiz.words.R
import com.gumsiz.words.databinding.FragmentContainerBinding
import com.gumsiz.words.databinding.MainFavFragmentBinding
import com.gumsiz.words.ui.mainf.MainFavFragment
import com.gumsiz.words.ui.mainf.MainFragment
import com.google.android.material.tabs.TabLayoutMediator

class ContainerFragment : Fragment() {
    private lateinit var pagerAdapter: MyPagerAdapter
    private var _binding: FragmentContainerBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentContainerBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pagerAdapter=MyPagerAdapter(requireActivity())
        binding.viewPager.adapter=pagerAdapter
        TabLayoutMediator(binding.tabLayout,binding.viewPager){tab, position ->
            tab.text=when(position){
                0->getString(R.string.app_name)
                else->""
            }
            if (position==1){tab.setIcon(android.R.drawable.btn_star_big_on)}
        }.attach()

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
                val alertDialog: AlertDialog?=activity?.let {
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setMessage(getString(R.string.txt_about))
                    builder.setPositiveButton("OK", DialogInterface.OnClickListener{ dialog, which ->
                        dialog?.dismiss()
                    })
                        .setNeutralButton("d-seite.de",
                            DialogInterface.OnClickListener{ dialog, which ->
                            val intent= Intent(Intent.ACTION_VIEW, Uri.parse("http://www.d-seite.de/vis/hinweise.html"))
                            startActivity(intent)
                        })
                    builder.create()
                }
                alertDialog?.show()
                true
            }
            /*R.id.update->{
                viewModel.update()
                true
            }*/
            else->super.onOptionsItemSelected(item)}
    }


}

class MyPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity){
    override fun getItemCount()=2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{ MainFragment()}
            else->{MainFavFragment()}
        }
    }

}