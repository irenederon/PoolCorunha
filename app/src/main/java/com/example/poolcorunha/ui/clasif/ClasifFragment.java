package com.example.poolcorunha.ui.clasif;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.poolcorunha.R;
import com.google.android.material.snackbar.Snackbar;

public class ClasifFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_clas, container, false);
        //final Spinner spin = (Spinner) root.findViewById(R.id.spinner2);
        final Button bot = (Button) root.findViewById(R.id.bot1);
        final ImageButton bot1 = (ImageButton) root.findViewById(R.id.image1);
       // final String compet = spin.getSelectedItem().toString();
        final  WebView webView = root.findViewById(R.id.webView1);
        bot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                webCambio(webView);
            }
        });

        bot1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    Snackbar.make(v, "Non se pode volver atrás.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });


        return root;
    }

    public void webCambio (WebView webView){
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
        webView.getSettings().setAppCacheMaxSize (1024 * 1024 * 8 );
        webView.canGoBack();
        webView.goBack();
        String url = "http://www.agpool.com/index.php?ctx=competicion&submenu=competicioncoruna";
        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient() {});
    }




}