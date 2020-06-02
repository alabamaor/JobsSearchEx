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
import androidx.viewpager2.widget.ViewPager2;

import com.alabamaor.jobapp.R;
import com.alabamaor.jobapp.model.SingleJobModel;
import com.alabamaor.jobapp.viewModel.ViewPagerViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewPagerFragment extends Fragment implements JobViewSliderAdapter.ViewPagerItem {


    @BindView(R.id.viewPager)
    ViewPager2 mViewPager;

    @BindView(R.id.mainPbLoadingVp)
    ProgressBar mProgressBar;

    @BindView(R.id.mainTvErrorMsgVp)
    TextView mMainTvErrorMsg;

    private JobViewSliderAdapter mAdapter;
    private ViewPagerViewModel mViewModel;

    public static ViewPagerFragment newInstance() {
        return new ViewPagerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.viewpager_fragment, container, false);

        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(ViewPagerViewModel.class);

        if (getArguments() != null && mViewModel.mJobsList.getValue() == null) {
            mViewModel.getJobs(ViewPagerFragmentArgs.fromBundle(getArguments()).getCategoryName());
        }
        mAdapter = new JobViewSliderAdapter(getContext(), new ArrayList<>());
        mAdapter.setListItemListener(this);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setPageTransformer(new DepthPageTransformer());

        if (mViewModel.mPosition.getValue() != null) {
            mViewPager.setCurrentItem(mViewModel.mPosition.getValue());
        }
        observe();
        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mViewModel.mPosition.setValue(position);

            }
        });



    }

    private void observe() {


        mViewModel.mJobsList.observe(getViewLifecycleOwner(), jobList -> {
            if (jobList != null) {
                mAdapter.update(jobList);
            }
        });

        mViewModel.mJobsList.observe(getViewLifecycleOwner(), jobList -> {
            if (jobList != null) {
                mViewPager.setVisibility(View.VISIBLE);
                mAdapter.update(jobList);
                mMainTvErrorMsg.setVisibility(View.INVISIBLE);
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });

        mViewModel.mIsLoading.observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading != null) {
                if (isLoading) {
                    mViewPager.setVisibility(View.INVISIBLE);
                    mMainTvErrorMsg.setVisibility(View.INVISIBLE);
                    mProgressBar.setVisibility(View.VISIBLE);
                }
            }
        });
        mViewModel.mHasError.observe(getViewLifecycleOwner(), loadError -> {
            if (loadError != null) {
                if (loadError) {
                    mViewPager.setVisibility(View.INVISIBLE);
                    mMainTvErrorMsg.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.INVISIBLE);
                }
            }
        });

        mViewModel.mPosition.observe(getViewLifecycleOwner(), position -> {
            if (position != null) {
                mViewPager.setCurrentItem(position);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onJobSelected(View v, SingleJobModel selected) {
        NavDirections action = ViewPagerFragmentDirections.actionViewPagerFragmentToNavigationJob(selected);
        Navigation.findNavController(v).navigate(action);
    }
}