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

    private ListItem mListItemListener;

    private Context mContext;
    private List<SingleJobModel> mJobsList;


    public ListAdapter(List<SingleJobModel> mJobsList, Context mContext) {
        this.mListItemListener = null;
        this.mJobsList = mJobsList;
        this.mContext = mContext;
    }

    public void update(List<SingleJobModel> newList) {
        this.mJobsList.clear();
        this.mJobsList.addAll(newList);
        notifyDataSetChanged();
    }


    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from
                (parent.getContext()).inflate(
                R.layout.job_list_tile, parent,
                false);


        return new JobViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {

        holder.mBtnMore.setOnClickListener(v -> {
            if (mListItemListener != null)
                mListItemListener.onJobSelected(holder.itemView, mJobsList.get(position));
        });
        holder.bind(mJobsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mJobsList.size();
    }

    public ListItem getListItemListener() {
        return mListItemListener;
    }

    public ListAdapter setListItemListener(ListItem listItemListener) {
        this.mListItemListener = listItemListener;
        return this;
    }

    public interface ListItem {
        void onJobSelected(View v, SingleJobModel selected);
    }

    class JobViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_title)
        TextView mTxtTitle;

        @BindView(R.id.txt_category)
        TextView mTxtCategory;

        @BindView(R.id.txt_company_name)
        TextView mTxtCompanyName;


        @BindView(R.id.txt_location)
        TextView mTxtLocation;

        @BindView(R.id.txt_publication_date)
        TextView mTxtDate;

        @BindView(R.id.txt_salary)
        TextView mTxtSalary;

        @BindView(R.id.txt_type)
        TextView mTxtType;

        @BindView(R.id.imageViewSalary)
        AppCompatImageView mIvSalary;

        @BindView(R.id.imageViewType)
        AppCompatImageView mIvType;

        @BindView(R.id.imageViewLocation)
        AppCompatImageView mIvLocation;

        @BindView(R.id.btnMore)
        AppCompatButton mBtnMore;

        Context mContext;

        JobViewHolder(@NonNull View itemView, Context c) {
            super(itemView);
            mContext = c;
            ButterKnife.bind(this, itemView);
        }

        public void bind(SingleJobModel jobModel) {
            mTxtCompanyName.setText(jobModel.getCompany_name());
            mTxtTitle.setText(jobModel.getTitle());
            mTxtCategory.setText(jobModel.getCategory());

            mTxtDate.setText(UtilHelper.getDate(jobModel.getPublication_date()));
            UtilHelper.checkIsEmpty(mContext, jobModel.getSalary(), mTxtSalary, mIvSalary);
            UtilHelper.checkIsEmpty(mContext, jobModel.getCandidate_required_location(), mTxtLocation, mIvLocation);
            UtilHelper.checkIsEmpty(mContext, UtilHelper.getType(jobModel.getJob_type()), mTxtType, mIvType);

        }

    }
}