package com.example.dani.exit0;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.widget.Toast;

/**
 * Created by Jorge on 31/01/2017.
 */

public class Camara {

    private Camera camara;
    private Camera.Parameters parameters;
    private boolean tieneFlash;
    private boolean flashEncendido;
    private Context contexto;


    public Camara(Context contexto) throws Exception{
        this.contexto = contexto;
        this.flashEncendido = false;
        camara = Camera.open();
        if(camara == null){
            throw new Exception("No se ha podido recuperar la camara");
        }
        preparaLinterna();

    }



    private void preparaLinterna() throws Exception {

        tieneFlash = this.contexto.getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if(!tieneFlash){
            liberarCamara();
            throw new Exception("El dispositivo no tiene flash");
        }

    }

    public void liberarCamara(){
        if(flashEncendido){
            apagarFlash();
        }
        camara.release();
    }

    public void controlarFlash() {
        parameters = camara.getParameters();
        if (!flashEncendido) {
            encenderFlash();
        } else {
           apagarFlash();
        }
    }

    public void encenderFlash(){
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camara.setParameters(parameters);
        camara.startPreview();
        flashEncendido = true;
    }

    public void apagarFlash(){
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camara.setParameters(parameters);
        flashEncendido = false;
    }
}
