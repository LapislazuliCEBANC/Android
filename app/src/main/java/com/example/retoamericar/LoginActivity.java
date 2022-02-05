package com.example.retoamericar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText usuario;
    EditText contrasena;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        retoSQLiteHelper rsdb = new retoSQLiteHelper(this, "reto", null, 1);
        SQLiteDatabase db = rsdb.getReadableDatabase();

        usuario = findViewById(R.id.edtLoginUsuario);
        contrasena = findViewById(R.id.edtLoginContrasena);


        login = findViewById(R.id.btnLoginLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] args = new String[]{usuario.getText().toString(), contrasena.getText().toString()};
                Cursor c = db.rawQuery("SELECT usuario, contrasena, idComercial FROM Comerciales WHERE usuario=? AND contrasena=?",args);
                if (c.moveToFirst()){
                    ponerGD(c.getInt(2));
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(),"El usiaro o la contraseña no so validos", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        contrasena.setText("");
    }

    private void ponerGD(int id){
        ((GlobalData) this.getApplication()).setIdComercial(id);
    }
}