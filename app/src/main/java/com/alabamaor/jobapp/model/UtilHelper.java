package com.alabamaor.jobapp.model;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import com.alabamaor.jobapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UtilHelper {


    public static String getDate(String createdDate) {

        SimpleDateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        try {
            Date parsedDate = defaultDateFormat.parse(createdDate);
            SimpleDateFormat finalDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            createdDate = finalDateFormat.format(parsedDate);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return createdDate;
    }

    public static String getType(String type) {

        if (type.equalsIgnoreCase("full_time")) {
            return "Full Time";
        } else {
            return type.toUpperCase();
        }

    }

    public static void checkIsEmpty(Context context, String str, TextView txt, AppCompatImageView iv) {
        if (str.isEmpty()) {
            txt.setVisibility(View.GONE);
            iv.setVisibility(View.GONE);
        } else {
            txt.setText(str);
            txt.setVisibility(View.VISIBLE);
            iv.setVisibility(View.VISIBLE);
            iv.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        }
    }
}
