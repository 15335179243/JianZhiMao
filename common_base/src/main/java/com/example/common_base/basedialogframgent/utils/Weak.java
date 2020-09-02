package com.example.common_base.basedialogframgent.utils;

import android.content.DialogInterface;

import com.example.common_base.basedialogframgent.utils.weakproxy.WeakOnCancelListener;
import com.example.common_base.basedialogframgent.utils.weakproxy.WeakOnDismissListener;
import com.example.common_base.basedialogframgent.utils.weakproxy.WeakOnShowListener;


public class Weak {
    public static WeakOnCancelListener proxy(DialogInterface.OnCancelListener real) {
        return new WeakOnCancelListener(real);
    }

    public static WeakOnDismissListener proxy(DialogInterface.OnDismissListener real) {
        return new WeakOnDismissListener(real);
    }

    public static WeakOnShowListener proxy(DialogInterface.OnShowListener real) {
        return new WeakOnShowListener(real);
    }
}
