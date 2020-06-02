package com.alabamaor.jobapp.view;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.alabamaor.jobapp.R;
import com.alabamaor.jobapp.model.SingleJobModel;
import com.alabamaor.jobapp.model.UtilHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JobViewSliderAdapter extends RecyclerView.Adapter<JobViewSliderAdapter.ViewHolder> {


    private List<SingleJobModel> mJobsList;
    private Context mContext;
    private ViewPagerItem mListItemListener;

    JobViewSliderAdapter(Context mContext, List<SingleJobModel> data) {
        this.mJobsList = data;
        this.mContext = mContext;
        this.mListItemListener = null;
    }


    public void update(List<SingleJobModel> newList) {
        this.mJobsList.clear();
        this.mJobsList.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from
                (parent.getContext()).inflate(
                R.layout.job_layout, parent,
                false);
        return new ViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListItemListener != null)
                    mListItemListener.onJobSelected(holder.itemView, mJobsList.get(position));
            }
        };

        holder.mView.setOnClickListener(listener);
        holder.mTxtTitle.setOnClickListener(listener);
        holder.mTxtDesc.setOnClickListener(listener);
        holder.mLinearLayoutJ.setOnClickListener(listener);

        holder.bind(mJobsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mJobsList.size();
    }

    public ViewPagerItem getListItemListener() {
        return mListItemListener;
    }

    public JobViewSliderAdapter setListItemListener(ViewPagerItem listItemListener) {
        this.mListItemListener = listItemListener;
        return this;
    }

    public interface ViewPagerItem {
        void onJobSelected(View v, SingleJobModel selected);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.txt_titleJ)
        TextView mTxtTitle;

        @BindView(R.id.txt_categoryJ)
        TextView mTxtCategory;

        @BindView(R.id.txt_company_nameJ)
        TextView mTxtCompanyName;

        @BindView(R.id.txt_locationJ)
        TextView mTxtLocation;

        @BindView(R.id.txt_publication_dateJ)
        TextView mTxtDate;

        @BindView(R.id.txt_salaryJ)
        TextView mTxtSalary;

        @BindView(R.id.txt_typeJ)
        TextView mTxtType;

        @BindView(R.id.txt_descJ)
        TextView mTxtDesc;

        @BindView(R.id.imageViewSalaryJ)
        AppCompatImageView mIvSalary;

        @BindView(R.id.imageViewTypeJ)
        AppCompatImageView mIvType;

        @BindView(R.id.imageViewLocationJ)
        AppCompatImageView mIvLocation;

        @BindView(R.id.linearLayoutJ)
        LinearLayout mLinearLayoutJ;

        Context mContext;
        View mView;

        ViewHolder(@NonNull View itemView, Context c) {
            super(itemView);
            mContext = c;
            mView = itemView;
            ButterKnife.bind(this, itemView);
        }

        public void bind(SingleJobModel jobModel) {


            mTxtCompanyName.setText(jobModel.getCompany_name());
            mTxtTitle.setText(jobModel.getTitle());
            mTxtCategory.setText(jobModel.getCategory());

            mTxtDesc.setText(Html.fromHtml(jobModel.getDescription(),
                    Html.FROM_HTML_MODE_COMPACT));


            mTxtDate.setText(UtilHelper.getDate(jobModel.getPublication_date()));
            UtilHelper.checkIsEmpty(mContext, jobModel.getSalary(), mTxtSalary, mIvSalary);
            UtilHelper.checkIsEmpty(mContext, jobModel.getCandidate_required_location(), mTxtLocation, mIvLocation);
            UtilHelper.checkIsEmpty(mContext, UtilHelper.getType(jobModel.getJob_type()), mTxtType, mIvType);

        }

    }
}