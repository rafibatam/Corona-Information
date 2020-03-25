package id.rafi.healthcare.coronainformation.data_dunia.adapter_data;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.rafi.healthcare.coronainformation.R;
import id.rafi.healthcare.coronainformation.full_information.FullCoronaInformation;
import id.rafi.healthcare.coronainformation.pojo_information.ModelInformation;

public class AdapterDataDunia extends RecyclerView.Adapter<AdapterDataDunia.ViewHolder> {
    List<ModelInformation> modelInformationList;
    Context context;

    public AdapterDataDunia(Context context, List<ModelInformation> modelInformationList) {
        this.context = context;
        this.modelInformationList = modelInformationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_data_dunia, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ModelInformation modelInformation = modelInformationList.get(position);

        holder.jumlahConfirm.setText(modelInformation.getCases() + "");
        holder.titleNegara.setText(modelInformation.getCountry());
        holder.jumlahSembuh.setText(modelInformation.getRecovered() + "");
        holder.jumlahKematian.setText(modelInformation.getDeaths() + "");
        holder.mainRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveDetail = new Intent(context, FullCoronaInformation.class);
                moveDetail.putExtra("country", modelInformation.getCountry());
                moveDetail.putExtra("cases", modelInformation.getCases());
                moveDetail.putExtra("todayCases", modelInformation.getTodayCases());
                moveDetail.putExtra("deaths", modelInformation.getDeaths());
                moveDetail.putExtra("todayDeaths", modelInformation.getTodayDeaths());
                moveDetail.putExtra("recovered", modelInformation.getRecovered());
                moveDetail.putExtra("active", modelInformation.getActive());
                context.startActivity(moveDetail);
            }
        });
        holder.fullInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveDetail = new Intent(context, FullCoronaInformation.class);
                moveDetail.putExtra("country", modelInformation.getCountry());
                moveDetail.putExtra("cases", modelInformation.getCases());
                moveDetail.putExtra("todayCases", modelInformation.getTodayCases());
                moveDetail.putExtra("deaths", modelInformation.getDeaths());
                moveDetail.putExtra("todayDeaths", modelInformation.getTodayDeaths());
                moveDetail.putExtra("recovered", modelInformation.getRecovered());
                moveDetail.putExtra("active", modelInformation.getActive());
                context.startActivity(moveDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelInformationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView jumlahConfirm, jumlahSembuh, jumlahKematian, titleNegara;
        private RelativeLayout mainRelative;
        private ImageView fullInformation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fullInformation = itemView.findViewById(R.id.imageInformation);
            mainRelative = itemView.findViewById(R.id.mainRelative);
            titleNegara = itemView.findViewById(R.id.titleNegara);
            jumlahConfirm = itemView.findViewById(R.id.jumlahConfirm);
            jumlahSembuh = itemView.findViewById(R.id.jumlahSembuh);
            jumlahKematian = itemView.findViewById(R.id.jumlahKematian);
        }
    }
}
