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
import androidx.viewpager2.widget.ViewPager2;

import com.alabamaor.jobapp.R;
import com.alabamaor.jobapp.model.SingleJobModel;
import com.alabamaor.jobapp.model.UtilHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JobViewSliderAdapter extends RecyclerView.Adapter<JobViewSliderAdapter.ViewHolder> {


    private List<SingleJobModel> mJobsList;
    private LayoutInflater mInflater;
    private ViewPager2 viewPager2;
    private Context context;
    private ViewPagerItem listItemListener;

    JobViewSliderAdapter(Context context, List<SingleJobModel> data, ViewPager2 viewPager2) {
        this.mInflater = LayoutInflater.from(context);
        this.mJobsList = data;
        this.viewPager2 = viewPager2;
        this.context = context;
        this.listItemListener = null;
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
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listItemListener != null)
                    listItemListener.onJobSelected(holder.itemView, mJobsList.get(position));
            }
        };

        holder.view.setOnClickListener(listener);
        holder.txtTitle.setOnClickListener(listener);
        holder.txtDesc.setOnClickListener(listener);
        holder.linearLayoutJ.setOnClickListener(listener);

        holder.bind(mJobsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mJobsList.size();
    }

    public ViewPagerItem getListItemListener() {
        return listItemListener;
    }

    public JobViewSliderAdapter setListItemListener(ViewPagerItem listItemListener) {
        this.listItemListener = listItemListener;
        return this;
    }

    public interface ViewPagerItem {
        void onJobSelected(View v, SingleJobModel selected);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.txt_titleJ)
        TextView txtTitle;

        @BindView(R.id.txt_categoryJ)
        TextView txtCategory;

        @BindView(R.id.txt_company_nameJ)
        TextView txtCompanyName;

        @BindView(R.id.txt_locationJ)
        TextView txtLocation;

        @BindView(R.id.txt_publication_dateJ)
        TextView txtDate;

        @BindView(R.id.txt_salaryJ)
        TextView txtSalary;

        @BindView(R.id.txt_typeJ)
        TextView txtType;


        @BindView(R.id.txt_descJ)
        TextView txtDesc;


        @BindView(R.id.imageViewSalaryJ)
        AppCompatImageView ivSalary;

        @BindView(R.id.imageViewTypeJ)
        AppCompatImageView ivType;

        @BindView(R.id.imageViewLocationJ)
        AppCompatImageView ivLocation;

        @BindView(R.id.linearLayoutJ)
        LinearLayout linearLayoutJ;

        Context context;
        View view;

        ViewHolder(@NonNull View itemView, Context c) {
            super(itemView);
            context = c;
            view = itemView;
            ButterKnife.bind(this, itemView);
        }

        public void bind(SingleJobModel jobModel) {


            txtCompanyName.setText(jobModel.getCompany_name());
            txtTitle.setText(jobModel.getTitle());
            txtCategory.setText(jobModel.getCategory());

            txtDesc.setText(Html.fromHtml(jobModel.getDescription(),
                    Html.FROM_HTML_MODE_COMPACT));


            txtDate.setText(UtilHelper.getDate(jobModel.getPublication_date()));
            UtilHelper.checkIsEmpty(context, jobModel.getSalary(), txtSalary, ivSalary);
            UtilHelper.checkIsEmpty(context, jobModel.getCandidate_required_location(), txtLocation, ivLocation);
            UtilHelper.checkIsEmpty(context, UtilHelper.getType(jobModel.getJob_type()), txtType, ivType);

        }

    }
}