package eu.carrade.amaury.instantupload.ui.shared;

import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

import eu.carrade.amaury.instantupload.R;


public class FilenameFormattingHelpDialogFragment extends DialogFragment
{
    private static final String SUGGESTED_TEMPLATE = "%r";

    private FilenameFormattingHelpListener listener = null;


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        final Fragment targetFragment = getTargetFragment();
        if (targetFragment != null && targetFragment instanceof FilenameFormattingHelpListener)
            listener = (FilenameFormattingHelpListener) targetFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final LayoutInflater inflater = getActivity().getLayoutInflater();

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_filename_format_title)
                .setView(inflater.inflate(R.layout.dialog_filename_formatting, null))
                .setNeutralButton(R.string.dialog_filename_format_button_close, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int i)
                    {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(R.string.dialog_filename_format_button_use_suggested, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int i)
                    {
                        if (listener != null) listener.onUseSuggestedTemplateOptionSelected(SUGGESTED_TEMPLATE);

                        dialog.dismiss();
                    }
                })
                .create();
    }

    public interface FilenameFormattingHelpListener
    {
        void onUseSuggestedTemplateOptionSelected(CharSequence suggestedTemplate);
    }
}
