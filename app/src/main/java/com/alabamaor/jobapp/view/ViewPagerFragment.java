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
    ViewPager2 viewPager;

    @BindView(R.id.mainPbLoadingVp)
    ProgressBar mainPbLoading;

    @BindView(R.id.mainTvErrorMsgVp)
    TextView mainTvErrorMsg;


    private JobViewSliderAdapter adapter;


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

        adapter = new JobViewSliderAdapter(getContext(), new ArrayList<>(), viewPager);
        adapter.setListItemListener(this);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(new DepthPageTransformer());

        if (getArguments() != null) {
            mViewModel.getJobs(ViewPagerFragmentArgs.fromBundle(getArguments()).getCategoryName());
        }
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
                viewPager.setVisibility(View.VISIBLE);
                adapter.update(jobList);
                mainTvErrorMsg.setVisibility(View.INVISIBLE);
                mainPbLoading.setVisibility(View.INVISIBLE);
            }
        });

        mViewModel.mIsLoading.observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading != null) {
                if (isLoading) {
                    viewPager.setVisibility(View.INVISIBLE);
                    mainTvErrorMsg.setVisibility(View.INVISIBLE);
                    mainPbLoading.setVisibility(View.VISIBLE);
                }
            }
        });
        mViewModel.mHasError.observe(getViewLifecycleOwner(), loadError -> {
            if (loadError != null) {
                if (loadError) {
                    viewPager.setVisibility(View.INVISIBLE);
                    mainTvErrorMsg.setVisibility(View.VISIBLE);
                    mainPbLoading.setVisibility(View.INVISIBLE);
                }
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