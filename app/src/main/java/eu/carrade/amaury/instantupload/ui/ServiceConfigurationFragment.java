package eu.carrade.amaury.instantupload.ui;

import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.HashSet;
import java.util.Set;

import eu.carrade.amaury.instantupload.InstantUpload;
import eu.carrade.amaury.instantupload.R;
import eu.carrade.amaury.instantupload.services.Service;
import eu.carrade.amaury.instantupload.utils.SimpleTextWatcher;


public class ServiceConfigurationFragment extends Fragment implements View.OnFocusChangeListener
{
    private static final String ARG_SERVICE_NAME = "service_name";
    private static final String ARG_SERVICE_PRIMARY = "service_primary";

    private OnServiceConfigurationChangeListener listener = null;

    private String serviceName = "";
    private boolean servicePrimary = false;

    private Set<String> usedNames = new HashSet<>();

    private EditText serviceNameField;
    private TextInputLayout serviceNameFieldLayout;
    private CheckBox servicePrimaryField;


    public ServiceConfigurationFragment()
    {
        // Required empty public constructor
    }

    /**
     * Construct a new fragment.
     *
     * @param serviceName The service name to display.
     * @param servicePrimary True if this service is the primary one.
     *
     * @return A new fragment instance.
     */
    public static ServiceConfigurationFragment newInstance(String serviceName, boolean servicePrimary)
    {
        ServiceConfigurationFragment fragment = new ServiceConfigurationFragment();

        Bundle args = new Bundle();
        args.putString(ARG_SERVICE_NAME, serviceName);
        args.putBoolean(ARG_SERVICE_PRIMARY, servicePrimary);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
        {
            serviceName = getArguments().getString(ARG_SERVICE_NAME);
            servicePrimary = getArguments().getBoolean(ARG_SERVICE_PRIMARY);
        }

        for (Service service : InstantUpload.get().getServicesManager().getServices())
            usedNames.add(service.getName());
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        if (context != null && context instanceof OnServiceConfigurationChangeListener)
        {
            listener = (OnServiceConfigurationChangeListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_service_configuration, container, false);

        serviceNameField = (EditText) view.findViewById(R.id.service_configuration_name);
        serviceNameFieldLayout = (TextInputLayout) view.findViewById(R.id.service_configuration_name_layout);
        servicePrimaryField = (CheckBox) view.findViewById(R.id.service_configuration_primary);

        serviceNameField.setText(serviceName);

        // There will always be a primary service, so if one is marked as primary it
        // cannot be un-marked. The user will have to go to another service configuration
        // to mark it as primary.
        servicePrimaryField.setChecked(servicePrimary);
        servicePrimaryField.setEnabled(!servicePrimary);

        serviceNameField.setOnFocusChangeListener(this);
        servicePrimaryField.setOnFocusChangeListener(this);

        serviceNameField.addTextChangedListener(new SimpleTextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                dispatchChange();
            }
        });

        ImageButton primaryHelpButton = (ImageButton) view.findViewById(R.id.service_configuration_primary_help_button);
        primaryHelpButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                displayPrimaryHelp();
            }
        });

        return view;
    }

    private void displayPrimaryHelp()
    {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.service_configuration_primary_help_dialog_title)
                .setMessage(R.string.service_configuration_primary_help_dialog_content)
                .create()
                .show();
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus)
    {
        dispatchChange();
    }

    private void dispatchChange()
    {
        if (listener != null) listener.onServiceConfigurationChange();
    }

    /**
     * Checks if this part of the form is valid.
     * @return {@code true} if valid.
     */
    public boolean isValid()
    {
        final String name = serviceNameField.getText().toString().trim();
        final boolean nameValid = isNameValid(name);

        if (!nameValid && !name.isEmpty())
        {
            serviceNameFieldLayout.setError(getString(R.string.error_service_name_taken));
            serviceNameFieldLayout.setErrorEnabled(true);
        }
        else
        {
            serviceNameFieldLayout.setError(null);
            serviceNameFieldLayout.setErrorEnabled(false);
        }

        return nameValid;
    }

    /**
     * Save to the given service.
     * <p />
     * If a field is not valid, we keep the old value.
     *
     * @param service The service to update.
     */
    public void saveToService(Service service)
    {
        final String name = serviceNameField.getText().toString().trim();
        final boolean isPrimary = servicePrimaryField.isChecked();

        if (isNameValid(name))
            service.setName(name);

        service.setPrimary(isPrimary);
    }

    /**
     * Check if the given name is non-empty and not already used.
     *
     * @param name The name to check.
     * @return {@code true} if valid.
     */
    private boolean isNameValid(String name)
    {
        name = name.trim();

        for (String usedName : usedNames)
            if (name.equals(usedName))
                return false;

        return !name.isEmpty();
    }

    public interface OnServiceConfigurationChangeListener
    {
        void onServiceConfigurationChange();
    }
}
