package ro.code4.monitorizarevot.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ro.code4.monitorizarevot.R;
import ro.code4.monitorizarevot.data.chat.ChatMessage;
import ro.code4.monitorizarevot.data.chat.ChatMessageType;

public class MessageListAdapter extends BaseAdapter<ChatMessage, MessageListAdapter.ChatMessageViewHolder> {

    private static final int ITEM_TYPE_RECEIVED = 0;

    private static final int ITEM_TYPE_SENT = 1;

    @Inject
    public MessageListAdapter(Context context) {
        super(context, null);
    }

    @NonNull
    @Override
    public ChatMessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Integer layoutResourceId;
        switch (viewType) {
            case ITEM_TYPE_RECEIVED:
                layoutResourceId = R.layout.chat_message_received;
                break;

            case ITEM_TYPE_SENT:
                layoutResourceId = R.layout.chat_message_sent;
                break;

            default:
                layoutResourceId = R.layout.chat_message_sent;
                break;
        }

        View view = LayoutInflater.from(mContext).inflate(layoutResourceId, null);

        return new ChatMessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatMessageViewHolder chatMessageViewHolder, int position) {
        chatMessageViewHolder.mMessage.setText(mItems.get(position).getMessage());
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getType() == ChatMessageType.SENT.ordinal() ? ITEM_TYPE_SENT : ITEM_TYPE_RECEIVED;
    }

    static class ChatMessageViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        @BindView(R.id.message)
        TextView mMessage;

        ChatMessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
