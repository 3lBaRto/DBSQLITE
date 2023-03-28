package com.newpro.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText CC, NOM, TEL;

    Button INSERTAR, CONSULTAR;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CC = findViewById(R.id.cc);
        NOM = findViewById(R.id.nom);
        TEL = findViewById(R.id.tel);

        INSERTAR = findViewById(R.id.btnInser);
        CONSULTAR = findViewById(R.id.btnQuer);




    }

    public void onStart()
    {
        super.onStart();

        INSERTAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertar(view);
            }
        });

        CONSULTAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consultar(view);
            }
        });

    }


    public void insertar(View view){
        AdminBD admin = new AdminBD(this,"BaseDeDatos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String cedula, nombre, telefono;
        cedula = CC.getText().toString();
        nombre = NOM.getText().toString();
        telefono = TEL.getText().toString();

        if(!cedula.isEmpty() && !nombre.isEmpty() && !telefono.isEmpty())
        {
            ContentValues registro = new ContentValues();
            registro.put("cedula",cedula);
            registro.put("nombre",nombre);
            registro.put("tel",telefono);
            BaseDeDatos.insert("usuarios", null, registro);
            BaseDeDatos.close();

            CC.setText("");
            NOM.setText("");
            TEL.setText("");

            Toast.makeText(this, "Registro exitoso",Toast.LENGTH_LONG).show();
        }else
        {
            Toast.makeText(this, "Complete los campos vacíos",Toast.LENGTH_LONG).show();
        }




    }


    public void consultar(View view)
    {
        AdminBD admin = new AdminBD(this,"BaseDeDatos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String TAG = "Depuracion";
        String cedula1 = CC.getText().toString();
        if(!cedula1.isEmpty())
        {
            Cursor fila = BaseDeDatos.rawQuery("Select nombre, tel from usuarios where cedula="+cedula1,null);
            Log.i(TAG,"esto hay en nombre "+ fila);
            if(fila.moveToFirst())
            {
                NOM.setText(fila.getString(0));
                TEL.setText(fila.getString(1));
                BaseDeDatos.close();
            }else
            {
                Toast.makeText(this, "No se encuentra el usuario",Toast.LENGTH_LONG).show();

            }
        }else
        {
            Toast.makeText(this, "Ingrese la cédula para la búsqueda",Toast.LENGTH_LONG).show();
        }

    }
}