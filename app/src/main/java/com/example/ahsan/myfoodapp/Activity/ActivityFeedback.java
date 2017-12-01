package com.example.ahsan.myfoodapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ahsan.myfoodapp.R;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class ActivityFeedback extends AppCompatActivity {

    private EditText name, email, comment;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        getSupportActionBar().setTitle("Feedback");
        name = findViewById(R.id.edtName);
        email = findViewById(R.id.edtEmail);
        comment = findViewById(R.id.edtOrderList);
        btn = findViewById(R.id.btnSend);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean error = false;
                String strName, strEmail, strComment;

                strName = name.getText().toString().trim();
                strEmail = email.getText().toString().trim();
                strComment = comment.getText().toString().trim();

                if(strName.equals("")){
                    error = true;
                    name.setError("Field Empty");
                }

                if(strEmail.equals("")){
                    error = true;
                    email.setError("Field Empty");
                }

                if(strComment.equals("")){
                    error = true;
                    comment.setError("Field Empty");
                }

                String namestr = "^[ A-Za-z]+$";


                if(!strName.matches(namestr)){
                    error = true;
                    name.setError("Invalid Name");
                }

                if(!isValidEmailAddress(strEmail)){
                    error = true;

                    email.setError("Invalid Email Address");
                }

                if(error){

                } else {
                    Intent intent  = new Intent(ActivityFeedback.this, ActivityFeebackConfirmation.class);
                    startActivity(intent);
                }





            }
        });
    }

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
}
