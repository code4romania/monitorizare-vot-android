package ro.code4.votehack.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import ro.code4.votehack.R;

public class LogoText extends LinearLayout {
    private TextView vote, hack;

    public LogoText(Context context) {
        super(context);
        init(context, null);
    }

    public LogoText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LogoText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LogoText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.widget_logo_text, this);

        setOrientation(HORIZONTAL);

        vote = (TextView) findViewById(R.id.logo_text_vote);
        hack = (TextView) findViewById(R.id.logo_text_hack);

        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LogoText, 0, 0);
            setTextSizePixels(array.getDimensionPixelSize(R.styleable.LogoText_textSize, 32));
            array.recycle();
        }
    }

    public void setTextSizePixels(float dimension) {
        vote.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimension);
        hack.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimension);
    }
}
