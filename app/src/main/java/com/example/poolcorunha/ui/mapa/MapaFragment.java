package com.example.poolcorunha.ui.mapa;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.poolcorunha.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MapaFragment extends Fragment {

    public String latitud;
    public String longitud;
    public String local;
    String facebook;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_lugar, container, false);
        Spinner spinLoc = (Spinner) root.findViewById(R.id.local);

        ((Button) root.findViewById(R.id.button))
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        pulsadoBoton();
                    }
                });

        ((Button) root.findViewById(R.id.go))
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        pulsadoBusqueda(root, local);
                        local = "";
                        latitud = "";
                        longitud = "";
                    }
                });
        ((ImageButton) root.findViewById(R.id.faceb))
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        pulsadoFb(root, local);
                        local = "";
                        facebook ="";
                    }
                });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void pulsadoBoton() {


        Uri uri = Uri.parse("https://www.google.es/maps/@43.3540868,-8.3525209,13z/data=!3m1!4b1!4m3!11m2!2sOhUGhGnEJyJgcGwZzpGxSKVHGbr87Q!3e3");
        if (URLUtil.isValidUrl(uri.toString())) {
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }


    public void pulsadoBusqueda(final View v, String local) {
        local = ((Spinner) v.findViewById(R.id.local)).getSelectedItem().toString();
        final String nombre = local;

        if (local.equals("Escolle un local")) {
            Snackbar.make(v, "Esta opción non é válida, por favor escolle un local.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                    .setTimestampsInSnapshotsEnabled(true)
                    .build();
            firestore.setFirestoreSettings(settings);

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            db.collection("locales").whereEqualTo("NOM", local).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    latitud = document.get("LAT").toString();
                                    longitud = document.get("LONG").toString();
                                    // Creates an Intent that will load a map of San Francisco
                                    Uri gmmIntentUri = Uri.parse("geo:"+latitud+","+longitud+"?q="+latitud+","+longitud+"("+nombre+")");
                                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                    mapIntent.setPackage("com.google.android.apps.maps");
                                    startActivity(mapIntent);

                                }
                            } else {

                            }
                        }
                    });
        }


    }

    public void pulsadoFb(final View v, String local) {
        local = ((Spinner) v.findViewById(R.id.local)).getSelectedItem().toString();

        if (local.equals("Escolle un local")) {
            Snackbar.make(v, "Esta opción non é válida, por favor escolle un local.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                    .setTimestampsInSnapshotsEnabled(true)
                    .build();
            firestore.setFirestoreSettings(settings);

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            db.collection("locales").whereEqualTo("NOM", local).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    facebook = document.get("FB").toString();
                                    if (facebook.equals("")){
                                        Snackbar.make(v, "Este local non ten páxina de Facebook.", Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                    } else {
                                        Uri uri = Uri.parse("https://www.facebook.com/"+facebook);
                                        if (URLUtil.isValidUrl(uri.toString())) {
                                            startActivity(new Intent(Intent.ACTION_VIEW, uri));
                                        }
                                    }


                                }
                            } else {

                            }
                        }
                    });
        }


    }


}