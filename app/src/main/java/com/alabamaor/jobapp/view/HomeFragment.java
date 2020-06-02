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


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.mainPbLoading)
    ProgressBar mProgressBar;

    @BindView(R.id.mainTvErrorMsg)
    TextView mMainTvErrorMsg;

    private ListAdapter adapter;
    private HomeViewModel mViewModel;


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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        if (mViewModel.mJobsList.getValue() == null) {
            mViewModel.init();
        }

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mViewModel.init();
            mSwipeRefreshLayout.setRefreshing(false);
        });


        adapter = new ListAdapter(new ArrayList<>(), getContext());
        adapter.setListItemListener(this);
        mRecyclerView.setAdapter(adapter);

        observe();
    }


    private void observe() {
        mViewModel.mJobsList.observe(getViewLifecycleOwner(), jobList -> {
            if (jobList != null) {
                adapter.update(jobList);
            }
        });

        mViewModel.mJobsList.observe(getViewLifecycleOwner(), jobList -> {
            if (jobList != null) {
                mRecyclerView.setVisibility(View.VISIBLE);
                adapter.update(jobList);
                mMainTvErrorMsg.setVisibility(View.INVISIBLE);
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });

        mViewModel.mIsLoading.observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading != null) {
                if (isLoading) {
                    mRecyclerView.setVisibility(View.INVISIBLE);
                    mMainTvErrorMsg.setVisibility(View.INVISIBLE);
                    mProgressBar.setVisibility(View.VISIBLE);
                }
            }
        });
        mViewModel.mHasError.observe(getViewLifecycleOwner(), loadError -> {
            if (loadError != null) {
                if (loadError) {
                    mRecyclerView.setVisibility(View.INVISIBLE);
                    mMainTvErrorMsg.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.INVISIBLE);
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
