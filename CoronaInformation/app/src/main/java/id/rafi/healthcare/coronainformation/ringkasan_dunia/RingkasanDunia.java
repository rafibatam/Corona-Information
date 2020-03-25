package id.rafi.healthcare.coronainformation.ringkasan_dunia;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
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

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import id.rafi.healthcare.coronainformation.R;
import id.rafi.healthcare.coronainformation.data_service.BaseAPIService;
import id.rafi.healthcare.coronainformation.data_service.RetrofitClient;
import id.rafi.healthcare.coronainformation.data_service.UtilsAPI;
import id.rafi.healthcare.coronainformation.pojo_information.ModelInformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RingkasanDunia extends Fragment {
    private Context mContext;
    private BaseAPIService baseAPIService;
    private TextView lastUpdate, totalNegara;
    private List<ModelInformation> modelInformationList;
    private static final String TAG = RingkasanDunia.class.getSimpleName();
    private PieChart pieChart;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ringkasan_dunia, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        baseAPIService = RetrofitClient.getClient(UtilsAPI.BASE_URL_API).create(BaseAPIService.class);
        lastUpdate = view.findViewById(R.id.lastUpdateTime);
        totalNegara = view.findViewById(R.id.totalNegara);

        progressBar = view.findViewById(R.id.progressBar);

        pieChart = view.findViewById(R.id.chartDunia);
        pieChart.setNoDataText("");

        Call<List<ModelInformation>> getInformation = baseAPIService.getData();
        getInformation.enqueue(new Callback<List<ModelInformation>>() {
            @Override
            public void onResponse(Call<List<ModelInformation>> call, Response<List<ModelInformation>> response) {
                if (response.isSuccessful()) {

                    List<ModelInformation> modelInformationLists = response.body();
                    modelInformationList = new ArrayList<>();

                    totalNegara.setText("Total : " + modelInformationLists.size() + " Negara");

                    for (ModelInformation modelInformation : modelInformationLists) {
                        String getCountry = modelInformation.getCountry();
                        int getKasus = modelInformation.getCases();
                        int getDeaths = modelInformation.getDeaths();
                        int getSembuh = modelInformation.getRecovered();
                        int getKasusToday = modelInformation.getTodayCases();
                        int getTodayDeaths = modelInformation.getTodayDeaths();

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
                        modelInformation.setTodayDeaths(getTodayDeaths);
                        modelInformation.setTodayCases(getKasusToday);
                        modelInformationList.add(modelInformation);

                        setupChart(modelInformationList);
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

    private int totalCases(List<ModelInformation> modelInformationList) {
        int totalKasus = 0;

        for (int a = 0; a < modelInformationList.size(); a++) {
            totalKasus += modelInformationList.get(a).getCases();
        }

        return totalKasus;
    }

    private int totalDeath(List<ModelInformation> modelInformationList) {
        int totalDeaths = 0;

        for (int a = 0; a < modelInformationList.size(); a++) {
            totalDeaths += modelInformationList.get(a).getDeaths();
        }

        return totalDeaths;
    }

    private int totalRecovered(List<ModelInformation> modelInformationList) {
        int totalSembuh = 0;

        for (int a = 0; a < modelInformationList.size(); a++) {
            totalSembuh += modelInformationList.get(a).getRecovered();
        }

        return totalSembuh;
    }

    private int kematianHariIni(List<ModelInformation> modelInformationList) {
        int kematianToday = 0;

        for (int a = 0; a < modelInformationList.size(); a++) {
            kematianToday += modelInformationList.get(a).getTodayDeaths();
        }

        return kematianToday;
    }

    private int konfirmasiHariIni(List<ModelInformation> modelInformationList) {
        int konfirmasiToday = 0;

        for (int a = 0; a < modelInformationList.size(); a++) {
            konfirmasiToday += modelInformationList.get(a).getRecovered();
        }

        return konfirmasiToday;
    }

    private void setupChart(List<ModelInformation> modelInformationList) {
        progressBar.setVisibility(View.GONE);

        List<PieEntry> pieEntryList = new ArrayList<>();

        int totalSemua[] = {totalCases(modelInformationList), totalDeath(modelInformationList), totalRecovered(modelInformationList), kematianHariIni(modelInformationList), konfirmasiHariIni(modelInformationList)};
        String titleName[] = {"Cases", " Deaths", "Recovered", "Today Deaths", "Today Cases"};

        for (int a = 0; a < totalSemua.length; a++) {
            pieEntryList.add(new PieEntry(totalSemua[a], titleName[a]));
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntryList, "");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTextColor(Color.WHITE);
        pieData.setValueTextSize(14f);

        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawEntryLabels(false);
        pieChart.setData(pieData);
        pieChart.animateY(1000);
        pieChart.invalidate();
    }
}