package eu.carrade.amaury.instantupload.ui;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import eu.carrade.amaury.instantupload.R;
import eu.carrade.amaury.instantupload.services.Service;


public class ServicesListAdapter extends BaseAdapter
{
    private List<Service> services;
    private LayoutInflater layoutInflater;


    public ServicesListAdapter(List<Service> services, LayoutInflater inflater)
    {
        if (services == null) throw new IllegalArgumentException("Services list cannot be null");
        if (inflater == null) throw new IllegalArgumentException("Layout inflater cannot be null");

        this.services = services;
        this.layoutInflater = inflater;
    }


    @Override
    public int getCount()
    {
        return services.size();
    }

    @Override
    public Service getItem(int i)
    {
        return services.get(i);
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
            view = layoutInflater.inflate(R.layout.services_list_item, null);

            holder = new ViewHolder();

            holder.icon = (TextView) view.findViewById(R.id.service_icon);
            holder.title = (TextView) view.findViewById(R.id.service_name);

            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }

        Service service = getItem(i);

        if (service != null)
        {
            holder.title.setText(service.getName());

            Drawable icon = service.getIcon();
            if (icon != null)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                    holder.icon.setBackground(icon);
                else
                    holder.icon.setBackgroundDrawable(icon);
            }
        }

        return view;
    }


    private static class ViewHolder
    {
        public TextView icon;
        public TextView title;
    }
}
