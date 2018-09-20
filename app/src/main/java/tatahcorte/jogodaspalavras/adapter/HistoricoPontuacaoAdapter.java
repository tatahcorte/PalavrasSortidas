package tatahcorte.jogodaspalavras.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import tatahcorte.jogodaspalavras.R;

public class HistoricoPontuacaoAdapter extends RecyclerView.Adapter {

    private static final DateFormat dateFormat = DateFormat.getDateInstance();
    @NonNull
    private final List<Object> itens;

    public HistoricoPontuacaoAdapter(@NonNull List<Object> itens) {
        this.itens = itens;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        int layout = viewType == 1 ? R.layout.historico_data : R.layout.historico_pontos;
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(layout, parent, false);

        if(viewType == 1){
            viewHolder = new DataViewHolder(view);
        } else {
            viewHolder = new PontuacaoViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof DataViewHolder){
            ((DataViewHolder)holder).bind((Date) itens.get(position));
        } else {
            ((PontuacaoViewHolder)holder).bind((Long) itens.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    @Override
    public int getItemViewType(int position) {
        return itens.get(position) instanceof Date ? 1 : 0;
    }

    class DataViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewData;

        public DataViewHolder(View itemView) {
            super(itemView);
            textViewData = itemView.findViewById(R.id.textViewData);
        }

        public void bind(Date date){
            textViewData.setText(dateFormat.format(date));
        }
    }

    class PontuacaoViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewPontos;

        public PontuacaoViewHolder(View itemView) {
            super(itemView);
            textViewPontos = itemView.findViewById(R.id.textViewPontos);
        }

        public void bind(Long pontos){
            textViewPontos.setText(String.valueOf(pontos));
        }
    }
}
