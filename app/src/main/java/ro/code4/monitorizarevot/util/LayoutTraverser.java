package ro.code4.monitorizarevot.util;

import android.view.View;
import android.view.ViewGroup;

/**
 * http://android-wtf.com/2013/06/how-to-easily-traverse-any-view-hierarchy-in-android/
 *
 * Traverses recursively all nested views of a ViewGroup
 * Returns a single View when process returns true
 */

public class LayoutTraverser {
    public interface Processor {
        boolean process(View view);
    }

    private final Processor processor;

    private LayoutTraverser(Processor processor) {
        this.processor = processor;
    }

    public static LayoutTraverser build(Processor processor) {
        return new LayoutTraverser(processor);
    }

    public View traverse(ViewGroup root) {
        final int childCount = root.getChildCount();

        for (int i = 0; i < childCount; ++i) {
            final View child = root.getChildAt(i);
            if (processor.process(child)) {
                return child;
            }

            if (child instanceof ViewGroup) {
                traverse((ViewGroup) child);
            }
        }

        return null;
    }
}
