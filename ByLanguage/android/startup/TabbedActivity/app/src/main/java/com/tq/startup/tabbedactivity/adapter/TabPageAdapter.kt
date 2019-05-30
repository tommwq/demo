package com.tq.startup.tabbedactivity.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tq.startup.tabbedactivity.*
import com.tq.startup.tabbedactivity.viewmodel.Tab1ViewModel

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class TabPageAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    companion object {
        data class PageDescriptor<T>(val label: Int, val clazz: Class<T>)
    }

    private val pages = arrayOf(
        PageDescriptor(R.string.tab_1_label, Tab1Fragment::class.java),
        PageDescriptor(R.string.tab_2_label, Tab2Fragment::class.java),
        PageDescriptor(R.string.tab_3_label, Tab3Fragment::class.java),
        PageDescriptor(R.string.tab_4_label, Tab4Fragment::class.java)
    )

    override fun getItem(position: Int): Fragment {
        return pages[position].clazz.newInstance()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(pages[position].label)
    }

    override fun getCount(): Int = pages.size
}