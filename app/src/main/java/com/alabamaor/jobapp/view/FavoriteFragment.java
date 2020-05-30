package com.alabamaor.jobapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.alabamaor.jobapp.R;
import com.alabamaor.jobapp.viewModel.FavoriteViewModel;

import butterknife.BindView;


public class FavoriteFragment extends Fragment {

    private FavoriteViewModel favoriteViewModel;

    @BindView(R.id.text_favorite)
    TextView textView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        favoriteViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);

        View root = inflater.inflate(R.layout.fragment_favorite, container, false);


        favoriteViewModel.getText().observe(getViewLifecycleOwner(), s -> textView.setText(s));
        return root;
    }
}
