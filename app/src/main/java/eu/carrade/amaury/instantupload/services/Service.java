package eu.carrade.amaury.instantupload.services;

import android.os.Bundle;

import java.io.File;


public abstract class Service
{
    private Integer internalID = null;

    private String name;
    private boolean isPrimary;

    private Bundle parameters = new Bundle();

    public int getInternalID()
    {
        return internalID;
    }

    public void setInternalID(int internalID)
    {
        if (this.internalID != null)
            throw new IllegalStateException("Cannot set the service's internal ID: already set (should only be set by the system).");

        this.internalID = internalID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean isPrimary()
    {
        return isPrimary;
    }

    public void setPrimary(boolean primary)
    {
        isPrimary = primary;
    }

    public Bundle getParameters()
    {
        return parameters;
    }


    public abstract ServiceType getType();

    public abstract ServiceFragment getSettingsFragment();

    public abstract void openSettings();
    public abstract void saveFile(File file, ServiceCallback callback);


    public interface ServiceCallback
    {
        void uploaded(String url);
        void failed(Exception e);
    }
}
