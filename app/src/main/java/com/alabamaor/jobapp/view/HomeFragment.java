package com.alabamaor.jobapp.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.alabamaor.jobapp.MainActivity;
import com.alabamaor.jobapp.R;
import com.alabamaor.jobapp.viewModel.HomeViewModel;
import com.alabamaor.jobapp.viewModel.SharedViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    @BindView(R.id.viewPager2)
    ViewPager2 viewPager;

    private JobViewSliderAdapter adapter;

    private SharedViewModel sharedViewModel;
    private HomeViewModel homeViewModel;



    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, inflate);

        Log.i("ALABAMA->", "Fragment onCreateView");
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.i("ALABAMA->", "Fragment onViewCreated");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("ALABAMA->", "Fragment onStop");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("ALABAMA->", "Fragment onStart");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.i("ALABAMA->", "Fragment onActivityCreated");
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        if (sharedViewModel.getJobList().getValue() == null)
            sharedViewModel.init();

        homeViewModel = new ViewModelProvider((MainActivity) getActivity()).get(HomeViewModel.class);

        adapter = new JobViewSliderAdapter(getContext(), new ArrayList<>(), viewPager);
        viewPager.setAdapter(adapter);

        viewPager.setPageTransformer(new DepthPageTransformer());

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        });
        observe();
    }

    private void observe() {
        sharedViewModel.getJobList().observe(getViewLifecycleOwner(), jobList -> {
            if (jobList != null) {
                adapter.update(jobList);
            }
        });
    }
}
