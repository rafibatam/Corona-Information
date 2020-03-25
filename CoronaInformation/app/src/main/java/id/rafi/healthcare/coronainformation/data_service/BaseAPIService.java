package id.rafi.healthcare.coronainformation.data_service;

import java.util.List;

import id.rafi.healthcare.coronainformation.pojo_information.ModelInformation;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface BaseAPIService {
    @Headers("Content-Type:application/json")
    @GET("covid19")
    Call<List<ModelInformation>> getData();
}
