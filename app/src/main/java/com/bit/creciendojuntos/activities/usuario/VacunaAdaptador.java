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

    private int resource;
    ArrayList<Vacuna> vacunas;


    public VacunaAdaptador(ArrayList<Vacuna> vacunas,int resource) {
        this.vacunas = vacunas;
        this.resource = resource;
    }

    @NonNull
    @Override
    public VacunaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resource, viewGroup, false);
        return new VacunaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VacunaViewHolder vacunaViewHolder, int index) {
        Vacuna vacuna = vacunas.get(index);

        vacunaViewHolder.tvPNombreVacunaCV.setText(vacuna.getNombreVacuna());
        vacunaViewHolder.tvPFechaDesdeCV.setText(vacuna.getFechaDesde());
        vacunaViewHolder.tvPProximaDosisCV.setText(vacuna.getProximaDosis());
    }

    @Override
    public int getItemCount() {
        return vacunas.size();
    }


    public class VacunaViewHolder extends RecyclerView.ViewHolder{

        private TextView tvPNombreVacunaCV;
        private TextView tvPFechaDesdeCV;
        private TextView tvPProximaDosisCV;
        public View view;


        public VacunaViewHolder(View view) {
            super(view);
            this.view = view;
            this.tvPNombreVacunaCV = view.findViewById(R.id.tvPNombreVacunaCV);
            this.tvPFechaDesdeCV = view.findViewById(R.id.tvPFechaDesde);
            this.tvPProximaDosisCV =view.findViewById(R.id.tvPProximaDosis);
        }
    }

}
