package com.navkiraninfotech.assignment.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.navkiraninfotech.assignment.ui.fragments.appListFragment.AppListFragment
import com.navkiraninfotech.assignment.ui.fragments.settingFragment.SettingFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                AppListFragment()
            }
            1 -> {
                SettingFragment()
            }
            else -> {
                AppListFragment()
            }
        }
    }

}