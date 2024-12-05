package com.example.mocomproject.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mocomproject.R;

public class OrganicDetailFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_detail, container, false);
        WebView detailWebView = view.findViewById(R.id.detail_webview);

        detailWebView.setWebViewClient(new WebViewClient());

        Bundle bundle = getArguments();
        if (bundle != null) {
            String detailTitle = bundle.getString("detailTitle");
            String detailText = bundle.getString("detailText");

            // HTML 형식으로 데이터 로드
            String htmlContent = "<html><body>" +
                    "<h1>" + detailTitle + "</h1>" +
                    "<div>" + detailText + "</div>" +
                    "</body></html>";

            detailWebView.loadData(htmlContent, "text/html", "UTF-8");
        }

        return view;
    }
}
