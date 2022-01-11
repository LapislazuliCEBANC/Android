package com.example.retoamericar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;

public class CalendarioActivity extends AppCompatActivity {
    Button crear;
    ListView lista;
    EditText nombre;
    EditText hora;
    CalendarView caledario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        
    }
}