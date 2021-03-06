package com.v2cc.im.blah.views.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.v2cc.im.blah.fragments.ChatsListFragment;
import com.v2cc.im.blah.fragments.FragmentTest;
import com.v2cc.im.blah.fragments.FriendsFragment;

import java.util.List;

/**
 * Created by Steve ZHANG (stevzhg@gmail.com)
 * 2015/9/22.
 * If it works, I created this. If not, I didn't.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private String[] titles;
    private List<Fragment> fragments;

    public ViewPagerAdapter(FragmentManager fm, String[] titles, List<Fragment> fragments) {
        super(fm);
        this.titles = titles;
        this.fragments = fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ChatsListFragment.newInstance(position);
            case 1:
                return FriendsFragment.newInstance(position);
            default:
                return FragmentTest.newInstance();
        }
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
