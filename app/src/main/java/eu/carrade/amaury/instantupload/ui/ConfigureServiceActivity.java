package eu.carrade.amaury.instantupload.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import eu.carrade.amaury.instantupload.R;
import eu.carrade.amaury.instantupload.services.Service;

public class ConfigureServiceActivity extends AppCompatActivity
{
    private Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_configure_service);
    }
}
