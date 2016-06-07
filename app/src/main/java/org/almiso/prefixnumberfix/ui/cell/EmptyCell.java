package org.almiso.prefixnumberfix.ui.cell;

import android.content.Context;
import android.widget.FrameLayout;

import org.almiso.prefixnumberfix.util.AndroidUtilities;

/**
 * Created by Alexandr on 07.06.16.
 */
public class EmptyCell  extends FrameLayout {

    private int cellHeight;

    public EmptyCell(Context context) {
        this(context, 8);
    }

    public EmptyCell(Context context, int height) {
        super(context);
        cellHeight = height;
    }

    public void setHeight(int height) {
        cellHeight = height;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(cellHeight), MeasureSpec.EXACTLY));
    }
}
