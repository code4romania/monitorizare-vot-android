package ro.code4.monitorizarevot.net;

import android.support.annotation.Nullable;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import ro.code4.monitorizarevot.net.model.Section;
import ro.code4.monitorizarevot.net.model.ResponseAnswerContainer;
import ro.code4.monitorizarevot.net.model.response.ResponseNote;
import ro.code4.monitorizarevot.net.model.response.VersionResponse;
import ro.code4.monitorizarevot.net.model.response.question.QuestionResponse;

public interface ApiService {
    @GET("/api/v1/formular")
    Call<List<Section>> getForm(@Query("idformular") String formId);

    @GET("/api/v1/formular/versiune")
    Call<VersionResponse> getFormVersion();

    @POST("/api/v1/raspuns")
    Call<QuestionResponse> postQuestionAnswer(@Body ResponseAnswerContainer responseAnswer);

    @Multipart
    @POST("/api/v1/note/ataseaza")
    Call<ResponseNote> postNote(@Part MultipartBody.Part file,
                                @Part MultipartBody.Part countyCode,
                                @Part MultipartBody.Part branchNumber,
                                @Part MultipartBody.Part questionId,
                                @Part MultipartBody.Part description);
}
