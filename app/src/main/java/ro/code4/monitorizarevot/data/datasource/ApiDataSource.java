package ro.code4.monitorizarevot.data.datasource;

import java.util.List;

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

public interface ApiDataSource {

    Observable<Boolean> login(User user);

    Observable<List<Section>> getForm(String formId);

    Observable<VersionResponse> getFormVersion();

    Observable<Ack> postBranchDetails(BranchDetails branchDetails);

    Observable<QuestionResponse> postQuestionAnswer(ResponseAnswerContainer responseAnswerContainer);

    Observable<ResponseNote> postNote(Note note);
}
