package com.example.retoamericar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class NuevaTareaActivity extends AppCompatActivity {
    Spinner spin;
    ArrayList<Integer> identificadores = new ArrayList<Integer>();
    int pos;
    TextView fecha;
    TextView hora;
    TextView descripcion;
    Button escojeHora, cancelar, crear;
    int horaa, minuto;
    TextView idPartner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_tarea);
        descripcion = findViewById(R.id.descripcionn);
        hora = findViewById(R.id.horaa);

        crear = findViewById(R.id.crearTarea);
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean v1=false, v2=false;

                if(hora.getText().length() == 0){
                    hora.setError("El campo debe completarse");
                    hora.requestFocus();
                }else{
                    v1 = true;
                }

                if(descripcion.getText().length() == 0){
                    descripcion.setError("El campo debe completarse");
                    descripcion.requestFocus();
                }else{
                    v2 = true;
                }

                if (v1 && v2){
                    escribir();
                    finish();
                }
            }
        });

        cancelar = findViewById(R.id.cancelarTarea);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pos = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        cargarSpinner();



        escojeHora = findViewById(R.id.escojeHora);
        escojeHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(NuevaTareaActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                String hrtime = getTime(hourOfDay,minute);
                                hora.setText(hrtime);
                            }
                        }, horaa, minuto, false);
                timePickerDialog.show();
            }
        });


        fecha = findViewById(R.id.fechaa);

        String valor = getIntent().getStringExtra("fecha");
        fecha.setText(valor);


    }

    @SuppressLint("SimpleDateFormat")
    private String getTime(int hr, int min) {
        Time tme = new Time(hr,min,0);
        Format formatter = new SimpleDateFormat("hh:mm a");
        return formatter.format(tme);
    }


    private void escribir(){
        retoSQLiteHelper rsdb = new retoSQLiteHelper(this, "reto", null, 1);
        SQLiteDatabase db = rsdb.getWritableDatabase();

        ContentValues nuevo = new ContentValues();
        nuevo.put("idComercial", ((GlobalData)this.getApplicationContext()).getIdComercial());
        nuevo.put("descripcion",descripcion.getText().toString());
        nuevo.put("idPartner", identificadores.get(pos));
        nuevo.put("fecha",fecha.getText().toString());
        nuevo.put("hora",hora.getText().toString());


        db.insert("Agenda", null, nuevo);
    }

    private void cargarSpinner() {
        retoSQLiteHelper rsdb = new retoSQLiteHelper(this, "reto", null, 1);
        SQLiteDatabase db = rsdb.getReadableDatabase();

        String id = String.valueOf((((GlobalData) this.getApplication()).getIdComercial()));

        String[] campos = new String[]{"idPartner as _id","nombre"};
        String[] args = new String[]{id};

        Cursor c = db.query("Partners", campos, "idComercial=?", args, null, null, null);
        if (c.moveToFirst()){

            SimpleCursorAdapter sca = new SimpleCursorAdapter(this,R.layout.partner_pedidos,c,
                    new String[]{"_id","nombre"},
                    new int[]{R.id.txvPartnerNombre, R.id.txvPartnerID
                    }, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            sca.setDropDownViewResource(R.layout.partner_pedidos);
            spin.setAdapter(sca);
            do{
                identificadores.add(c.getInt(0));
            }while (c.moveToNext());
        }
        db.close();
    }
}