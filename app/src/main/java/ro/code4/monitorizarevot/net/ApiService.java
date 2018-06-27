package ro.code4.monitorizarevot.net;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import ro.code4.monitorizarevot.constants.Api;
import ro.code4.monitorizarevot.net.model.BranchDetails;
import ro.code4.monitorizarevot.net.model.ResponseAnswerContainer;
import ro.code4.monitorizarevot.net.model.Section;
import ro.code4.monitorizarevot.net.model.User;
import ro.code4.monitorizarevot.net.model.response.Ack;
import ro.code4.monitorizarevot.net.model.response.ResponseNote;
import ro.code4.monitorizarevot.net.model.response.VersionResponse;
import ro.code4.monitorizarevot.net.model.response.question.QuestionResponse;

public interface ApiService {

    @POST(Api.AUTH)
    Call<Object>postAuth(@Body User user);

    @GET(Api.FORM)
    Call<List<Section>> getForm(@Query(Api.KEY_FORM_ID) String formId);

    @GET(Api.FORM_VERSION)
    Call<VersionResponse> getFormVersion();

    @POST(Api.BRANCH)
    Call<Ack> postBranchDetails(@Body BranchDetails branchDetails);

    @POST(Api.QUESTION_ANSWER)
    Call<QuestionResponse> postQuestionAnswer(@Body ResponseAnswerContainer responseAnswer);

    @Multipart
    @POST(Api.NOTE)
    Call<ResponseNote> postNote(@Part MultipartBody.Part file,
                                @Part MultipartBody.Part countyCode,
                                @Part MultipartBody.Part branchNumber,
                                @Part MultipartBody.Part questionId,
                                @Part MultipartBody.Part description);
}
