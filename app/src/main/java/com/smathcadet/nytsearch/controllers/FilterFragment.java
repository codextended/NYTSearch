package com.smathcadet.nytsearch.controllers;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.smathcadet.nytsearch.R;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Lenovo de Marcus on 7/15/2018.
 */

public class FilterFragment extends DialogFragment {
    public interface OnDataPass {
        void onDataPass(String date, String order, ArrayList<String> newsDesk);
    }
    OnDataPass dataPasser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }
    String date = "";
    String sortOrder;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_filter, null);
        builder.setView(view);

        final DatePicker picker = view.findViewById(R.id.dpFilter);
        final Spinner spinner = view.findViewById(R.id.spFilter);
        final CheckBox cbTech = view.findViewById(R.id.cbTechnology);
        final CheckBox cbPolitics = view.findViewById(R.id.cbPolitics);
        final CheckBox cbSport = view.findViewById(R.id.cbSport);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int year = picker.getYear();
                int month = picker.getMonth() + 1;
                int day = picker.getDayOfMonth();
                String dayString = "";
                String monthString = "";
                if (day < 10){
                    dayString = "0" + day;
                }else {
                    dayString = String.valueOf(day);
                }

                if (month < 10){
                    monthString = "0" + month;
                }else {
                    monthString = String.valueOf(month);
                }
                date = String.valueOf(year) + monthString + dayString;

                sortOrder = spinner.getSelectedItem().toString();

                ArrayList<String> newsDesk = new ArrayList<>();
                String tech = cbTech.getText().toString();
                String politics = cbPolitics.getText().toString();
                String sport = cbSport.getText().toString();

                if (!TextUtils.isEmpty(tech)){
                    newsDesk.add(tech);
                }
                if (!TextUtils.isEmpty(politics)){
                    newsDesk.add(politics);
                }
                if (!TextUtils.isEmpty(sport)){
                    newsDesk.add(sport);
                }

                dataPasser.onDataPass(date, sortOrder.toLowerCase(), newsDesk);
            }
        });

        return builder.create();
    }
}
