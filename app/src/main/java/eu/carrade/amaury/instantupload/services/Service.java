package eu.carrade.amaury.instantupload.services;


import android.graphics.drawable.Drawable;

import java.io.File;

public interface Service
{
    String getName();
    Drawable getIcon();

    void openSettings();
    void saveFile(File file, ServiceCallback callback);

    interface ServiceCallback
    {
        void uploaded(String url);
        void failed(Exception e);
    }
}
