package ro.code4.votehack.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import ro.code4.votehack.R;

public class FormSelectorCard extends CardView {
    private ImageView icon;
    private TextView text;

    public FormSelectorCard(Context context) {
        super(context);
        init(context, null);
    }

    public FormSelectorCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FormSelectorCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.widget_form_selector_card, this);

        setSelectableForeground(context);

        icon = (ImageView) findViewById(R.id.form_card_image);
        text = (TextView) findViewById(R.id.form_card_text);

        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FormSelectorCard, 0, 0);

            icon.setImageResource(array.getResourceId(R.styleable.FormSelectorCard_src, 0));
            text.setText(array.getResourceId(R.styleable.FormSelectorCard_text, 0));

            array.recycle();
        }
    }

    private void setSelectableForeground(Context context) {
        int[] selectableAttrs = new int[] { android.R.attr.selectableItemBackground };
        TypedArray ta = context.obtainStyledAttributes(selectableAttrs);
        Drawable selectableItemBackground = ta.getDrawable(0);
        ta.recycle();
        setForeground(selectableItemBackground);
    }
}
