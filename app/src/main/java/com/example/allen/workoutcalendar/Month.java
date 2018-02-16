package com.example.allen.workoutcalendar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Allen on 2/15/2018.
 */


public class Month extends Fragment{

    private CalendarView cv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab_month, container, false);
        cv = rootView.findViewById(R.id.cv);

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int yr, int month, int day) {
                Toast.makeText(rootView.getContext(), "Year= "+ yr + " Month= " + month + " Day= " + day,
                        Toast.LENGTH_SHORT).show();

                ((CalendarActivity)getActivity()).setDate(day + " " + month + " " + yr);

            }
        });

        return rootView;
    }

}
