package ro.code4.monitorizarevot.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ro.code4.monitorizarevot.BaseFragment;
import ro.code4.monitorizarevot.R;
import ro.code4.monitorizarevot.ToolbarActivity;
import ro.code4.monitorizarevot.db.Data;
import ro.code4.monitorizarevot.db.Preferences;
import ro.code4.monitorizarevot.net.model.Form;
import ro.code4.monitorizarevot.net.model.FormDetails;
import ro.code4.monitorizarevot.net.model.Version;
import ro.code4.monitorizarevot.util.FormRenderer;
import ro.code4.monitorizarevot.viewmodel.FormsListViewModel;
import ro.code4.monitorizarevot.widget.ChangeBranchBarLayout;
import ro.code4.monitorizarevot.widget.FormSelectorCard;

import static ro.code4.monitorizarevot.ToolbarActivity.BRANCH_SELECTION_BACKSTACK_INDEX;

public class FormsListFragment extends BaseFragment<FormsListViewModel> implements View.OnClickListener {
    View rootView;
    GridLayout grid;

    public static FormsListFragment newInstance() {
        return new FormsListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forms_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rootView = view;
        grid = view.findViewById(R.id.cardsContainer);

        renderLayout();

        setBranchBar((ChangeBranchBarLayout) view.findViewById(R.id.change_branch_bar));
    }

    private void renderLayout() {
        List<FormDetails> formDetailsList = viewModel.getFormDetails();
        grid.removeAllViews();

        if (formDetailsList.isEmpty()) {
            showRetrySyncSnackbar();
        } else {
            for (FormDetails formDetails : formDetailsList) {
                FormSelectorCard card = new FormSelectorCard(rootView.getContext());
                card.setLetter(formDetails.getCode());
                card.setText(formDetails.getDescription());
                card.setOnClickListener(this);
                card.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, FormRenderer.dpToPx(150, getResources())));
                card.setTag(formDetails);

                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                layoutParams.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
                layoutParams.height = FormRenderer.dpToPx(150, getResources());
                layoutParams.width = 0;

                grid.addView(card, layoutParams);
            }
        }

        FormSelectorCard card = new FormSelectorCard(rootView.getContext());
        card.setIcon(R.drawable.ic_notes);
        card.setText(getString(R.string.form_notes));
        card.setOnClickListener(this);

        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        layoutParams.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        layoutParams.height = FormRenderer.dpToPx(150, getResources());
        layoutParams.width = 0;

        grid.addView(card, layoutParams);
    }

    private void showRetrySyncSnackbar() {
        final Snackbar snackbar = Snackbar.make(rootView, getString(R.string.forms_sync_failed_description), Snackbar.LENGTH_INDEFINITE);

        snackbar.getView().setBackgroundColor(getResources().getColor(R.color.errorRed));
        snackbar.setActionTextColor(getResources().getColor(R.color.textLight));
        snackbar.setAction(getString(R.string.forms_sync_failed_retry), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();

                if (getActivity() != null && getActivity() instanceof ToolbarActivity) {
                    ((ToolbarActivity) getActivity()).requestSync(true);
                }
            }
        });

        snackbar.show();
    }

    public void onSyncedForms() {
        if (rootView != null && ! this.isRemoving()) {
            renderLayout();
        }
    }

    private void setBranchBar(ChangeBranchBarLayout barLayout) {
        barLayout.setBranchText(Preferences.getCountyCode() + " " + Preferences.getBranchNumber());
        barLayout.setChangeBranchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateBackUntil(BRANCH_SELECTION_BACKSTACK_INDEX);
            }
        });
    }

    private void showForm(Form form) {
        if (form != null && form.getSections() != null && !form.getSections().isEmpty()) {
            navigateTo(QuestionsOverviewFragment.newInstance(form.getId()));
        } else {
            Toast.makeText(getActivity(), getString(R.string.error_no_form_data), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public String getTitle() {
        return getString(R.string.title_forms_list);
    }

    @Override
    protected void setupViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(FormsListViewModel.class);
    }

    @Override
    public void onClick(View v) {
        if (!(v instanceof FormSelectorCard)) {
            return;
        }

        Object tag = v.getTag();

        if (!(tag instanceof FormDetails)) {
            navigateTo(AddNoteFragment.newInstance());
        } else {
            showForm(Data.getInstance().getForm(((FormDetails) tag).getCode()));
        }
    }
}
