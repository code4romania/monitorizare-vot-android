package ro.code4.monitorizarevot.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import ro.code4.monitorizarevot.net.model.response.ResponseAnswer;
import ro.code4.monitorizarevot.util.LayoutTraverser;

public class AnswerRadioGroup extends LinearLayout implements CompoundButton.OnCheckedChangeListener {
    public AnswerRadioGroup(Context context) {
        super(context);
        init();
    }

    public AnswerRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnswerRadioGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AnswerRadioGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
    }

    @Override
    public void onCheckedChanged(final CompoundButton checkedButton, boolean isChecked) {
        if (isChecked) {
            LayoutTraverser.build(new LayoutTraverser.Processor() {
                @Override
                public boolean process(View view) {
                    if (view instanceof CompoundButton && view != checkedButton) {
                        ((RadioButton) view).setChecked(false);
                    }
                    return false;
                }
            }).traverse(this);
        }
    }

    public ResponseAnswer getCheckedAnswer() {
        View view = LayoutTraverser.build(new LayoutTraverser.Processor() {
            @Override
            public boolean process(View view) {
                if (view instanceof AnswerRadioButton ||
                        view instanceof AnswerRadioButtonWithDetails) {
                    Checkable button = (Checkable) view;
                    return button.isChecked();
                }
                return false;
            }
        }).traverse(this);
        return view != null ? ((AnswerLayout) view).getAnswer() : null;
    }
}
