package com.smathcadet.nytsearch.controllers;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.smathcadet.nytsearch.R;

/**
 * Created by Lenovo de Marcus on 7/15/2018.
 */

public class SearchFragment extends DialogFragment {

    public interface OnDataPass {
        void onDataPass(String data);
    }
    OnDataPass dataPasser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_search, null);
        builder.setView(view);
        builder.setTitle(R.string.dialog_serach);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText etQuery = view.findViewById(R.id.dialog_text_view);
                String query = etQuery.getText().toString();
                dataPasser.onDataPass(query);
            }
        });

        return builder.create();
    }
}
