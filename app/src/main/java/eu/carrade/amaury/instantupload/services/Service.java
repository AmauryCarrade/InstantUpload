package eu.carrade.amaury.instantupload.services;

import android.app.Fragment;

import java.io.File;


public interface Service
{
    ServiceType getType();

    String getName();
    boolean isPrimary();

    Fragment getSettingsFragment();

    void openSettings();
    void saveFile(File file, ServiceCallback callback);


    interface ServiceCallback
    {
        void uploaded(String url);
        void failed(Exception e);
    }
}
