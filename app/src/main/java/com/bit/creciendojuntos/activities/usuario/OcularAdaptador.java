package com.bit.creciendojuntos.activities.usuario;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bit.creciendojuntos.R;
import com.bit.creciendojuntos.models.Ocular;
import com.bit.creciendojuntos.models.Vacuna;

import java.util.ArrayList;

public class OcularAdaptador extends RecyclerView.Adapter<OcularAdaptador.OcularViewHolder> {

    private int resource;
    ArrayList<Ocular> oculares;

    public OcularAdaptador(ArrayList<Ocular> oculares,int resource) {
        this.oculares = oculares;
        this.resource = resource;
    }

    //Metodo donde se crea la vista
    @NonNull
    @Override
    public OcularAdaptador.OcularViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resource, viewGroup, false);
        return new OcularAdaptador.OcularViewHolder(view);
    }

    //Metodo se definen los datos que se quieren mostrar en la vista
    @Override

    public void onBindViewHolder(@NonNull OcularViewHolder ocularViewHolder, int index) {
        Ocular ocular = oculares.get(index);
        ocularViewHolder.txViewNombreOculista.setText(ocular.getNombreOculista());
        ocularViewHolder.tvPAstigmatismoCV.setText(ocular.getAstigmatismo());
        ocularViewHolder.tvPDiagnosticoOcularCV.setText(ocular.getDiagnostico());
        ocularViewHolder.tvPFechaControlOcularCV.setText(ocular.getFechaControl());
        ocularViewHolder.tvPGraduacionODCV.setText(ocular.getGraduacionOD());
        ocularViewHolder.tvPGraduacionOICV.setText(ocular. getGraduacionOI());
        ocularViewHolder.tvPHipermetropiaCV.setText(ocular.getHipermetropia());
        ocularViewHolder.tvPProximoControlOcularCV.setText(ocular. getProximoControl());
        ocularViewHolder.tvPTratamientoOcularCV.setText(ocular.getTratamiento());

    }

    //Funcion que devuelve el numero de vista - cantidad de vacunas que tiene arraylist
    @Override
    public int getItemCount() {
        return oculares.size();
    }

    public class OcularViewHolder extends RecyclerView.ViewHolder{

        TextView txViewNombreOculista;
        TextView tvPAstigmatismoCV;
        TextView tvPDiagnosticoOcularCV;
        TextView tvPFechaControlOcularCV;
        TextView tvPGraduacionODCV;
        TextView tvPGraduacionOICV;
        TextView tvPHipermetropiaCV;
        TextView tvPProximoControlOcularCV;
        TextView tvPTratamientoOcularCV;
        //public View view;


        public OcularViewHolder(View view) {
            super(view);
            //  this.view = view;
            this.txViewNombreOculista =  view.findViewById(R.id.txViewNombreOculista);
            this.tvPAstigmatismoCV = view.findViewById(R.id.tvPAstigmatismoCV);
            this.tvPDiagnosticoOcularCV = view.findViewById(R.id.tvPDiagnosticoOcularCV);
            this.tvPFechaControlOcularCV = view.findViewById(R.id.tvPFechaControlOcularCV);
            this.tvPGraduacionODCV = view.findViewById(R.id.tvPGraduacionODCV);
            this.tvPGraduacionOICV = view.findViewById(R.id.tvPGraduacionOICV);
            this.tvPHipermetropiaCV = view.findViewById(R.id.tvPHipermetropiaCV);
            this.tvPProximoControlOcularCV = view.findViewById(R.id.tvPProximoControlOcularCV);
            this.tvPTratamientoOcularCV = view.findViewById(R.id.tvPTratamientoOcularCV);






        }
    }
}
