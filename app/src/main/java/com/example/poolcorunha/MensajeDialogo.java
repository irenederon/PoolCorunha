package com.example.poolcorunha;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class MensajeDialogo extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Non se pudo iniciar a sesión").setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("O login ou a contrasinal introducidos son incorrectos. Compróbaos e volve a intentalo de novo.")
                .setPositiveButton("ACEPTAR", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Código asociado al botón Aceptar. Por ejemplo:
                        //Toast.makeText(getActivity(), "PULSADA OPCION BOA", Toast.LENGTH_LONG).show();
                    }
                });
        return builder.create();
    }
}
