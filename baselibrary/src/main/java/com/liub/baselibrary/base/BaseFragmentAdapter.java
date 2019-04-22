package com.liub.baselibrary.base;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by liub on 2019/4/4
 * Describe:
 */
public class BaseFragmentAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentList;
    private List<String> mTitles;

    public BaseFragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> stringList) {
        super(fm);
        this.fragmentList = fragments;
        this.mTitles = stringList;
        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
        }
        if (null == mTitles) {
            mTitles = new ArrayList<>();
        }
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
