package tatahcorte.jogodaspalavras.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import tatahcorte.jogodaspalavras.R;
import tatahcorte.jogodaspalavras.adapter.HistoricoPontuacaoAdapter;
import tatahcorte.jogodaspalavras.servico.PontuacaoServico;
import tatahcorte.jogodaspalavras.utils.ActivityUtils;

public class ActivityHistorico extends AppCompatActivity {

    private final PontuacaoServico servico = new PontuacaoServico();

    private RecyclerView recyclerView;
    private ImageButton limparHistorico;
    private TextView textViewPontosRecorde;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        recyclerView = findViewById(R.id.listHistorico);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        updateAdapter();

        limparHistorico = findViewById(R.id.btnLimparHistorico);
        limparHistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.mostrarDialogoConfirmacao(
                    ActivityHistorico.this
                    , R.string.cuidado
                    , R.string.apagar_dados_historico
                    , new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            servico.limparTabela();
                            updateAdapter();
                        }
                    }, null);
            }
        });

        textViewPontosRecorde = findViewById(R.id.textViewPontosRecorde);
        textViewPontosRecorde.setText(
                servico.getRecorde()
        );
    }

    private void updateAdapter(){
        recyclerView.setAdapter(
            new HistoricoPontuacaoAdapter(
                servico.getListaPontuacaoAgrupadaPorData()
            )
        );
    }
}
