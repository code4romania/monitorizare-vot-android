package ro.code4.monitorizarevot.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnEditorAction;
import ro.code4.monitorizarevot.BaseFragment;
import ro.code4.monitorizarevot.Injectable;
import ro.code4.monitorizarevot.R;
import ro.code4.monitorizarevot.adapter.MessageListAdapter;
import ro.code4.monitorizarevot.adapter.RvListener;
import ro.code4.monitorizarevot.data.chat.ChatMessage;
import ro.code4.monitorizarevot.viewmodel.ChatViewModel;

public class ChatFragment extends BaseFragment<ChatViewModel> implements Injectable {

    @BindView(R.id.message_input)
    EditText mMessageEditTxt;

    @BindView(R.id.messages_list)
    RecyclerView mMessagesList;

    @Inject
    MessageListAdapter mAdapter;

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mMessagesList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMessagesList.setAdapter(mAdapter);
        mMessagesList.addOnItemTouchListener(new RvListener(getActivity(), new RvListener.ItemClickListenerAdapter() {

            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        }));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLifecycle().addObserver(viewModel);

        viewModel.history().observe(getActivity(), new Observer<List<ChatMessage>>() {

            @Override
            public void onChanged(@Nullable List<ChatMessage> chatMessageList) {
                mAdapter.setItems(chatMessageList);
            }
        });

        viewModel.newMessage().observe(getActivity(), new Observer<ChatMessage>() {

            @Override
            public void onChanged(@Nullable ChatMessage message) {
                List<ChatMessage> items = mAdapter.getItems();
                items.add(message);

                mAdapter.setItems(items);
            }
        });
    }

    @Override
    public String getTitle() {
        return getString(R.string.title_chat);
    }

    @Override
    protected void setupViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(ChatViewModel.class);
    }

    @OnEditorAction(R.id.message_input)
    boolean onSendMessage(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_DONE) {
            viewModel.sendMessage(mMessageEditTxt.getText().toString());
            mMessageEditTxt.setText(null);
        }
        return false;
    }
}
