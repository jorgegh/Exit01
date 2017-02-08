package com.example.dani.exit0;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraManager;
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
    private boolean flashEncendido = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            camara = new Camara(this);

            androidButton = (Button) findViewById(R.id.button);
            androidButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, "Click", Toast.LENGTH_SHORT).show();

                    CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

                        camara.controlarFlash();


                    }else{

                        if(flashEncendido){
                            try {
                                String cameraId = camManager.getCameraIdList()[0]; // Usually front camera is at 0 position.
                                camManager.setTorchMode(cameraId, false);
                                flashEncendido = false;
                            }
                            catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                        else {
                            try {
                                String cameraId = camManager.getCameraIdList()[0]; // Usually front camera is at 0 position.
                                camManager.setTorchMode(cameraId, true);
                                flashEncendido = true;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
            });


        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

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

}

