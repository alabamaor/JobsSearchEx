package com.alabamaor.jobapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.alabamaor.jobapp.R;
import com.alabamaor.jobapp.viewModel.HomeViewModel;
import com.alabamaor.jobapp.viewModel.SharedViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    @BindView(R.id.view_pager)
    ViewPager2 viewPager;

    private SharedViewModel sharedViewModel;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ButterKnife.bind(this, getActivity());

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        sharedViewModel.init();
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        observe();
        return root;
    }

    private void observe() {
        sharedViewModel.getMoviesList().observe(getViewLifecycleOwner(), jobList -> {
            if (jobList != null){

            }
        });
    }
}
