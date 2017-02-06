package com.example.dani.exit0;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button androidButton;
    private Camara camara;
    private final int MY_PERMISSIONS_REQUEST_CAMERA = 0;
    private boolean tengoPermiso = false;
    private boolean iniciado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            solicitarPermiso();

            if (tengoPermiso && !iniciado) {
                inicializar();
            }

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void inicializar() throws Exception {
        camara = new Camara(this);
        androidButton = (Button) findViewById(R.id.button);
        androidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Click", Toast.LENGTH_SHORT).show();
                camara.controlarFlash();
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Deseas salir de la aplicacion?")
                    .setCancelable(false)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            camara.liberarCamara();
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            dialog.cancel();

                        }
                    });

            final AlertDialog alert = builder.create();
            alert.show();

        }
  /*if ((keyCode == KeyEvent.KEYCODE_MENU));
 if((keyCode==KeyEvent.KEYCODE_HOME));*/
        return true;


    }

    private void solicitarPermiso() throws Exception {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {

            Toast.makeText(this, "La version de android es anterior a Android 6" + Build.VERSION.SDK_INT, Toast.LENGTH_LONG).show();

        } else {

            int resultado = checkSelfPermission(Manifest.permission.CAMERA);

            if (resultado != PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Solicitando permisos", Toast.LENGTH_LONG).show();
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);



            } else if (resultado == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Han sido concedidos los permisos ", Toast.LENGTH_LONG).show();
                iniciado = true;
                inicializar();

            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (MY_PERMISSIONS_REQUEST_CAMERA == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                tengoPermiso = true;
            } else {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }


    }
}

