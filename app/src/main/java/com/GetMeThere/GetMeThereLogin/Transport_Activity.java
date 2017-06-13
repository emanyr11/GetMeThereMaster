package com.GetMeThere.GetMeThereLogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;


public class Transport_Activity extends AppCompatActivity {

    public AppCompatButton btn_pdf;
    public AppCompatButton btn_maps;

    public void init(){
        btn_maps = (AppCompatButton) findViewById(com.GetMeThere.GetMeThereLogin.R.id.btn_maps);
        btn_maps.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent pdf = new Intent(Transport_Activity.this,WebActivity.class);
                startActivity(pdf);
            }
        });
    }
    public void init2(){
        btn_pdf = (AppCompatButton) findViewById(com.GetMeThere.GetMeThereLogin.R.id.btn_pdf);
        btn_pdf.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent pdf = new Intent(Transport_Activity.this,PDF_Activity.class);
                startActivity(pdf);
            }
        });
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.GetMeThere.GetMeThereLogin.R.layout.activity_transport_);
        init();
        init2();
    }
}
