package eu.carrade.amaury.instantupload.services;

import android.graphics.drawable.Drawable;

import eu.carrade.amaury.instantupload.InstantUpload;
import eu.carrade.amaury.instantupload.R;
import eu.carrade.amaury.instantupload.services.sftp.SFTPService;


public enum ServiceType
{
    SFTP(
            R.string.services_sftp_name,
            R.drawable.ic_vpn_lock_white_24dp, R.drawable.ic_vpn_lock_black_24dp,
            SFTPService.class
    )

    ;

    private final String name;
    private final Drawable iconLight;
    private final Drawable iconDark;

    private final Class<? extends Service> handler;

    ServiceType(String name, Drawable iconLight, Drawable iconDark, Class<? extends Service> handler)
    {
        this.name = name;
        this.iconLight = iconLight;
        this.iconDark = iconDark;
        this.handler = handler;
    }

    ServiceType(int name, int iconLight, int iconDark, Class<? extends Service> handler)
    {
        this(
                InstantUpload.get().getString(name),
                InstantUpload.get().getResources().getDrawable(iconLight),
                InstantUpload.get().getResources().getDrawable(iconDark),
                handler
        );
    }

    public String getName()
    {
        return name;
    }

    public Drawable getIconLight()
    {
        return iconLight;
    }

    public Drawable getIconDark()
    {
        return iconDark;
    }

    public Class<? extends Service> getHandler()
    {
        return handler;
    }
}
