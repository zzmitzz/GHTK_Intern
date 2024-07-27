package com.example.ghtk_intern_week2.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ghtk_intern_week2.ui.screen.account.AchievementFragment
import com.example.ghtk_intern_week2.ui.screen.account.PathFragment

class AccountViewPagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return AchievementFragment()
            1 -> return PathFragment()
        }
        return AchievementFragment()
    }
}