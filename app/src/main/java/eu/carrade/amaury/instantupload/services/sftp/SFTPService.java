package eu.carrade.amaury.instantupload.services.sftp;

import java.io.File;

import eu.carrade.amaury.instantupload.services.Service;
import eu.carrade.amaury.instantupload.services.ServiceFragment;
import eu.carrade.amaury.instantupload.services.ServiceType;


public class SFTPService extends Service
{
    @Override
    public ServiceType getType()
    {
        return ServiceType.SFTP;
    }

    @Override
    public ServiceFragment getSettingsFragment()
    {
        return new SFTPSettingsFragment();
    }

    @Override
    public void saveFile(File file, ServiceCallback callback)
    {

    }
}
