package ro.code4.votehack.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ro.code4.votehack.R;
import ro.code4.votehack.net.model.Question;
import ro.code4.votehack.net.model.Section;
import ro.code4.votehack.net.model.response.ResponseAnswer;
import ro.code4.votehack.util.QuestionsOverviewNavigator;

public class QuestionsOverviewAdapter extends RecyclerView.Adapter {
    private Context context;
    private QuestionsOverviewNavigator navigator;
    private String sectionCode;
    private List<Question> questions;

    public QuestionsOverviewAdapter(Context context, Section section, QuestionsOverviewNavigator navigator) {
        this.context = context;
        this.sectionCode = section.getSectionCode();
        this.questions = section.getQuestionList();
        this.navigator = navigator;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_holder_question_overview, parent, false);
        return new QuestionsOverviewViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, int position) {
        final QuestionsOverviewViewHolder holder = (QuestionsOverviewViewHolder) vh;
        boolean hasAnswer = questions.get(position).getRaspunsIntrebare() != null;
        holder.header.setText(sectionCode.concat(String.valueOf(position + 1)));
        holder.description.setText(questions.get(position).getText());
        holder.status.setText(hasAnswer ?
                context.getString(R.string.question_complete) :
                context.getString(R.string.question_incomplete));
        holder.status.setTextColor(hasAnswer ?
                ContextCompat.getColor(context, R.color.complete) :
                ContextCompat.getColor(context, R.color.incomplete));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.showQuestionDetails(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    private class QuestionsOverviewViewHolder extends RecyclerView.ViewHolder {
        TextView header, description, status;
        CardView cardView;

        QuestionsOverviewViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            header = (TextView) itemView.findViewById(R.id.header);
            description = (TextView) itemView.findViewById(R.id.description);
            status = (TextView) itemView.findViewById(R.id.status);
        }
    }
}
