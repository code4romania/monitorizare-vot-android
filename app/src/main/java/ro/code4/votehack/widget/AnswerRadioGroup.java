package ro.code4.votehack.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import ro.code4.votehack.net.model.response.ResponseAnswer;
import ro.code4.votehack.util.LayoutTraverser;

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
                if (view instanceof AnswerRadioButton) {
                    AnswerRadioButton button = (AnswerRadioButton) view;
                    if (button.isChecked()) {
                        return true;
                    }
                } else if(view instanceof AnswerRadioButtonWithDetails){
                    AnswerRadioButtonWithDetails buttonWithDetails = (AnswerRadioButtonWithDetails) view;
                    if(buttonWithDetails.isChecked()){
                        return true;
                    }
                }
                return false;
            }
        }).traverse(this);

        if(view instanceof AnswerRadioButton){
            AnswerRadioButton button = (AnswerRadioButton) view;
            return button != null ? button.getAnswer() : null;
        } else if (view instanceof AnswerRadioButtonWithDetails){
            AnswerRadioButtonWithDetails buttonWithDetails = (AnswerRadioButtonWithDetails) view;
            return buttonWithDetails != null ? buttonWithDetails.getAnswer() : null;
        }

        return null;
    }
}
