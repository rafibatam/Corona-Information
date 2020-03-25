package id.rafi.healthcare.coronainformation.full_information;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import id.rafi.healthcare.coronainformation.R;

public class FullCoronaInformation extends AppCompatActivity {
    private TextView titleNegara, textViewBubble;
    private ImageView arrowBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_corona_information);

        titleNegara = findViewById(R.id.titleNegara);

        String getCountry = getIntent().getStringExtra("country");
        titleNegara.setText(getCountry);

        int totalConfirm = getIntent().getIntExtra("cases", 0);
        int confirmToday = getIntent().getIntExtra("todayCases", 0);
        int kematian = getIntent().getIntExtra("deaths", 0);
        int kematianToday = getIntent().getIntExtra("todayDeaths", 0);
        int sembuh = getIntent().getIntExtra("recovered", 0);
        int actives = getIntent().getIntExtra("active", 0);

        textViewBubble = findViewById(R.id.textViewBubble);
        textViewBubble.setText("Total Terkonfirmasi : " + totalConfirm + "\n\n"
                + "Terkonfirmasi Hari Ini : " + confirmToday + "\n\n"
                + "Total Kematian : " + kematian + "\n\n"
                + "Kematian Hari Ini : " + kematianToday + "\n\n"
                + "Total Sembuh : " + sembuh + "\n\n"
                + "Dalam Perawatan : " + actives);

        arrowBack = findViewById(R.id.arrowBack);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}