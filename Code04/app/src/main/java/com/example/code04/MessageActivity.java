package com.example.code04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Log.d("MessageActivity.this", "onCreate: ");
        TextView tvMessage = findViewById(R.id.message);
        Intent intent = getIntent();
        String message = intent.getStringExtra(
                MainActivity.MESSAGE_STRING);
        if (message != null) {
            if (tvMessage != null) {
                tvMessage.setText(message);
            }
        }
    }
}
