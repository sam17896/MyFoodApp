package com.example.ahsan.myfoodapp.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ahsan.myfoodapp.BuildConfig;
import com.example.ahsan.myfoodapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ActivityReservation extends AppCompatActivity {
    public static String Currency = null;
    public static final String DATE_DIALOG_ID = "datePicker";
    public static final String TIME_DIALOG_ID = "timePicker";
    public static double Tax;
    static Button btnDate;
    static Button btnTime;
    static EditText dateText;
    private static int mDay;
    private static int mHour;
    private static int mMinute;
    private static int mMonth;
    private static int mYear;
    static EditText timeText;
    String Comment = BuildConfig.FLAVOR;
    String Date;
    String Date_Time;
    String Email;
    int IOConnect = 0;
    String Name;
    String NumberOfPeople;
    String OrderList = BuildConfig.FLAVOR;
    String Phone;
    String Result;
    String TaxCurrencyAPI;
    String Time;
    Button btnSend;
    ArrayList<ArrayList<Object>> data;
    EditText edtComment;
    EditText edtEmail;
    EditText edtName;
    EditText edtNumberOfPeople;
    EditText edtOrderList;
    EditText edtPhone;
    ProgressBar progressBar;
    ScrollView sclDetail;
    TextView txtAlert;

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar c = Calendar.getInstance();
            return new DatePickerDialog(getActivity(), this, c.get(1), c.get(2), c.get(5));
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            ActivityReservation.mYear = year;
            ActivityReservation.mMonth = month;
            ActivityReservation.mDay = day;
            ActivityReservation.dateText.setText(new StringBuilder().append(ActivityReservation.mYear).append("-").append(ActivityReservation.mMonth + 1).append("-").append(ActivityReservation.mDay).append(" "));
        }
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar c = Calendar.getInstance();
            int hour = c.get(11);
            int minute = c.get(12);
            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            ActivityReservation.mHour = hourOfDay;
            ActivityReservation.mMinute = minute;
            ActivityReservation.timeText.setText(new StringBuilder().append(ActivityReservation.pad(ActivityReservation.mHour)).append(":").append(ActivityReservation.pad(ActivityReservation.mMinute)).append(":").append("00"));
        }
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        Log.d("Log", "Working in Normal Mode, RTL Mode is Disabled");
     //   setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.title_checkout);
        }
        this.edtName = (EditText) findViewById(R.id.edtName);
        this.edtNumberOfPeople = (EditText) findViewById(R.id.edtNumberOfPeople);
        this.edtEmail = (EditText) findViewById(R.id.edtEmail);
        btnDate = (Button) findViewById(R.id.btnDate);
        btnTime = (Button) findViewById(R.id.btnTime);
        this.edtPhone = (EditText) findViewById(R.id.edtPhone);
        this.edtOrderList = (EditText) findViewById(R.id.edtOrderList);
        this.edtComment = (EditText) findViewById(R.id.edtComment);
        this.btnSend = (Button) findViewById(R.id.btnSend);
        this.sclDetail = (ScrollView) findViewById(R.id.sclDetail);
        this.progressBar = (ProgressBar) findViewById(R.id.prgLoading);
        this.txtAlert = (TextView) findViewById(R.id.txtAlert);
        dateText = (EditText) findViewById(R.id.dateText);
        timeText = (EditText) findViewById(R.id.timeText);
        dateText.setText(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
        timeText.setText(new SimpleDateFormat("HH:mm:00").format(Calendar.getInstance().getTime()));
//        this.TaxCurrencyAPI = Config.ADMIN_PANEL_URL + "/api/get-tax-and-currency.php" + "?accesskey=" + Utils.ACCESS_KEY;
        //dbhelper = new DBHelper(this);
        btnDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerFragment().show(ActivityReservation.this.getSupportFragmentManager(), ActivityReservation.DATE_DIALOG_ID);
            }
        });
        dateText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new DatePickerFragment().show(ActivityReservation.this.getSupportFragmentManager(), ActivityReservation.DATE_DIALOG_ID);
            }
        });
        btnTime.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            new TimePickerFragment().show(ActivityReservation.this.getSupportFragmentManager(), ActivityReservation.TIME_DIALOG_ID);
            }
        });
        this.btnSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                ActivityReservation.this.Name = ActivityReservation.this.edtName.getText().toString();
                ActivityReservation.this.NumberOfPeople = ActivityReservation.this.edtNumberOfPeople.getText().toString();
                ActivityReservation.this.Email = ActivityReservation.this.edtEmail.getText().toString();
                ActivityReservation.this.Date = ActivityReservation.dateText.getText().toString();
                ActivityReservation.this.Time = ActivityReservation.timeText.getText().toString();
                ActivityReservation.this.Phone = ActivityReservation.this.edtPhone.getText().toString();
                ActivityReservation.this.Comment = ActivityReservation.this.edtComment.getText().toString();
                ActivityReservation.this.Date_Time = ActivityReservation.this.Date + " " + ActivityReservation.this.Time;





                if (ActivityReservation.this.Name.equalsIgnoreCase(BuildConfig.FLAVOR) || ActivityReservation.this.NumberOfPeople.equalsIgnoreCase(BuildConfig.FLAVOR) || ActivityReservation.this.Email.equalsIgnoreCase(BuildConfig.FLAVOR) || ActivityReservation.this.Date.equalsIgnoreCase(BuildConfig.FLAVOR) || ActivityReservation.this.Time.equalsIgnoreCase(BuildConfig.FLAVOR) || ActivityReservation.this.Phone.equalsIgnoreCase(BuildConfig.FLAVOR)) {
                    Toast.makeText(ActivityReservation.this, R.string.form_alert, Toast.LENGTH_LONG).show();
                }else {
                    Intent intent = new Intent(ActivityReservation.this,ActivityReservationConfirmation.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        progressBar.setVisibility(View.INVISIBLE);
        sclDetail.setVisibility(View.VISIBLE);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private static String pad(int c) {
        if (c >= 10) {
            return String.valueOf(c);
        }
        return "0" + String.valueOf(c);
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
