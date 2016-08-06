package eu.carrade.amaury.instantupload.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import eu.carrade.amaury.instantupload.InstantUpload;
import eu.carrade.amaury.instantupload.R;
import eu.carrade.amaury.instantupload.services.Service;
import eu.carrade.amaury.instantupload.services.ServiceType;

public class SelectServiceTypeActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_service_type);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ServiceType[] serviceTypes = ServiceType.values();
        final ListView servicesListView = (ListView) findViewById(R.id.list_services_types);
        servicesListView.setAdapter(new ServicesTypesListAdapter(serviceTypes));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when a service type is selected by the user.
     * @param type The selected type
     */
    public void onServiceTypeSelected(ServiceType type)
    {
        Intent intent = new Intent(this, AddServiceActivity.class);
        Bundle b = new Bundle();
        b.putString(AddServiceActivity.SERVICE_TYPE_FIELD, type.name());
        intent.putExtras(b);

        startActivity(intent);
    }


    public class ServicesTypesListAdapter extends BaseAdapter
    {
        private final ServiceType[] serviceTypes;

        public ServicesTypesListAdapter(ServiceType[] serviceTypes)
        {
            this.serviceTypes = serviceTypes;
        }

        @Override
        public int getCount()
        {
            return serviceTypes.length;
        }

        @Override
        public ServiceType getItem(int i)
        {
            return serviceTypes[i];
        }

        @Override
        public long getItemId(int i)
        {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup)
        {
            ViewHolder holder;

            if (view == null)
            {
                view = getLayoutInflater().inflate(R.layout.services_types_list_item, null);

                holder = new ViewHolder();

                holder.icon = (TextView) view.findViewById(R.id.service_type_icon);
                holder.title = (TextView) view.findViewById(R.id.service_type_name);

                view.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) view.getTag();
            }

            ServiceType serviceType = getItem(i);

            if (serviceType != null)
            {
                holder.title.setText(serviceType.getName());

                Drawable icon = serviceType.getIconLight();
                if (icon != null)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        holder.icon.setBackground(icon);
                    else
                        holder.icon.setBackgroundDrawable(icon);
                }

                view.setTag(R.id.tag_service_type, serviceType);

                view.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        onServiceTypeSelected(((ServiceType) view.getTag(R.id.tag_service_type)));
                    }
                });
            }

            return view;
        }

        private class ViewHolder
        {
            public TextView icon;
            public TextView title;
        }
    }
}
