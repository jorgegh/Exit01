package com.example.dani.exit0;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Jesus on 13/02/2017.
 */

public class WidgetConfig extends Activity {

    private int widgetId = 0;

    private Button btnAceptar;
    private Button btnCancelar;
    private EditText txtMensaje;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_config);

        //FindViewByID
        btnAceptar = (Button) findViewById(R.id.BtnAceptar);
        btnCancelar = (Button) findViewById(R.id.BtnCancelar);
        txtMensaje = (EditText) findViewById(R.id.TxtMensaje);

        //Obtenemos el Intent que ha lanzado esta ventana
        //y recuperamos sus parámetros
        Intent intentOrigen = getIntent();
        Bundle params = intentOrigen.getExtras();

        //Obtenemos el ID del widget que se está configurando
        widgetId = params.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        //Establecemos el resultado por defecto (si se pulsa el botón 'Atrás'
        //del teléfono será éste el resultado devuelto).
        setResult(RESULT_CANCELED);


        //Onclick
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnCancelar.setText("cancelar1");
                Toast.makeText(WidgetConfig.this, "Cancelar", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAceptar.setText("Aceptar1");
                Toast.makeText(WidgetConfig.this, "Aceptar", Toast.LENGTH_LONG).show();

                //Guardamos el mensaje personalizado en las preferencias
                SharedPreferences prefs =
                        getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("msg_" + widgetId, txtMensaje.getText().toString());
                editor.commit();

                //Actualizamos el widget tras la configuración
                AppWidgetManager appWidgetManager =
                        AppWidgetManager.getInstance(WidgetConfig.this);
                Widget.actualizarWidget(WidgetConfig.this, appWidgetManager, widgetId);

                //Devolvemos como resultado: ACEPTAR (RESULT_OK)
                Intent resultado = new Intent();
                resultado.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
                setResult(RESULT_OK, resultado);
                finish();
            }
        });
    }
}