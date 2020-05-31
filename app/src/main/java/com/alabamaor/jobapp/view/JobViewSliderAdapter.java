package com.alabamaor.jobapp.view;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.alabamaor.jobapp.R;
import com.alabamaor.jobapp.model.SingleJobModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JobViewSliderAdapter extends RecyclerView.Adapter<JobViewSliderAdapter.ViewHolder> {


    private List<SingleJobModel> mJobsList;
    private LayoutInflater mInflater;
    private ViewPager2 viewPager2;
    private Context context;

    JobViewSliderAdapter(Context context, List<SingleJobModel> data, ViewPager2 viewPager2) {
        this.mInflater = LayoutInflater.from(context);
        this.mJobsList = data;
        this.viewPager2 = viewPager2;
        this.context =context;
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
                R.layout.job_list_tile, parent,
                false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mJobsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mJobsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.txt_title)
        TextView txtTitle;

        @BindView(R.id.txt_category)
        TextView txtCategory;

        @BindView(R.id.txt_company_name)
        TextView txtCompanyName;

        @BindView(R.id.txt_desc)
        TextView txtDesc;

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

        Context context;

        ViewHolder(@NonNull View itemView, Context c) {
            super(itemView);
            context = c;
            ButterKnife.bind(this, itemView);
        }

        public void bind(SingleJobModel jobModel) {


            txtCompanyName.setText(jobModel.getCompany_name());
            txtTitle.setText(jobModel.getTitle());
            txtCategory.setText(jobModel.getCategory());

            setDate(jobModel.getPublication_date());

            txtDesc.setText(Html.fromHtml(jobModel.getDescription(),
                    Html.FROM_HTML_MODE_COMPACT));


            checkIsEmpty(jobModel.getSalary(), txtSalary, ivSalary);
            checkIsEmpty(jobModel.getCandidate_required_location(), txtLocation, ivLocation);
            checkIsEmpty(jobModel.getJob_type(), txtType, ivType);



        }

        private void checkIsEmpty(String str, TextView txt, AppCompatImageView iv) {
            if ( str.isEmpty() ){
                txt.setVisibility(View.GONE);
                iv.setVisibility(View.GONE);
            }
            else{
                txt.setText(str);
                txt.setVisibility(View.VISIBLE);
                iv.setVisibility(View.VISIBLE);
                iv.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            }
        }

        private void setDate(String createdDate) {

            SimpleDateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            try {
                Date parsedDate = defaultDateFormat.parse(createdDate);
                SimpleDateFormat finalDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                createdDate = finalDateFormat.format(parsedDate);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            txtDate.setText(createdDate);


        }

    }
}