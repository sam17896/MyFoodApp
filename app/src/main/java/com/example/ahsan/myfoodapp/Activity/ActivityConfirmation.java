package com.example.ahsan.myfoodapp.Activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahsan.myfoodapp.R;

import java.security.SecureRandom;

public class ActivityConfirmation extends AppCompatActivity {

    private TextView textView,pin;
    private Button btn,btnLocation;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        Log.d("Log", "Working in Normal Mode, RTL Mode is Disabled");
//        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.title_confirm);
        }
        this.textView = findViewById(R.id.textView);
        pin = findViewById(R.id.pin);
        this.textView.setText(Html.fromHtml(getResources().getString(R.string.success_order)));
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(100000);
        final String formatted = String.format("%05d", num);
        pin.setText("Your order will be delivered after 40-45 minutes.. You can track your order with this order number "+formatted);
        btn = findViewById(R.id.btn);
        btnLocation = findViewById(R.id.btnLocation);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("PIN CODE", formatted);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(ActivityConfirmation.this, "PinCode copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityConfirmation.this, ActivityLocation.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
