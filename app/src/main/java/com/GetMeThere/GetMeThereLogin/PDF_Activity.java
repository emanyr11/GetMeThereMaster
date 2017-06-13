package com.GetMeThere.GetMeThereLogin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

public class PDF_Activity extends AppCompatActivity {

    PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_);

        pdfView = (PDFView)findViewById(R.id.pdfView);
        pdfView.fromAsset("AUT_Shuttle.pdf").load();
    }
}
