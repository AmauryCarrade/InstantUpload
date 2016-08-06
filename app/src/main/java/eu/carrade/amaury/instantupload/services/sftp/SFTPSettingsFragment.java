package eu.carrade.amaury.instantupload.services.sftp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import eu.carrade.amaury.instantupload.R;
import eu.carrade.amaury.instantupload.services.Service;
import eu.carrade.amaury.instantupload.services.ServiceFragment;


public class SFTPSettingsFragment extends ServiceFragment
{
    public SFTPSettingsFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sftp_settings, container, false);
    }

    @Override
    public boolean isValid()
    {
        return true;
    }

    @Override
    public void loadFromService(Service service)
    {

    }

    @Override
    public void saveToService(Service service)
    {

    }
}
