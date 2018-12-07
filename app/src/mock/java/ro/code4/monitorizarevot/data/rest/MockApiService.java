package ro.code4.monitorizarevot.data.rest;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.MultipartBody;
import ro.code4.monitorizarevot.net.model.BranchDetails;
import ro.code4.monitorizarevot.net.model.ResponseAnswerContainer;
import ro.code4.monitorizarevot.net.model.Section;
import ro.code4.monitorizarevot.net.model.User;
import ro.code4.monitorizarevot.net.model.response.Ack;
import ro.code4.monitorizarevot.net.model.response.ResponseNote;
import ro.code4.monitorizarevot.net.model.response.VersionResponse;
import ro.code4.monitorizarevot.net.model.response.question.QuestionResponse;
import rx.Observable;

public class MockApiService implements ApiService {

    @Inject
    public MockApiService() {
    }

    @Override
    public Observable<Object> postAuth(User user) {
        return Observable.just((Object) "{\"access_token\": \"12335dssdbddre\"}").delay(1, TimeUnit.SECONDS);
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
    public Observable<QuestionResponse> postQuestionAnswer(ResponseAnswerContainer responseAnswer) {
        return null;
    }

    @Override
    public Observable<ResponseNote> postNote(MultipartBody.Part file, MultipartBody.Part countyCode, MultipartBody.Part branchNumber,
                                             MultipartBody.Part questionId, MultipartBody.Part description) {
        return null;
    }
}
