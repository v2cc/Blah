package com.v2cc.im.blah.base.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.v2cc.im.blah.R;

/**
 * Created by Steve ZHANG (stevzhg@gmail.com)
 * 2015/9/22.
 * If it works, I created this. If not, I didn't.
 */
public class FragmentTest extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    public static FragmentTest newInstance() {
        FragmentTest fragmentTest = new FragmentTest();
        Bundle bundle = new Bundle();
        fragmentTest.setArguments(bundle);
        return fragmentTest;
    }

    @Override
    protected int setRootViewId() {
        return R.layout.frag_test;
    }

    @Override
    public void onRefresh() {

    }
}