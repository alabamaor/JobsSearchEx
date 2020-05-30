package com.alabamaor.jobapp.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alabamaor.jobapp.R;
import com.alabamaor.jobapp.model.SingleJobModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.JobViewHolder> {

    private ListItem listItemListener;

    private List<SingleJobModel> jobsList;


    public ListAdapter(List<SingleJobModel> jobsList) {
        this.listItemListener = null;
        this.jobsList = jobsList;
    }

    public void update(List<SingleJobModel> newCountries) {
        this.jobsList.clear();
        this.jobsList.addAll(newCountries);
        notifyDataSetChanged();
    }


    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from
                (parent.getContext()).inflate(
                R.layout.job_list_tile, parent,
                false);


        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {

        holder.itemView.setOnClickListener(v -> {
            if (listItemListener != null)
                listItemListener.onJobSelected(holder.itemView, jobsList.get(position));
        });
        holder.bind(jobsList.get(position));
    }

    @Override
    public int getItemCount() {
        return jobsList.size();
    }

    public ListItem getListItemListener() {
        return listItemListener;
    }

    public ListAdapter setListItemListener(ListItem listItemListener) {
        this.listItemListener = listItemListener;
        return this;
    }

    public interface ListItem {
        void onJobSelected(View v, SingleJobModel selected);
    }

    class JobViewHolder extends RecyclerView.ViewHolder {

//
//        @BindView(R.id.TileTvCountryEnglishName)
//        TextView englishName;
//        @BindView(R.id.TileTvCountryNativeName)
//        TextView nativeName;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(SingleJobModel jobModel) {
//            englishName.setText(countryModel.getName());
//            nativeName.setText(countryModel.getNativeName());
        }
    }
}

