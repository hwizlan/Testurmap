package com.example.hilrudt.testurmmap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MapInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_info);

        TextView infoTextView = findViewById(R.id.infomap);

        Intent intent = getIntent();
        String infor = "INFO";
        infor = intent.getStringExtra("mapinfo");

        infoTextView.setText(infor);

//        try { // мониторим код

//            Toast.makeText(this, "Не увидите это сообщение!", Toast.LENGTH_LONG).show();
//        } catch (Exception ex) {
//            Toast.makeText(this, "Нельзя котов делить на ноль!", Toast.LENGTH_LONG).show();
//        }

    }
}
