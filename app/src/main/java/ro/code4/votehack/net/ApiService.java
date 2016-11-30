package ro.code4.votehack.net;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ro.code4.votehack.net.model.Section;
import ro.code4.votehack.net.model.ResponseAnswerContainer;
import ro.code4.votehack.net.model.response.VersionResponse;
import ro.code4.votehack.net.model.response.question.QuestionResponse;

public interface ApiService {
    @GET("/api/v1/formular?idformular={formId}")
    Call<List<Section>> getForm(@Path("formId") int formId);

    @GET("/api/v1/formular/versiune")
    Call<VersionResponse> getFormVersion();

    @POST("/api/v1/raspuns")
    Call<QuestionResponse> postQuestionAnswer(@Body ResponseAnswerContainer responseAnswer);

}
