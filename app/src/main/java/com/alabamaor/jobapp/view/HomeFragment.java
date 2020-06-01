package com.alabamaor.jobapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alabamaor.jobapp.R;
import com.alabamaor.jobapp.model.SingleJobModel;
import com.alabamaor.jobapp.viewModel.HomeViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment implements ListAdapter.ListItem {

//    @BindView(R.id.viewPager2)
//    ViewPager2 viewPager;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.mainPbLoading)
    ProgressBar mainPbLoading;

    @BindView(R.id.mainTvErrorMsg)
    TextView mainTvErrorMsg;

    //    private JobViewSliderAdapter adapter;
    private ListAdapter adapter;
    private HomeViewModel homeViewModel;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        if (homeViewModel.mJobsList.getValue() == null) {
            homeViewModel.init();
        }

        swipeRefreshLayout.setOnRefreshListener(() -> {
            homeViewModel.init();
            swipeRefreshLayout.setRefreshing(false);
        });


//        adapter = new JobViewSliderAdapter(getContext(), new ArrayList<>(), viewPager);
        adapter = new ListAdapter(new ArrayList<>(), getContext());
        adapter.setListItemListener(this);
        recyclerView.setAdapter(adapter);

        observe();
    }

    private void observe() {
        homeViewModel.mJobsList.observe(getViewLifecycleOwner(), jobList -> {
            if (jobList != null) {
                adapter.update(jobList);
            }
        });

        homeViewModel.mJobsList.observe(getViewLifecycleOwner(), jobList -> {
            if (jobList != null) {
                recyclerView.setVisibility(View.VISIBLE);
                adapter.update(jobList);
                mainTvErrorMsg.setVisibility(View.INVISIBLE);
                mainPbLoading.setVisibility(View.INVISIBLE);
            }
        });

        homeViewModel.mIsLoading.observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading != null) {
                if (isLoading) {
                    recyclerView.setVisibility(View.INVISIBLE);
                    mainTvErrorMsg.setVisibility(View.INVISIBLE);
                    mainPbLoading.setVisibility(View.VISIBLE);
                }
            }
        });
        homeViewModel.mHasError.observe(getViewLifecycleOwner(), loadError -> {
            if (loadError != null) {
                if (loadError) {
                    recyclerView.setVisibility(View.INVISIBLE);
                    mainTvErrorMsg.setVisibility(View.VISIBLE);
                    mainPbLoading.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    @Override
    public void onJobSelected(View v, SingleJobModel selected) {
        NavDirections action = HomeFragmentDirections.toNavigationJob(selected);
        Navigation.findNavController(v).navigate(action);
    }
}
