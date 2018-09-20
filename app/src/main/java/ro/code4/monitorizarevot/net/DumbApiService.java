package ro.code4.monitorizarevot.net;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ro.code4.monitorizarevot.net.model.BranchDetails;
import ro.code4.monitorizarevot.net.model.ResponseAnswerContainer;
import ro.code4.monitorizarevot.net.model.Section;
import ro.code4.monitorizarevot.net.model.User;
import ro.code4.monitorizarevot.net.model.response.Ack;
import ro.code4.monitorizarevot.net.model.response.ResponseNote;
import ro.code4.monitorizarevot.net.model.response.VersionResponse;
import ro.code4.monitorizarevot.net.model.response.question.QuestionResponse;

public class DumbApiService implements ApiService {

    public Call<Object> postAuth(final User user) {
        return new DumbCall<Object>() {
            public Response<Object> execute() {
                if (user.getPin().equals("0000")) {
                    return Response.error(400, ResponseBody.create(MediaType.parse("application/json"), ""));
                }

                return Response.<Object>success("{\"access_token\":\"accessTOKEN\"}");
            }
        };
    }

    public Call<List<Section>> getForm(final String formId) {
        return new DumbCall<List<Section>>() {
            public Response<List<Section>> execute() {
                List<Section> sections = new ArrayList<>();
                Gson gson = new Gson();

                String q = "{\"idIntrebare\":1, \"textIntrebare\":\"To be, or not to be\", \"codIntrebare\":\"Q1.1\", \"idTipIntrebare\":2, "
                        + "\"raspunsuriDisponibile\": ["
                        + "{\"idOptiune\": 1, \"textOptiune\": \"Option 1\", \"seIntroduceText\": false}, "
                        + "{\"idOptiune\": 2, \"textOptiune\": \"Option 2, provide more details\", \"seIntroduceText\": true}"
                        + "], "
                + " \"branchQuestionAnswer\": null}";

                String q2 = "{\"idIntrebare\":2, \"textIntrebare\":\"To be, to work, to drink\", \"codIntrebare\":\"Q1.2\", \"idTipIntrebare\":0, "
                        + "\"raspunsuriDisponibile\": ["
                        + "{\"idOptiune\": 3, \"textOptiune\": \"To be\", \"seIntroduceText\": false}, "
                        + "{\"idOptiune\": 4, \"textOptiune\": \"To work\", \"seIntroduceText\": false}, "
                        + "{\"idOptiune\": 5, \"textOptiune\": \"To drink\", \"seIntroduceText\": false} "
                        + "], "
                        + " \"branchQuestionAnswer\": null}";

                sections.add(gson.fromJson("{\"codSectiune\": \"unused\", \"descriere\":\"Form description\", \"intrebari\":"
                        + "[" + q + ", " + q2 + "]}", Section.class));

                return Response.success(sections);
            }
        };
    }

    public Call<VersionResponse> getFormVersion() {
        return new DumbCall<VersionResponse>() {
            public Response<VersionResponse> execute() {
                Gson gson = new Gson();
                VersionResponse resp = gson.fromJson("{\"versiune\":{\"A\":1, \"B\":1, \"C\":1}}", VersionResponse.class);
                return Response.success(resp);
            }
        };
    }

    public Call<Ack> postBranchDetails(BranchDetails branchDetails) {
        return new DumbCall<Ack>() {
            @Override
            public Response<Ack> execute() {
                return Response.success(new Ack());
            }
        };
    }

    public Call<QuestionResponse> postQuestionAnswer(final ResponseAnswerContainer responseAnswer) {
        // TODO store responseAnswer.getReponseMapperList();

        return new DumbCall<QuestionResponse>() {
            @Override
            public Response<QuestionResponse> execute() {
                return Response.success(new QuestionResponse());
            }
        };
    }

    public Call<ResponseNote> postNote(MultipartBody.Part file, MultipartBody.Part countyCode, MultipartBody.Part branchNumber, MultipartBody.Part questionId, MultipartBody.Part description) {
        return new DumbCall<ResponseNote>() {
            @Override
            public Response<ResponseNote> execute() {
                return Response.success(new ResponseNote());
            }
        };
    }

    public abstract class DumbCall<T> implements Call<T> {
        public void enqueue(Callback<T> callback) {
        }

        public boolean isExecuted() {
            return false;
        }

        public void cancel() {
        }

        public boolean isCanceled() {
            return false;
        }

        public Call<T> clone() {
            return null;
        }

        public Request request() {
            return null;
        }
    }
}
