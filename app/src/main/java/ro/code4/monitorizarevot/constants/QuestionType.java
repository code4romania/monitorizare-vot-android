package ro.code4.monitorizarevot.constants;

public enum QuestionType {
    SINGLE_OPTION,
    MULTIPLE_OPTIONS,
    UNKNOWN;

    public static QuestionType from(Integer id) {
        if (id != null && (id == 1 || id == 2)) {
            return SINGLE_OPTION;
        }
        if (id != null && (id == 0 || id == 3)) {
            return MULTIPLE_OPTIONS;
        }
        return UNKNOWN;
    }
}
