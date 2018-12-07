package ro.code4.monitorizarevot.data.datasource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ro.code4.monitorizarevot.net.model.BranchDetails;
import ro.code4.monitorizarevot.net.model.Note;
import ro.code4.monitorizarevot.net.model.ResponseAnswerContainer;
import ro.code4.monitorizarevot.net.model.Section;
import ro.code4.monitorizarevot.net.model.User;
import ro.code4.monitorizarevot.net.model.response.Ack;
import ro.code4.monitorizarevot.net.model.response.ResponseNote;
import ro.code4.monitorizarevot.net.model.response.VersionResponse;
import ro.code4.monitorizarevot.net.model.response.question.QuestionResponse;
import rx.Observable;

@Singleton
public class LocalDataSource implements ApiDataSource {

    @Inject
    public LocalDataSource() {
    }

    @Override
    public Observable<Boolean> login(User user) {
        return null;
    }

    @Override
    public Observable<List<Section>> getForm(String formId) {
        return null;
    }

    @Override
    public Observable<VersionResponse> getFormVersion() {
        return null;
    }

    @Override
    public Observable<Ack> postBranchDetails(BranchDetails branchDetails) {
        return null;
    }

    @Override
    public Observable<QuestionResponse> postQuestionAnswer(ResponseAnswerContainer responseAnswerContainer) {
        return null;
    }

    @Override
    public Observable<ResponseNote> postNote(Note note) {
        return null;
    }
}
