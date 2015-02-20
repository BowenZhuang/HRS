package com.zhuang.bowen.hospitalreservation;

import android.app.Activity;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
/**
 * Created by Bowem on 2015-02-19.
 */

public class PatientListAdpter extends ArrayAdapter<Patient> {

    private final Activity context;
    private final List<Patient> patients;

    static class ViewHolder {
        public TextView idText;
        public TextView nameText;
        public TextView phoneText;
        public TextView isMsgSent;
        public CheckBox isCalled;
    }

    public PatientListAdpter(Activity  context, List<Patient> objects) {
        super(context, R.layout.rowlayout, objects);
        this.context = context;
        this.patients = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.rowlayout, null);
            //
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.idText = (TextView) rowView.findViewById(R.id.id);
            viewHolder.nameText = (TextView) rowView.findViewById(R.id.name);
            viewHolder.phoneText = (TextView) rowView.findViewById(R.id.phone);
            viewHolder.isMsgSent = (TextView) rowView.findViewById(R.id.isMsgSent);
            viewHolder.isCalled = (CheckBox) rowView.findViewById(R.id.checkBox);
            viewHolder.isCalled
                    .setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            Patient patient = (Patient) viewHolder.isCalled
                                    .getTag();
                            CheckBox checkBox = (CheckBox) view;
                            final boolean isChecked = checkBox.isChecked();
                            updateCall(patient);
                        }
                    });
            viewHolder.isCalled.setTag(patients.get(position));
            rowView.setTag(viewHolder);
        } else {
        rowView = convertView;
        ((ViewHolder) rowView.getTag()).isCalled.setTag(patients.get(position));
    }



        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        Patient patient = patients.get(position);
        holder.idText.setText(patient.getId());
        holder.nameText.setText(patient.getName());
        holder.phoneText.setText(patient.getPhone());

        if (patient.isMsgSent()) {
            holder.isMsgSent.setText("SMS have been sent");
        } else {
            holder.isMsgSent.setText("");
        }

        holder.isCalled.setChecked(patient.isCalled());

        return rowView;
    }

    private void updateCall(Patient curPatient) {
        String urlClick = "http://" + PatientListActivity.Server + "/4x4/patient_update.php?id=";
        urlClick += curPatient.getId() + "&called=1";
        new TheUpdateCallTask().execute(urlClick);
    }
}

