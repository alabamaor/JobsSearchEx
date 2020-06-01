package com.alabamaor.jobapp.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.alabamaor.jobapp.R;
import com.alabamaor.jobapp.viewModel.JobViewModel;
import com.alabamaor.jobapp.viewModel.SendCvViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;
import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;
import static androidx.core.content.PermissionChecker.checkSelfPermission;

public class SendCVFragment extends Fragment implements View.OnClickListener {

    private static final int PERMISSIONS_REQUEST_CODE = 0;
    private static final int FILE_PICKER_REQUEST_CODE = 1;
    private static final String PATH = "/storage/emulated/0/Download/";

    @BindView(R.id.txt_apply_title)
    TextView txtTitle;

    @BindView(R.id.txtFileName)
    TextView txtFileName;

    @BindView(R.id.textInputEditText)
    TextInputEditText textInputEditText;

    @BindView(R.id.btnSend)
    AppCompatButton btnSend;

    @BindView(R.id.btnUpload)
    AppCompatButton btnUpload;

    private SendCvViewModel mViewModel;

    public static SendCVFragment newInstance() {
        return new SendCVFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.send_cv_fragment, container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(SendCvViewModel.class);

        if (getArguments() != null) {
            mViewModel.selectedJob.setValue(SendCVFragmentArgs.fromBundle(getArguments()).getJob());
        }
        observe();

        txtFileName.setText("");
        btnSend.setOnClickListener(this);
        btnUpload.setOnClickListener(this);

    }

    private void observe() {
        mViewModel.selectedJob.observe(getViewLifecycleOwner(), jobModel -> {
            if (jobModel != null) {
                txtTitle.setText("Applying for " + jobModel.getTitle() + " at " + jobModel.getCompany_name());
            }
        });

        mViewModel.cvPath.observe(getViewLifecycleOwner(), path -> {
            if (path != null) {
                txtFileName.setText(path);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSend:
                if (isValid()) {
                    sendEmail();
                } else {
                    Toast.makeText(getActivity(), getString(R.string.msg_error), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnUpload:
                checkPermissionsAndOpenFilePicker();
                break;

        }
    }

    private boolean isValid() {
        if (txtFileName.getText().toString().isEmpty() ||
             textInputEditText.getText().toString().isEmpty()){
            return false;
        }
        return true;
    }

    private void checkPermissionsAndOpenFilePicker() {
        boolean permissionGranted = checkSelfPermission(requireContext(), READ_EXTERNAL_STORAGE) == PERMISSION_GRANTED;

        if (permissionGranted) {
            openFilePicker();
        } else {
            if (shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)) {
                showError();
            } else {
                requestPermissions(new String[]{READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_CODE);
            }
        }
    }

    private void showError() {
        Toast.makeText(getActivity(), R.string.permission_msg, Toast.LENGTH_SHORT).show();
    }

    private void openFilePicker() {

        new MaterialFilePicker()
                .withSupportFragment(this)
                .withRequestCode(FILE_PICKER_REQUEST_CODE)
                .withHiddenFiles(true)
                .withPath(PATH)
                .start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && requestCode == PERMISSIONS_REQUEST_CODE) {
            openFilePicker();
        } else {
            showError();
        }
    }

    @SuppressLint("IntentReset")
    void sendEmail() {
        String[] TO = {"alabama.or@gmail.com"};
        String[] CC = {""};

        File file = new File(mViewModel.cvPath.getValue());

        Uri uri = FileProvider.getUriForFile(
                getActivity(),
                "com.alabamaor.jobapp.provider", //(use your app signature + ".provider" )
                file);

        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, txtTitle.getText());
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Applying for: job ID: "
                +mViewModel.selectedJob.getValue().getId()+". See CV attached. Thanks for opportunity, "+textInputEditText.getText().toString());

        try {
            startActivity(Intent.createChooser(emailIntent, getString(R.string.send_email)));

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), R.string.no_email_installed, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            assert data != null;
            String path = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);

            if (path != null) {
                mViewModel.cvPath.setValue(path);
            }
        }
    }


}