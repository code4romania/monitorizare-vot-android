package ro.code4.monitorizarevot.dagger;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ro.code4.monitorizarevot.fragment.*;

@Module
public abstract class FragmentBindingModule {

    @ContributesAndroidInjector
    abstract AddNoteFragment contributeAddNoteFragment();

    @ContributesAndroidInjector
    abstract BranchDetailsFragment contributeBranchDetailsFragment();

    @ContributesAndroidInjector
    abstract BranchSelectionFragment contributeBranchSelectionFragment();

    @ContributesAndroidInjector
    abstract FormsListFragment contributeFormsListFragment();

    @ContributesAndroidInjector
    abstract GuideFragment contributeGuideFragment();

    @ContributesAndroidInjector
    abstract QuestionFragment contributeQuestionFragment();

    @ContributesAndroidInjector
    abstract QuestionsDetailsFragment contributeQuestionDetailsFragment();

    @ContributesAndroidInjector
    abstract QuestionsOverviewFragment contributeQuestionOverviewFragment();

    @ContributesAndroidInjector
    abstract ChatFragment contributeChatFragment();
}
