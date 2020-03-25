package id.rafi.healthcare.coronainformation.data_service;

public class UtilsAPI {
    public static final String BASE_URL_API = "https://siakad.iteba.ac.id/api/";

    public static BaseAPIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL_API).create(BaseAPIService.class);
    }
}
