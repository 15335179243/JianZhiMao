package com.example.common_base.basedialogframgent.aosp;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.FixDialogFragment;
import com.example.common_base.basedialogframgent.dialog.WeakDialog;

import org.jetbrains.annotations.NotNull;


public class DialogFragment extends FixDialogFragment {
    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new WeakDialog(getActivity(), getTheme());
    }
}
