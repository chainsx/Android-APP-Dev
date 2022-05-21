package com.example.code02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnShowToast = findViewById(R.id.btnShowToast);
        btnShowToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Hello World!", Toast.LENGTH_SHORT).show();
            }
        });

        final TextView tvCount = findViewById(R.id.tvCount);
        Button btnCount = findViewById(R.id.btnCount);
        tvCount.setText(Integer.toString(count));
        btnCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvCount.setText(Integer.toString(++count));
            }
        });
    }
}

/*
package com.example.code02;

        import androidx.appcompat.app.AppCompatActivity;

        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int count = 0;
    private static final String KEY_INDEX="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnShowToast = findViewById(R.id.btnShowToast);

        btnShowToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Hello World!", Toast.LENGTH_SHORT).show();
            }
        });

        final TextView tvCount = findViewById(R.id.tvCount);
        if(savedInstanceState!=null){
            count=Integer.valueOf(savedInstanceState.getInt(KEY_INDEX,0));
            tvCount.setText(Integer.toString(count));
        }
        Button btnCount = findViewById(R.id.btnCount);
        tvCount.setText(Integer.toString(count));
        btnCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvCount.setText(Integer.toString(++count));
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle onSaveInstanceState) {
        super.onSaveInstanceState(onSaveInstanceState);
        onSaveInstanceState.putInt(KEY_INDEX,count);
    }
}*/
