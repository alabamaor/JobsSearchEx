package com.alabamaor.jobapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.alabamaor.jobapp.R;
import com.alabamaor.jobapp.model.SingleJobModel;
import com.alabamaor.jobapp.model.UtilHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.JobViewHolder> {

    private ListItem listItemListener;

    private Context context;
    private List<SingleJobModel> jobsList;


    public ListAdapter(List<SingleJobModel> jobsList, Context context) {
        this.listItemListener = null;
        this.jobsList = jobsList;
        this.context = context;
    }

    public void update(List<SingleJobModel> newList) {
        this.jobsList.clear();
        this.jobsList.addAll(newList);
        notifyDataSetChanged();
    }


    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from
                (parent.getContext()).inflate(
                R.layout.job_list_tile, parent,
                false);


        return new JobViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {

        holder.btnMore.setOnClickListener(v -> {
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

        @BindView(R.id.txt_title)
        TextView txtTitle;

        @BindView(R.id.txt_category)
        TextView txtCategory;

        @BindView(R.id.txt_company_name)
        TextView txtCompanyName;


        @BindView(R.id.txt_location)
        TextView txtLocation;

        @BindView(R.id.txt_publication_date)
        TextView txtDate;

        @BindView(R.id.txt_salary)
        TextView txtSalary;

        @BindView(R.id.txt_type)
        TextView txtType;


        @BindView(R.id.imageViewSalary)
        AppCompatImageView ivSalary;

        @BindView(R.id.imageViewType)
        AppCompatImageView ivType;

        @BindView(R.id.imageViewLocation)
        AppCompatImageView ivLocation;

        @BindView(R.id.btnMore)
        AppCompatButton btnMore;

        Context context;

        JobViewHolder(@NonNull View itemView, Context c) {
            super(itemView);
            context = c;
            ButterKnife.bind(this, itemView);
        }

        public void bind(SingleJobModel jobModel) {
            txtCompanyName.setText(jobModel.getCompany_name());
            txtTitle.setText(jobModel.getTitle());
            txtCategory.setText(jobModel.getCategory());

            txtDate.setText(UtilHelper.getDate(jobModel.getPublication_date()));
            UtilHelper.checkIsEmpty(context, jobModel.getSalary(), txtSalary, ivSalary);
            UtilHelper.checkIsEmpty(context, jobModel.getCandidate_required_location(), txtLocation, ivLocation);
            UtilHelper.checkIsEmpty(context, UtilHelper.getType(jobModel.getJob_type()), txtType, ivType);

        }

    }
}