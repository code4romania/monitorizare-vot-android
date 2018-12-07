package ro.code4.monitorizarevot.data.rest;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.*;
import ro.code4.monitorizarevot.net.model.BranchDetails;
import ro.code4.monitorizarevot.net.model.ResponseAnswerContainer;
import ro.code4.monitorizarevot.net.model.Section;
import ro.code4.monitorizarevot.net.model.User;
import ro.code4.monitorizarevot.net.model.response.Ack;
import ro.code4.monitorizarevot.net.model.response.ResponseNote;
import ro.code4.monitorizarevot.net.model.response.VersionResponse;
import ro.code4.monitorizarevot.net.model.response.question.QuestionResponse;
import rx.Observable;

public interface ApiService {

    @POST("/api/v1/access/token")
    Observable<Object> postAuth(@Body User user);

    @GET("/api/v1/formular")
    Observable<List<Section>> getForm(@Query("idformular") String formId);

    @GET("/api/v1/formular/versiune")
    Observable<VersionResponse> getFormVersion();

    @POST("/api/v1/sectie")
    Observable<Ack> postBranchDetails(@Body BranchDetails branchDetails);

    @POST("/api/v1/raspuns")
    Observable<QuestionResponse> postQuestionAnswer(@Body ResponseAnswerContainer responseAnswer);

    @Multipart
    @POST("/api/v1/note/ataseaza")
    Observable<ResponseNote> postNote(@Part MultipartBody.Part file,
                                      @Part MultipartBody.Part countyCode,
                                      @Part MultipartBody.Part branchNumber,
                                      @Part MultipartBody.Part questionId,
                                      @Part MultipartBody.Part description);

}