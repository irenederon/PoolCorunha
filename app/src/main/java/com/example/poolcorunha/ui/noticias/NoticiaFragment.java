package com.example.poolcorunha.ui.noticias;

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

public class NoticiaFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_noti, container, false);
        final ImageButton bot1 = (ImageButton) root.findViewById(R.id.image1);
        final WebView webView = root.findViewById(R.id.webView);
        ((Button) root.findViewById(R.id.buttonBola))
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        bolaOcho(root);
                    }
                });

        ((Button) root.findViewById(R.id.buttonPro))
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        spainPro(root);
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
    public void bolaOcho(View root) {

        WebView webView = root.findViewById(R.id.webView);
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
        String url = "https://bola-8.es/";
        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient() {});

    }

    public void spainPro(View root) {

        WebView webView = root.findViewById(R.id.webView);
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
        String url ="http://spainprosnooker.es/category/noticias/";
        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient() {});


    }


}