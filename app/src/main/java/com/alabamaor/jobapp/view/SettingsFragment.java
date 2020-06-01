package com.alabamaor.jobapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.alabamaor.jobapp.R;
import com.alabamaor.jobapp.viewModel.SettingsViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SettingsFragment extends Fragment implements AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    @BindView(R.id.spinnerCategory)
    Spinner spinnerCategory;

    @BindView(R.id.checkBoxCategory)
    AppCompatCheckBox checkBoxCategory;


    @BindView(R.id.btnFilterStart)
    AppCompatButton btnFilter;


    ArrayAdapter aa;

    private SettingsViewModel mViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);
        aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, new ArrayList());
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(aa);
        spinnerCategory.setOnItemSelectedListener(this);

        checkBoxCategory.setOnCheckedChangeListener(this);
        btnFilter.setOnClickListener(this);
        observeData();

        if (mViewModel.position.getValue() != null) {
            aa.notifyDataSetChanged();
            spinnerCategory.setSelection(mViewModel.position.getValue());
        }
    }

    private void observeData() {

        mViewModel.categoriesList.observe(getViewLifecycleOwner(), list -> {
            if (list != null) {
                aa.clear();
                aa.addAll(mViewModel.getCategoryList());
                aa.notifyDataSetChanged();
            }
        });
        mViewModel.isFilterCategories.observe(getViewLifecycleOwner(), isCategories -> {
            if (isCategories != null) {
                checkBoxCategory.setChecked(isCategories);
                spinnerCategory.setEnabled(isCategories);
            }
        });
        mViewModel.position.observe(getViewLifecycleOwner(), position -> {
            if (position != null) {
                spinnerCategory.setSelection(position);

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mViewModel.position.setValue(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mViewModel.isFilterCategories.setValue(isChecked);
        if (isChecked) {
            mViewModel.init();
        }
    }

    @Override
    public void onClick(View v) {
        String categoryName = "";
        if (checkBoxCategory.isChecked()) {
            categoryName = mViewModel.categoriesList.getValue().get(
                    mViewModel.position.getValue()
            ).getSlug();
        }

        NavDirections action = SettingsFragmentDirections.actionNavigationSettingsToViewPagerFragment(categoryName);
        Navigation.findNavController(v).navigate(action);
    }
}
