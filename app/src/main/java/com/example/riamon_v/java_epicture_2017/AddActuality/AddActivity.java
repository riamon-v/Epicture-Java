package com.example.riamon_v.java_epicture_2017.AddActuality;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.riamon_v.java_epicture_2017.R;

public class AddActivity extends AppCompatActivity {

    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        title = findViewById(R.id.textView);
        title.setText(getIntent().getStringExtra("title"));
    }
}
