package org.almiso.prefixnumberfix.ui.cell;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import org.almiso.prefixnumberfix.R;
import org.almiso.prefixnumberfix.model.Contact;

/**
 * Created by Alexandr on 07.06.16.
 */
public class ContactCell extends FrameLayout {

    private AppCompatTextView tvName;

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

    private void initCell(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.cell_contact, this, false);

        tvName = (AppCompatTextView) view.findViewById(R.id.tvName);


        addView(view);
    }


    public void setContact(Contact contact) {
        tvName.setText(contact.toString());
    }
}
