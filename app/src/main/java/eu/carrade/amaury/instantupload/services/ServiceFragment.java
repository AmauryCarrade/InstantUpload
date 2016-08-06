package eu.carrade.amaury.instantupload.services;

import android.support.v4.app.Fragment;


/**
 * A fragment given by services and representing the configuration form for this fragment.
 */
public abstract class ServiceFragment extends Fragment
{
    /**
     * Checks if the form is filed completely.
     *
     * @return {@code true} if the form is valid.
     */
    public abstract boolean isValid();

    /**
     * Loads the fragment's default values from the given service.
     *
     * @param service The service.
     */
    public abstract void loadFromService(Service service);

    /**
     * Saves the fragment's values to the given service.
     *
     * @param service The service.
     */
    public abstract void saveToService(Service service);
}
