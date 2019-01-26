package android.text;

import android.support.annotation.Nullable;

/**
 * Copy of the {@link TextUtils} class from the Android platform. This is just to allow the unit tests to call on a real method - which is otherwise prevented.
 * This is a know issue and a solution is to be provided @see <a href="http://tools.android.com/tech-docs/unit-testing-support#TOC-Method-...-not-mocked.-">here</a>.
 */
public class TextUtils {

    /**
     * Returns true if the string is null or 0-length.
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    public static boolean isEmpty(@Nullable CharSequence str) {
        return str == null || str.length() == 0;
    }
}
