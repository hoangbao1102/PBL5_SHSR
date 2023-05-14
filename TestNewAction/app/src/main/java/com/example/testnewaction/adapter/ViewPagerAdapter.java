package com.example.testnewaction.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.testnewaction.fragement.FragmentAction;
import com.example.testnewaction.fragement.FragmentHome;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new FragmentHome();
            case 1: return new FragmentAction();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
