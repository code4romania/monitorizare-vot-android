package ro.code4.monitorizarevot.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Custom layout that contains a fixed layout at the bottom of the screen and a ScrollView
 * that fills the remaining space on top
 *
 * Must contain exactly two children
 * - the first one is the content inside the ScrollView
 * - the second one is the bottom layout
 */
public class ScrollViewWithFixedBottom extends LinearLayout {
    private ScrollView scrollView;

    public ScrollViewWithFixedBottom(Context context) {
        super(context);
        init(context);
    }

    public ScrollViewWithFixedBottom(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScrollViewWithFixedBottom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ScrollViewWithFixedBottom(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void init(Context context) {
        setOrientation(VERTICAL);

        scrollView = new ScrollView(context);
        scrollView.setFillViewport(true);
        LayoutParams scrollLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);

        scrollView.setLayoutParams(scrollLayoutParams);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if(getChildCount() != 2) throw new RuntimeException("Layout must contain exactly two children");

        View scrollChildView = getChildAt(0);
        View bottomLayout = getChildAt(1);

        removeViewAt(1);
        removeViewAt(0);

        scrollView.addView(scrollChildView);

        addView(scrollView);
        addView(bottomLayout);
    }
}
