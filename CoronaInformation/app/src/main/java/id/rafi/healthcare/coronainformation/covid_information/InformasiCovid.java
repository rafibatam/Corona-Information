package id.rafi.healthcare.coronainformation.covid_information;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import id.rafi.healthcare.coronainformation.R;

public class InformasiCovid extends Fragment {
    private Context mContext;
    private TextView descriptionCovid, descriptionBagaimanaGejala, descriptionPencegahan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_informasi_covid, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        descriptionCovid = view.findViewById(R.id.descriptionApaItu);
        descriptionCovid.setText("COVID-19 merupakan keluarga besar dari virus SARS and MERS yang menyebabkan penyakit mulai dari gejala ringan sampai berat. Virus ini merupakan virus jenis baru yang belum pernah diidentifikasi sebelumnya pada manusia, yang kemudian pertama kali didentifikasi di kota Wuhan, China pada Desember 2019." + "\n\nVirus corona dapat ditularkan antara hewan ke manusia dan manusia ke manusia. Gejala klinis akan muncul setelah 2-14 hari setelah terinfeksi, namun dapat menular meski belum menunjukkan gelaja infeksi.");

        descriptionBagaimanaGejala = view.findViewById(R.id.descriptionBagaimanaGejala);
        descriptionBagaimanaGejala.setText("Jika seseorang terinfeksi virus corona, maka akan mengalami gejala seperti berikut:\n\n" +
                "1. Demam selama beberapa hari\n" +
                "2. Batuk\n" +
                "3. Nyeri Tenggorokan\n" +
                "4. Kesulitan bernafas\n" +
                "5. Flu/pilek");

        descriptionPencegahan = view.findViewById(R.id.descriptionBagaimanaPencegahan);
        descriptionPencegahan.setText("Saran Kesehatan:\n" +
                "1. Cuci tangan sesering mungkin menggunakan sabun di air mengalir\n" +
                "2. Hindari menyentuh hewan liar dan unggas liar\n" +
                "3. Hindari menyentuh wajah, mulut dan hidung\n" +
                "4. Gunakan masker bila berada diruang publik\n" +
                "5. Konsumsi makanan yang sehat dan bergizi\n" +
                "6. Periksakan ke dokter jika mengalami gejala infeksi\n\n" +

                "Saran Bepergian:\n" +
                "1. Jangan bepergian jika dalam kondisi kurang sehat\n" +
                "2. Jika tidak ada urusan yang sangat mendesak, sebaiknya tidak bepergian ke luar negeri apalagi membawa anak.");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }
}
