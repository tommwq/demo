package com.tq.alarm.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tq.alarm.ClockFragment
import com.tq.alarm.R
import com.tq.alarm.PlaceholderFragment

private val tab_titles = arrayOf(
    R.string.tab_clock,
    R.string.tab_downcount_timer,
    R.string.tab_stopwatch,
    R.string.tab_alarm
)


class TabPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (tab_titles[position]) {
            R.string.tab_clock -> ClockFragment()
            else-> PlaceholderFragment.newInstance(position + 1)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(tab_titles[position])
    }

    override fun getCount(): Int {
        return tab_titles.size
    }
}