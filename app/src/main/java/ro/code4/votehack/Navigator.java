package ro.code4.votehack;

public interface Navigator {
    void navigateTo(BaseFragment fragment);

    /**
     * The first fragment should not be added to the backStack
     */
    void navigateTo(BaseFragment fragment, boolean addToBackStack);

    void navigateBack();

    void setTitle(String title);
}
