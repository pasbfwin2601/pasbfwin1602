package com.bit.creciendojuntos.activities.usuario;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bit.creciendojuntos.R;
import com.bit.creciendojuntos.models.Vacuna;

import java.util.ArrayList;

public class VacunaAdaptador extends RecyclerView.Adapter<VacunaAdaptador.VacunaViewHolder> {

    ArrayList<Vacuna> vacunas;


    public VacunaAdaptador(ArrayList<Vacuna> vacunas) {
        this.vacunas = vacunas;
    }

    @NonNull
    @Override
    public VacunaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_vacuna, parent, false);
        return new VacunaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VacunaViewHolder vacunaViewHolder, int position) {
        Vacuna vacuna = vacunas.get(position);
        

        Log.e("PNnombre vacuna","PNnombre vacuna"+vacuna.getNombreVacuna());
        Log.e("PFfecha desde","PFfecha desde"+vacuna.getFechaDesde());
        Log.e("PPProximaDosis","PPProximaDosis"+vacuna.getProximaDosis());
        vacunaViewHolder.tvPNombreVacunaCV.setText(vacuna.getNombreVacuna());
        vacunaViewHolder.tvPFechaDesdeCV.setText(vacuna.getFechaDesde());
        vacunaViewHolder.tvPProximaDosisCV.setText(vacuna.getProximaDosis());
    }

    @Override
    public int getItemCount() {
        return vacunas.size();
    }

    public static class VacunaViewHolder extends RecyclerView.ViewHolder{

        private TextView tvPNombreVacunaCV;
        private TextView tvPFechaDesdeCV;
        private TextView tvPProximaDosisCV;

        public VacunaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPNombreVacunaCV = itemView.findViewById(R.id.tvPNombreVacunaCV);
            tvPFechaDesdeCV = itemView.findViewById(R.id.tvPFechaDesde);
            tvPProximaDosisCV = itemView.findViewById(R.id.tvPProximaDosis);
        }
    }

}
