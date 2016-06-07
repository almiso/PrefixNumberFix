package org.almiso.prefixnumberfix.ui.cell;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by Alexandr on 07.06.16.
 */
public class ContactCell extends FrameLayout {

    public ContactCell(Context context) {
        super(context);
        initCell(context);
    }

    public ContactCell(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCell(context);
    }

    public ContactCell(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCell(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ContactCell(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initCell(context);
    }

    private void initCell(Context context){

    }
}
