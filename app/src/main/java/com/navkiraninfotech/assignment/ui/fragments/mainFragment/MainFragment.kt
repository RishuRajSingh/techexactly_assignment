package com.navkiraninfotech.assignment.ui.fragments.mainFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.navkiraninfotech.assignment.adapter.ViewPagerAdapter
import com.navkiraninfotech.assignment.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)

        binding.mainViewPager.adapter = ViewPagerAdapter(activity?.supportFragmentManager!!, lifecycle)
        TabLayoutMediator(binding.tabLayout, binding.mainViewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Applications"
                1 -> tab.text = "Settings"
            }
        }.attach()
        binding.imgBack.setOnClickListener {
            requireActivity().finish()
        }
        return binding.root
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
            }
    }
}