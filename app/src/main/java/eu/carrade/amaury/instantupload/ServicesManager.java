package eu.carrade.amaury.instantupload;


import android.graphics.drawable.Drawable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import eu.carrade.amaury.instantupload.services.Service;

public class ServicesManager
{
    private List<Service> services = new ArrayList<>();

    public void loadServices()
    {
        services.add(new Service()
        {
            @Override
            public String getName()
            {
                return "Pomf";
            }

            @Override
            public Drawable getIcon()
            {
                return InstantUpload.get().getResources().getDrawable(android.support.v7.appcompat.R.drawable.abc_ic_voice_search_api_material);
            }

            @Override
            public void openSettings()
            {

            }

            @Override
            public void saveFile(File file, ServiceCallback callback)
            {

            }
        });
    }

    public List<Service> getServices()
    {
        return services;
    }

    public void saveService(Service service)
    {

    }

    public void deleteService(Service service)
    {

    }
}
