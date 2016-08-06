package eu.carrade.amaury.instantupload.ui;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import eu.carrade.amaury.instantupload.InstantUpload;
import eu.carrade.amaury.instantupload.R;
import eu.carrade.amaury.instantupload.services.Service;
import eu.carrade.amaury.instantupload.services.ServiceFragment;
import eu.carrade.amaury.instantupload.services.ServiceType;


public class AddServiceActivity extends AppCompatActivity implements ServiceConfigurationFragment.OnServiceConfigurationChangeListener
{
    /**
     * This activity must be called with a bundle containing this key
     */
    public static final String SERVICE_TYPE_FIELD = "serviceType";

    private Service service;
    private ServiceFragment serviceFragment;
    private ServiceConfigurationFragment sharedConfigurationFragment;

    private MenuItem doneButton;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final Bundle b = getIntent().getExtras();
        if (b == null) throw new IllegalArgumentException("Extra bundle required to create AddServiceActivity");

        final String serviceTypeName = b.getString(SERVICE_TYPE_FIELD);
        if (serviceTypeName == null) throw new IllegalArgumentException("Invalid service type given to AddServiceActivity (empty)");

        ServiceType serviceType = ServiceType.valueOf(serviceTypeName);

        try
        {
            service = serviceType.getHandler().newInstance();
        }
        catch (InstantiationException e) { InstantUpload.get().handleError(e); }
        catch (IllegalAccessException e) { InstantUpload.get().handleError(e); }

        final boolean initiallyPrimary = InstantUpload.get().getServicesManager().getServices().size() == 0;

        setContentView(R.layout.activity_add_service);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_service);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.activity_add_service, serviceType.getName()));

        serviceFragment = service.getSettingsFragment();
        sharedConfigurationFragment = ServiceConfigurationFragment.newInstance("", initiallyPrimary);

        final FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.service_add_layout_common, sharedConfigurationFragment);
        if (serviceFragment != null) ft.replace(R.id.service_add_layout_specific, serviceFragment);

        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.add_service, menu);

        // The Done button is initially disabled, and enabled when the form
        // is valid
        doneButton = menu.getItem(0);
        doneButton.setEnabled(false);

        checkForm();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;

            case R.id.service_add_menu_done:
                saveService();

                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                finish();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onServiceConfigurationChange()
    {
        checkForm();
    }

    private void checkForm()
    {
        // In this case the activity was just initialized and the check was initialized by the initial
        // focus, so it is not relevant. It will be re-executed when the activity is fully loaded anyway.
        if (doneButton == null) return;

        doneButton.setEnabled(sharedConfigurationFragment.isValid() && (serviceFragment == null || serviceFragment.isValid()));
    }

    public void saveService()
    {
        sharedConfigurationFragment.saveToService(service);

        if (serviceFragment != null)
            serviceFragment.saveToService(service);

        InstantUpload.get().getServicesManager().saveService(service);
    }
}
