package eu.carrade.amaury.instantupload.services.sftp;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
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
import eu.carrade.amaury.instantupload.utils.SimpleTextWatcher;


public class SFTPSettingsFragment extends ServiceFragment implements FilenameFormattingHelpDialogFragment.FilenameFormattingHelpListener
{
    public static final int DIALOG_FORMATTING = 1;

    private String exampleFilename;
    private TextInputEditText inputFilenameFormat = null;
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
        inputFilenameFormat = (TextInputEditText) view.findViewById(R.id.service_configuration_sftp_filename_format);
        formatterPreviewText = (TextView) view.findViewById(R.id.service_configuration_filename_format_preview);

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
