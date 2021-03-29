package com.bit.creciendojuntos.activities.usuario;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bit.creciendojuntos.R;
import com.bit.creciendojuntos.models.Dentista;
import com.bit.creciendojuntos.models.Pediatra;

import java.util.ArrayList;

public class PediatraAdaptador extends RecyclerView.Adapter<PediatraAdaptador.PediatraViewHolder> {

    private int resource;
    ArrayList<Pediatra> pediatras;

    public PediatraAdaptador(ArrayList<Pediatra> pediatras,int resource) {
        this.pediatras = pediatras;
        this.resource = resource;
    }

    //Metodo donde se crea la vista
    @NonNull
    @Override
    public PediatraAdaptador.PediatraViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resource, viewGroup, false);
        return new PediatraAdaptador.PediatraViewHolder(view);
    }

    //Metodo se definen los datos que se quieren mostrar en la vista
    @Override

    public void onBindViewHolder(@NonNull PediatraViewHolder pediatraViewHolder, int index) {
        Pediatra pediatra = pediatras.get(index);
        pediatraViewHolder.txViewNombrePediatra.setText(pediatra.getNombrePediatra());
        pediatraViewHolder.tvPFechaPediatraCV.setText(pediatra.getFechaPediatra());
        pediatraViewHolder.tvPPresionCV.setText(pediatra.getPresion());
        pediatraViewHolder.tvPPesoCV.setText(pediatra.getPeso());
        pediatraViewHolder.tvPTallaCV.setText(pediatra.getTalla());
        pediatraViewHolder.tvPImcCV.setText(pediatra.getImc());
        pediatraViewHolder.tvPAptitudCV.setText(pediatra. getAptitudFisica());
        pediatraViewHolder.tvPProximoControlCV.setText(pediatra.getProximoControl());
    }

    //Funcion que devuelve el numero de vista - cantidad de vacunas que tiene arraylist
    @Override
    public int getItemCount() {
        return pediatras.size();
    }

    public class PediatraViewHolder extends RecyclerView.ViewHolder{

        TextView txViewNombrePediatra;
        TextView tvPFechaPediatraCV;
        TextView tvPPresionCV;
        TextView tvPPesoCV;
        TextView tvPTallaCV;
        TextView tvPImcCV;
        TextView tvPAptitudCV;
        TextView tvPProximoControlCV;
        //public View view;


        public PediatraViewHolder(View view) {
            super(view);
            //  this.view = view;
            this.txViewNombrePediatra =  view.findViewById(R.id.txViewNombrePediatra);
            this.tvPFechaPediatraCV = view.findViewById(R.id.tvPFechaPediatraCV);
            this.tvPPresionCV = view.findViewById(R.id.tvPPresionCV);
            this.tvPPesoCV = view.findViewById(R.id.tvPPesoCV);
            this.tvPTallaCV = view.findViewById(R.id.tvPTallaCV);
            this.tvPImcCV = view.findViewById(R.id.tvPImcCV);
            this.tvPAptitudCV = view.findViewById(R.id.tvPAptitudCV);
            this.tvPProximoControlCV = view.findViewById(R.id.tvPProximoControlCV);

        }
    }
}

