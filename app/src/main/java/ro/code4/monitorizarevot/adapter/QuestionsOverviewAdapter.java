package ro.code4.monitorizarevot.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ro.code4.monitorizarevot.R;
import ro.code4.monitorizarevot.net.model.Form;
import ro.code4.monitorizarevot.net.model.Question;
import ro.code4.monitorizarevot.util.FormUtils;
import ro.code4.monitorizarevot.util.QuestionsOverviewNavigator;

public class QuestionsOverviewAdapter extends RecyclerView.Adapter {
    private Context context;
    private QuestionsOverviewNavigator navigator;
    private List<Question> questions;

    public QuestionsOverviewAdapter(Context context, Form form, QuestionsOverviewNavigator navigator) {
        this.context = context;
        this.navigator = navigator;
        this.questions = FormUtils.getAllQuestions(form.getId());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_holder_question_overview, parent, false);
        return new QuestionsOverviewViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder vh, int position) {
        final QuestionsOverviewViewHolder holder = (QuestionsOverviewViewHolder) vh;
        Question question = questions.get(position);
        boolean hasAnswer = question.getAnswers().size() > 0;
        holder.header.setText(question.getCode());
        holder.description.setText(question.getText());
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
            cardView = itemView.findViewById(R.id.card_view);
            header = itemView.findViewById(R.id.header);
            description = itemView.findViewById(R.id.description);
            status = itemView.findViewById(R.id.status);
        }
    }
}
