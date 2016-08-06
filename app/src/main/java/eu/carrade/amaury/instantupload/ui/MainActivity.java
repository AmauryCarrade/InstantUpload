package eu.carrade.amaury.instantupload.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

import java.util.List;

import eu.carrade.amaury.instantupload.InstantUpload;
import eu.carrade.amaury.instantupload.R;
import eu.carrade.amaury.instantupload.services.Service;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        updateDrawerMenu();
    }

    private void updateDrawerMenu()
    {
        final List<Service> services = InstantUpload.get().getServicesManager().getServices();

        final MenuItem item = navigationView.getMenu().findItem(R.id.menu_services);
        final SubMenu subMenuServices = item.getSubMenu();

        subMenuServices.clear();

        if (services.isEmpty())
        {
            final MenuItem menuItem = subMenuServices.add(
                    Menu.NONE, Menu.NONE, Menu.NONE, R.string.menu_placeholder_no_service_yet
            );

            menuItem.setEnabled(false);
        }
        else
        {
            for (Service service : services)
            {
                final MenuItem menuItem = subMenuServices.add(
                        Menu.NONE, Menu.NONE, service.isPrimary() ? 0 : Menu.NONE, service.getName()
                );

                menuItem.setIcon(service.getType().getIconDark());

                if (service.isPrimary())
                    menuItem.setChecked(true);
            }
        }
    }

    public void actionNewService(View view)
    {
        Intent intent = new Intent(this, SelectServiceTypeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.nav_new_service:
                actionNewService(item.getActionView());
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
