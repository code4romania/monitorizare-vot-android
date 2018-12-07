package ro.code4.monitorizarevot.dagger;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import ro.code4.monitorizarevot.viewmodel.*;

@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(StartViewModel.class)
    abstract ViewModel bindStartViewModel(StartViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ToolbarViewModel.class)
    abstract ViewModel bindToolbarViewModel(ToolbarViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AddNoteViewModel.class)
    abstract ViewModel bindAddNoteViewModel(AddNoteViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(BranchDetailsViewModel.class)
    abstract ViewModel bindBranchDetailsViewModel(BranchDetailsViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(BranchSelectionViewModel.class)
    abstract ViewModel bindBranchSelectionViewModel(BranchSelectionViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FormsListViewModel.class)
    abstract ViewModel bindFormsListViewModel(FormsListViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(GuideViewModel.class)
    abstract ViewModel bindGuideViewModel(GuideViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(QuestionDetailsViewModel.class)
    abstract ViewModel bindQuestionDetailsViewModel(QuestionDetailsViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(QuestionOverviewViewModel.class)
    abstract ViewModel bindQuestionOverviewViewModel(QuestionOverviewViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(QuestionViewModel.class)
    abstract ViewModel bindQuestionViewModel(QuestionViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ChatViewModel.class)
    abstract ViewModel bindChatViewModel(ChatViewModel viewModel);

}
