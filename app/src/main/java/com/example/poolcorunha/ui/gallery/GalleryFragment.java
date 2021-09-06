package com.example.poolcorunha.ui.gallery;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.poolcorunha.R;
import com.google.android.material.snackbar.Snackbar;

public class GalleryFragment extends Fragment {


    int contador;
    int i;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        final ImageView image = root.findViewById(R.id.fotos);
        final Context context = this.getContext();
       final int[] arrayFotos = {
                R.drawable.foto1,
                R.drawable.foto2,
                R.drawable.foto3,
                R.drawable.foto4,
                R.drawable.foto5,
                R.drawable.foto6,
        };
        contador = arrayFotos.length;
        ((ImageButton) root.findViewById(R.id.atras))
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        pulsadoAtras(root, image, arrayFotos);
                    }
                });
        ((ImageButton) root.findViewById(R.id.adelante))
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        pulsadoAdelante(root,image, arrayFotos);
                    }
                });
        return root;
    }

    public void pulsadoAtras(View v, ImageView image, int[] arrayFotos) {
        if (i > 0) {
            i = i - 1;
            Drawable d = getResources().getDrawable(arrayFotos[i]);
            image.setImageDrawable(d);
        } else {
            Snackbar.make(v, "Estás na primeira imaxe.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }

    public void pulsadoAdelante(View v, ImageView image, int[] arrayFotos) {
        if (i < contador-1) {
            i = i + 1;
            Drawable d = getResources().getDrawable(arrayFotos[i]);
            image.setImageDrawable(d);
        } else {
            Snackbar.make(v, "Estás na derradeira imaxe.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }
}