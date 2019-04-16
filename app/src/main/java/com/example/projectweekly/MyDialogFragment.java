package com.example.projectweekly;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.projectweekly.Interfaces.CreateUserListener;

public class MyDialogFragment extends AppCompatDialogFragment {
    static int request = 1;
    EditText etName, etJob;
     CreateUserListener createUserListener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflator = getActivity().getLayoutInflater();
        View view = inflator.inflate(R.layout.dialog, null);

        builder.setView(view)
                .setTitle("Create User")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = etName.getText().toString();
                        String job = etJob.getText().toString();
                        createUserListener.createUser(name, job, request);
                    }
                });

        etName = view.findViewById(R.id.et_Dialog_Name);
        etJob = view.findViewById(R.id.et_Dialog_Job);
        return builder.create();
    }

    public void setRequest(int request){
        this.request = request;
    }
    public void setInstance(CreateUserListener listener){
        createUserListener = listener;
    }
}