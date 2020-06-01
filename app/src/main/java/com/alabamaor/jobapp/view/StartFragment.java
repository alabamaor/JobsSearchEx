package com.alabamaor.jobapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.alabamaor.jobapp.R;
import com.alabamaor.jobapp.viewModel.StartViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartFragment extends Fragment implements View.OnClickListener {

    private StartViewModel mViewModel;

    @BindView(R.id.btnFilter)
    AppCompatButton btnFilter;
    @BindView(R.id.btnAll)
    AppCompatButton btnAll;

    public static StartFragment newInstance() {
        return new StartFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.start_fragment, container, false);

        ButterKnife.bind(this, inflate);
        return inflate;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(StartViewModel.class);

        btnAll.setOnClickListener(this);
        btnFilter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        NavDirections action = null;
                switch (v.getId()) {
            case R.id.btnFilter:
                action = StartFragmentDirections.actionStartFragmentToNavigationSettings();
                break;
            case R.id.btnAll:
                 action = StartFragmentDirections.actionToNavigationHome();
                break;
        }
                Navigation.findNavController(v).navigate(action);
    }
}