package id.rafi.healthcare.coronainformation.data_dunia;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import id.rafi.healthcare.coronainformation.R;
import id.rafi.healthcare.coronainformation.data_dunia.adapter_data.AdapterDataDunia;
import id.rafi.healthcare.coronainformation.data_service.BaseAPIService;
import id.rafi.healthcare.coronainformation.data_service.RetrofitClient;
import id.rafi.healthcare.coronainformation.data_service.UtilsAPI;
import id.rafi.healthcare.coronainformation.pojo_information.ModelInformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataDunia extends Fragment {
    private Context mContext;
    private RecyclerView recyclerView;
    private static final String TAG = DataDunia.class.getSimpleName();
    private AdapterDataDunia adapterDataDunia;
    private List<ModelInformation> modelInformationList;
    private BaseAPIService baseAPIService;
    private ProgressBar progressBar;
    private TextView lastUpdate, totalNegara;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_data_dunia, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        baseAPIService = RetrofitClient.getClient(UtilsAPI.BASE_URL_API).create(BaseAPIService.class);
        progressBar = view.findViewById(R.id.mainProgress);
        lastUpdate = view.findViewById(R.id.lastUpdateTime);
        totalNegara = view.findViewById(R.id.totalNegara);

        recyclerView = view.findViewById(R.id.recyclerViewDataDunia);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        Call<List<ModelInformation>> getInformation = baseAPIService.getData();
        getInformation.enqueue(new Callback<List<ModelInformation>>() {
            @Override
            public void onResponse(Call<List<ModelInformation>> call, Response<List<ModelInformation>> response) {
                if (response.isSuccessful()) {

                    List<ModelInformation> modelInformationLists = response.body();
                    modelInformationList = new ArrayList<>();
                    progressBar.setVisibility(View.GONE);

                    totalNegara.setText("Total : " + modelInformationLists.size() + " Negara");

                    for (ModelInformation modelInformation : modelInformationLists) {
                        String getCountry = modelInformation.getCountry();
                        int getKasus = modelInformation.getCases();
                        int getDeaths = modelInformation.getDeaths();
                        int getSembuh = modelInformation.getRecovered();
                        int getKasusToday = modelInformation.getTodayCases();
                        int getTodayDeaths = modelInformation.getTodayDeaths();
                        int getActives = modelInformation.getActive();

                        String getLastUpdate = modelInformation.getLastUpdate();
                        String getFixDate = getLastUpdate.substring(0, getLastUpdate.length() - 17);
                        String getFixTime = getLastUpdate.substring(11, getLastUpdate.length() - 8);

                        SimpleDateFormat firstSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat secondSimpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");

                        try {
                            String currentDate = secondSimpleDateFormat.format(firstSimpleDateFormat.parse(getFixDate));
                            lastUpdate.setText(currentDate + ", " + getFixTime + " WIB");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        modelInformation.setCountry(getCountry);
                        modelInformation.setCases(getKasus);
                        modelInformation.setDeaths(getDeaths);
                        modelInformation.setRecovered(getSembuh);
                        modelInformation.setTodayCases(getKasusToday);
                        modelInformation.setTodayDeaths(getTodayDeaths);
                        modelInformation.setActive(getActives);
                        modelInformationList.add(modelInformation);

                        adapterDataDunia = new AdapterDataDunia(mContext, modelInformationList);
                        recyclerView.setAdapter(adapterDataDunia);
                    }

                } else {
                    Toast.makeText(mContext, "Check your connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ModelInformation>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }
}