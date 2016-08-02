package eu.carrade.amaury.instantupload.services.sftp;


import android.app.Fragment;

import java.io.File;

import eu.carrade.amaury.instantupload.services.Service;
import eu.carrade.amaury.instantupload.services.ServiceType;

public class SFTPService implements Service
{
    @Override
    public ServiceType getType()
    {
        return ServiceType.SFTP;
    }

    @Override
    public String getName()
    {
        return null;
    }

    @Override
    public boolean isPrimary()
    {
        return false;
    }

    @Override
    public Fragment getSettingsFragment()
    {
        return null;
    }

    @Override
    public void openSettings()
    {

    }

    @Override
    public void saveFile(File file, ServiceCallback callback)
    {

    }
}
