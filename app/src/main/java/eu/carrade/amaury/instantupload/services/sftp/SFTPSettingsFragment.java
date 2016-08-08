package eu.carrade.amaury.instantupload.services.sftp;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import eu.carrade.amaury.instantupload.R;
import eu.carrade.amaury.instantupload.services.Service;
import eu.carrade.amaury.instantupload.services.ServiceFragment;
import eu.carrade.amaury.instantupload.ui.shared.FilenameFormattingHelpDialogFragment;
import eu.carrade.amaury.instantupload.utils.FilenameFormatter;
import eu.carrade.amaury.instantupload.utils.MaterialUtils;
import eu.carrade.amaury.instantupload.utils.SimpleTextWatcher;


public class SFTPSettingsFragment extends ServiceFragment implements FilenameFormattingHelpDialogFragment.FilenameFormattingHelpListener
{
    public static final int DIALOG_FORMATTING = 1;

    private String exampleFilename;

    private TextInputEditText inputServer = null;
    private TextInputEditText inputUsername = null;
    private TextInputEditText inputFilenameFormat = null;
    private TextInputEditText inputURL = null;

    private TextInputLayout inputURLLayout = null;

    private TextView formatterPreviewText = null;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_sftp_settings, container, false);

        exampleFilename = getString(R.string.formatter_preview_example_filename);

        inputServer         = (TextInputEditText) view.findViewById(R.id.service_configuration_sftp_server);
        inputUsername       = (TextInputEditText) view.findViewById(R.id.service_configuration_sftp_username);
        inputFilenameFormat = (TextInputEditText) view.findViewById(R.id.service_configuration_sftp_filename_format);
        inputURL            = (TextInputEditText) view.findViewById(R.id.service_configuration_sftp_url);

        inputURLLayout = (TextInputLayout) view.findViewById(R.id.service_configuration_sftp_url_layout);

        formatterPreviewText = (TextView) view.findViewById(R.id.service_configuration_filename_format_preview);


        final TextWatcher checkOnChangeListener = new SimpleTextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if (listener != null) listener.onServiceSpecificConfigurationChange();
            }
        };

        inputServer.addTextChangedListener(checkOnChangeListener);
        inputUsername.addTextChangedListener(checkOnChangeListener);
        inputURL.addTextChangedListener(checkOnChangeListener);


        inputFilenameFormat.addTextChangedListener(new SimpleTextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                updateFilenamePreview();
            }
        });

        final ImageButton formattingHelpButton = (ImageButton) view.findViewById(R.id.service_configuration_filename_format_help_button);
        formattingHelpButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DialogFragment dialog = new FilenameFormattingHelpDialogFragment();
                dialog.setTargetFragment(SFTPSettingsFragment.this, DIALOG_FORMATTING);
                dialog.show(getFragmentManager(), "help_filename_formatting");
            }
        });

        updateFilenamePreview();

        return view;
    }

    @Override
    public void onUseSuggestedTemplateOptionSelected(CharSequence suggestedTemplate)
    {
        inputFilenameFormat.setText(suggestedTemplate);
    }

    private void updateFilenamePreview()
    {
        String format = inputFilenameFormat.getText().toString();
        String formatted;

        if (StringUtils.isBlank(format))
            formatted = exampleFilename;
        else
            formatted = FilenameFormatter.formatFilename(format, exampleFilename);

        formatterPreviewText.setText(formatted);
    }

    @Override
    public boolean isValid()
    {
        final boolean serverValid = !StringUtils.isBlank(inputServer.getText().toString());
        final boolean userValid = !StringUtils.isBlank(inputUsername.getText().toString());
        final boolean urlValid = !StringUtils.isBlank(inputURL.getText().toString()) && StringUtils.contains(inputURL.getText().toString(), "%s");

        MaterialUtils.setInputError(inputURLLayout, !urlValid && !StringUtils.isBlank(inputURL.getText().toString()), getString(R.string.error_sftp_public_url_placeholder_missing));

        return serverValid && userValid && urlValid;
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
