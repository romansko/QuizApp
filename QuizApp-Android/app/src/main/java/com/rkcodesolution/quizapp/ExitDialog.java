package com.rkcodesolution.quizapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;

public class ExitDialog extends DialogFragment {
    private IExitListener mListener;

    /**
     * Create Exit Dialog.
     * If user choose to exit, onExitPressed function will be called.
     */
    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setTitle(R.string.exit_dialog_title)
                .setMessage(R.string.exit_dialog_text)
                .setPositiveButton(R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                mListener.onExitPressed();
                                dismiss();
                            }
                        }
                )
                .setNegativeButton(R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dismiss();
                            }
                        }
                )
                .create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IExitListener) {
            mListener = (IExitListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement IExitListener");
        }
    }

    /**
     * Interface for the exit fragment.
     */
    public interface IExitListener {
        void onExitPressed();
    }
}
