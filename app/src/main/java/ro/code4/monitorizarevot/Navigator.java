package ro.code4.monitorizarevot;

public interface Navigator {
    void navigateTo(BaseFragment fragment);

    /**
     * The first fragment should not be added to the backStack
     */
    void navigateTo(BaseFragment fragment, boolean addToBackStack);

    void navigateBack();

    void navigateBackUntil(int backstackIndex);

    void setTitle(String title);

    void setMenu(boolean isEnabled);
}
