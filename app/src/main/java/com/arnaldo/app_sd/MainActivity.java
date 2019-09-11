package com.arnaldo.app_sd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText et_nombre, ml_contenido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_nombre = (EditText) findViewById(R.id.et_nombre);
        ml_contenido = (EditText) findViewById(R.id.ml_contenido);

    }

    public void Guardar(View view) {
        String nombre = et_nombre.getText().toString();
        String contenido = ml_contenido.getText().toString();

        try {
            File tarjetaSD = Environment.getExternalStorageDirectory();
            File rutaarchivo = new File(tarjetaSD.getPath(), nombre);
            OutputStreamWriter crearArcchivo = new OutputStreamWriter(openFileOutput(nombre, Activity.MODE_PRIVATE));

            crearArcchivo.write(contenido);
            crearArcchivo.flush();
            crearArcchivo.close();
            Toast.makeText(this, "El contenido se guardó en: " + tarjetaSD.getPath(), Toast.LENGTH_SHORT).show();
            et_nombre.setText("");
            ml_contenido.setText("");
        } catch (IOException e) {
            System.out.println("El contenido no se pudo guardar " + e);
            Toast.makeText(this, "El contenido no se pudo guardar", Toast.LENGTH_LONG).show();
        }
    }

    public void Consultar(View view) {
        String nombre = et_nombre.getText().toString();


        try {
            File tarjetaSD = Environment.getExternalStorageDirectory();
            File rutaarchivo = new File(tarjetaSD.getPath(), nombre);
            InputStreamReader abrirArchivo = new InputStreamReader(openFileInput(nombre));

            BufferedReader leerArchivo = new BufferedReader(abrirArchivo);
            String linea = leerArchivo.readLine();
            String contenidocompleto = "";
            while (linea != null) {
                contenidocompleto = contenidocompleto + linea + "\n";
                linea = leerArchivo.readLine();
            }

            leerArchivo.close();
            abrirArchivo.close();

            ml_contenido.setText(contenidocompleto);
        } catch (IOException e) {
            System.out.println("Error al leer el archivo " + e);
            Toast.makeText(this, "Error al leer el archivo", Toast.LENGTH_LONG).show();
        }
    }
}
