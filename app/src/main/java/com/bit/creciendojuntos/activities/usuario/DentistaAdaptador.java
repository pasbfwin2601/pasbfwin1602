package com.bit.creciendojuntos.activities.usuario;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bit.creciendojuntos.R;
import com.bit.creciendojuntos.models.Dentista;


import java.util.ArrayList;

public class DentistaAdaptador extends RecyclerView.Adapter<DentistaAdaptador.DentistaViewHolder> {

    private int resource;
    ArrayList<Dentista> dentistas;

    public DentistaAdaptador(ArrayList<Dentista> dentistas,int resource) {
        this.dentistas = dentistas;
        this.resource = resource;
    }

    //Metodo donde se crea la vista
    @NonNull
    @Override
    public DentistaAdaptador.DentistaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resource, viewGroup, false);
        return new DentistaAdaptador.DentistaViewHolder(view);
    }

    //Metodo se definen los datos que se quieren mostrar en la vista
    @Override

    public void onBindViewHolder(@NonNull DentistaAdaptador.DentistaViewHolder dentistaViewHolder, int index) {
        Dentista dentista = dentistas.get(index);
        dentistaViewHolder.txViewNombreDentista.setText(dentista.getNombreDentista());
        dentistaViewHolder.tvPCariesCV.setText(dentista.getCarie());
        dentistaViewHolder.tvPExtraccionesCV.setText(dentista.getExtraccion());
        dentistaViewHolder.tvPFechaDentistaCV.setText(dentista.getFechaControl());
        dentistaViewHolder.tvPGingivitisCV.setText(dentista.getGingivitis());
        dentistaViewHolder.tvPTratamientoDentistaCV.setText(dentista.getTratamiento());
        dentistaViewHolder.tvPProximaDentistaCV.setText(dentista.getProximaVisita());
    }

    //Funcion que devuelve el numero de vista - cantidad de vacunas que tiene arraylist
    @Override
    public int getItemCount() {
        return dentistas.size();
    }

    public class DentistaViewHolder extends RecyclerView.ViewHolder{

        TextView txViewNombreDentista;
        TextView tvPFechaDentistaCV;
        TextView tvPCariesCV;
        TextView tvPExtraccionesCV;
        TextView tvPGingivitisCV;
        TextView tvPTratamientoDentistaCV;
        TextView tvPProximaDentistaCV;
        //public View view;


        public DentistaViewHolder(View view) {
            super(view);
            //  this.view = view;
            this.txViewNombreDentista =  view.findViewById(R.id.txViewNombreDentista);
            this.tvPFechaDentistaCV = view.findViewById(R.id.tvPFechaDentistaCV);
            this.tvPCariesCV = view.findViewById(R.id.tvPCariesCV);
            this.tvPExtraccionesCV = view.findViewById(R.id.tvPExtraccionesCV);
            this.tvPGingivitisCV = view.findViewById(R.id.tvPGingivitisCV);
            this.tvPTratamientoDentistaCV = view.findViewById(R.id.tvPTratamientoDentistaCV);
            this.tvPProximaDentistaCV = view.findViewById(R.id.tvPProximaDentistaCV);

        }
    }


}
