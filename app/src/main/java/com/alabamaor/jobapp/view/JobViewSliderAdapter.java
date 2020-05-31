package com.alabamaor.jobapp.view;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.alabamaor.jobapp.R;
import com.alabamaor.jobapp.model.SingleJobModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JobViewSliderAdapter extends RecyclerView.Adapter<JobViewSliderAdapter.ViewHolder> {


    private List<SingleJobModel> mJobsList;
    private LayoutInflater mInflater;
    private ViewPager2 viewPager2;

    JobViewSliderAdapter(Context context, List<SingleJobModel> data, ViewPager2 viewPager2) {
        this.mInflater = LayoutInflater.from(context);
        this.mJobsList = data;
        this.viewPager2 = viewPager2;
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
        return new ViewHolder(view);
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


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(SingleJobModel jobModel) {
            txtSalary.setText(jobModel.getSalary());
            txtType.setText(jobModel.getJob_type());
            txtCompanyName.setText(jobModel.getCompany_name());
            txtTitle.setText(jobModel.getTitle());

            setDate(jobModel.getCategory());


            txtLocation.setText(jobModel.getCandidate_required_location());
            txtDesc.setText(Html.fromHtml(jobModel.getDescription(),
                    Html.FROM_HTML_MODE_COMPACT));

        }

        private void setDate(String dtStart) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
            try {
                Date date = format.parse(dtStart);
                txtCategory.setText(format2.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }
}