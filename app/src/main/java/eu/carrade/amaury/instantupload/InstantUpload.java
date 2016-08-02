package eu.carrade.amaury.instantupload;

import android.app.Application;


/**
 * Main application class
 */
public class InstantUpload extends Application
{
    private static InstantUpload instance;

    private ServicesManager servicesManager;

    @Override
    public void onCreate()
    {
        super.onCreate();

        instance = this;
        servicesManager = new ServicesManager();

        servicesManager.loadServices();
    }

    public void handleError(Exception e)
    {
        // TODO nice system error handling
        e.printStackTrace();
    }

    public ServicesManager getServicesManager()
    {
        return servicesManager;
    }

    public static InstantUpload get()
    {
        return instance;
    }
}
