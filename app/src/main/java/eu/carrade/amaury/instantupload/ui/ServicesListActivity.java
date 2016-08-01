package eu.carrade.amaury.instantupload.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import eu.carrade.amaury.instantupload.InstantUpload;
import eu.carrade.amaury.instantupload.R;
import eu.carrade.amaury.instantupload.services.Service;

public class ServicesListActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        updateView();
    }

    /**
     * Updates this view using the services loaded
     */
    private void updateView()
    {
        final List<Service> services = InstantUpload.get().getServicesManager().getServices();

        final ListView servicesListView = (ListView) findViewById(R.id.services_list);
        final LinearLayout startupTips = (LinearLayout) findViewById(R.id.services_list_empty_tips);

        if (services.size() == 0)
        {
            servicesListView.setVisibility(View.GONE);
            startupTips.setVisibility(View.VISIBLE);
        }
        else
        {
            startupTips.setVisibility(View.GONE);
            servicesListView.setVisibility(View.VISIBLE);

            servicesListView.setAdapter(new ServicesListAdapter(services, getLayoutInflater()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addProvider(View view)
    {
        Toast.makeText(ServicesListActivity.this, "File uploaded, link copied in the clipboard", Toast.LENGTH_SHORT).show();
    }
}
