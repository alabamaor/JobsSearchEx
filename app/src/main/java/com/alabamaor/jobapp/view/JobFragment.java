package com.alabamaor.jobapp.view;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.alabamaor.jobapp.R;
import com.alabamaor.jobapp.model.UtilHelper;
import com.alabamaor.jobapp.viewModel.JobViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JobFragment extends Fragment {

    @BindView(R.id.txt_title_f)
    TextView txtTitle;
    @BindView(R.id.txt_category_f)
    TextView txtCategory;
    @BindView(R.id.txt_company_name_f)
    TextView txtCompanyName;
    @BindView(R.id.txt_location_f)
    TextView txtLocation;
    @BindView(R.id.txt_publication_date_f)
    TextView txtDate;
    @BindView(R.id.txt_salary_f)
    TextView txtSalary;
    @BindView(R.id.txt_type_f)
    TextView txtType;
    @BindView(R.id.txt_desc_f)
    TextView txtUrl;
    @BindView(R.id.imageViewSalary_f)
    AppCompatImageView ivSalary;
    @BindView(R.id.imageViewType_f)
    AppCompatImageView ivType;
    @BindView(R.id.imageViewLocation_f)
    AppCompatImageView ivLocation;
    @BindView(R.id.btnApply)
    AppCompatButton btnApply;
    private JobViewModel mViewModel;

    public static JobFragment newInstance() {
        return new JobFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.job_fragment, container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(getActivity()).get(JobViewModel.class);

        if (getArguments() != null) {
            mViewModel.selectedJob.setValue(JobFragmentArgs.fromBundle(getArguments()).getSelectedJob());
        }

        observe();
    }

    private void observe() {
        mViewModel.selectedJob.observe(getViewLifecycleOwner(), jobModel -> {
            if (jobModel != null) {
                txtCompanyName.setText(jobModel.getCompany_name());
                txtTitle.setText(jobModel.getTitle());
                txtCategory.setText(jobModel.getCategory());

                txtDate.setText(UtilHelper.getDate(jobModel.getPublication_date()));
                UtilHelper.checkIsEmpty(getContext(), jobModel.getSalary(), txtSalary, ivSalary);
                UtilHelper.checkIsEmpty(getContext(), jobModel.getCandidate_required_location(), txtLocation, ivLocation);
                UtilHelper.checkIsEmpty(getContext(), UtilHelper.getType(jobModel.getJob_type()), txtType, ivType);

                txtUrl.setText(Html.fromHtml(jobModel.getDescription(),
                        Html.FROM_HTML_MODE_COMPACT));

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnApply.setOnClickListener(v -> {
            Navigation.findNavController(getView()
            ).navigate(JobFragmentDirections.toSendCVFragment(mViewModel.selectedJob.getValue()));
        });
    }

}