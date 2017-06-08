package com.cityshare.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cityshare.app.model.HttpRequest;
import com.cityshare.app.model.Login;
import com.cityshare.app.model.Pedido;
import com.cityshare.app.model.Server;
import com.cityshare.app.model.Usuario;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SolicitacoesActivity extends AppCompatActivity {

    private ListView lv_solicitacoes;

    Context context;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_pendentes:
                    new GetSolicitacoesPendentes().execute();
                    return true;
                case R.id.navigation_em_andamento:
                    new GetSolicitacoesEmAndamento().execute();
                    return true;
                case R.id.navigation_concluidos:
                    new GetSolicitacoesConcluidas().execute();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitacoes);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        context = this;

        lv_solicitacoes = (ListView) findViewById(R.id.lv_solicitacoes);

        new GetSolicitacoesPendentes().execute();
    }

    private Pedido[] getSolicitacoes(HashMap<String, String> parametros) {
        String url = Server.servidor + "apis/android/get_solicitacoes.php";

        String json = HttpRequest.post(url, parametros);
        Log.d("JSONSOLICITACOES", json);
        return new Gson().fromJson(json, Pedido[].class);
    }

    private class GetSolicitacoesEmAndamento extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;
        Pedido[] solicitacoes;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(context, "Carregando", "Aguarde");
        }

        @Override
        protected Void doInBackground(Void... params) {
            final int ID_STATUS_PEDIDO_AGENDADO = 1, ID_STATUS_PEDIDO_CONCLUIDO = 9;

            HashMap<String, String> parametros = new HashMap<>();
            parametros.put("idUsuario", String.valueOf(Login.getId_usuario(context)));
            parametros.put("where", "p.idStatusPedido != " + ID_STATUS_PEDIDO_AGENDADO + " AND p.idStatusPedido != " + ID_STATUS_PEDIDO_CONCLUIDO);

            solicitacoes = getSolicitacoes(parametros);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();

            SolicitacoesAdapter adapter = new SolicitacoesAdapter(context, R.layout.list_view_item_solicitacao, Arrays.asList(solicitacoes));
            lv_solicitacoes.setAdapter(adapter);
        }
    }

    private class GetSolicitacoesPendentes extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;
        Pedido[] solicitacoes;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(context, "Carregando", "Aguarde");
        }

        @Override
        protected Void doInBackground(Void... params) {
            final int ID_STATUS_PEDIDO_AGENDADO = 1;

            HashMap<String, String> parametros = new HashMap<>();
            parametros.put("idUsuario", String.valueOf(Login.getId_usuario(context)));
            parametros.put("where", "p.idStatusPedido = " + ID_STATUS_PEDIDO_AGENDADO);

            solicitacoes = getSolicitacoes(parametros);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();

            SolicitacoesAdapter adapter = new SolicitacoesAdapter(context, R.layout.list_view_item_solicitacao, Arrays.asList(solicitacoes));
            lv_solicitacoes.setAdapter(adapter);
        }
    }

    private class GetSolicitacoesConcluidas extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;
        Pedido[] solicitacoes;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(context, "Carregando", "Aguarde");
        }

        @Override
        protected Void doInBackground(Void... params) {
            final int ID_STATUS_PEDIDO_CONCLUIDO = 9;

            HashMap<String, String> parametros = new HashMap<>();
            parametros.put("idUsuario", String.valueOf(Login.getId_usuario(context)));
            parametros.put("where", "p.idStatusPedido = " + ID_STATUS_PEDIDO_CONCLUIDO);

            solicitacoes = getSolicitacoes(parametros);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();

            SolicitacoesAdapter adapter = new SolicitacoesAdapter(context, R.layout.list_view_item_solicitacao, Arrays.asList(solicitacoes));
            lv_solicitacoes.setAdapter(adapter);
        }
    }

    private class GetImagemSolicitacao extends AsyncTask<Void, Void, Void> {
        private String url_imagem;
        private ImageView imageView;
        private Bitmap imagem;
        private boolean circle;

        private GetImagemSolicitacao(String url, ImageView iv_alvo, boolean circle) {
            this.url_imagem = url;
            this.imageView = iv_alvo;
            this.circle = circle;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Log.d("URL", url_imagem);
                imagem = Picasso.with(context).load(url_imagem).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if( imagem != null ) {
                RoundedBitmapDrawable round = RoundedBitmapDrawableFactory.create(null, imagem);
                round.setCircular( circle );

                imageView.setImageDrawable(round);
            }
        }
    }

    private class AlterarStatusSolicitacao extends AsyncTask<Void, Void, Void> {
        private static final String ACEITAR = "aceitar", RECUSAR = "recusar";
        int idPedido;
        private String modo;

        private AlterarStatusSolicitacao(int idPedido, String modo) {
            this.idPedido = idPedido;
            this.modo = modo;
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = Server.servidor + "apis/android/get_solicitacoes.php";
            HashMap<String, String> parametros = new HashMap<>();
            parametros.put("modo", modo);
            parametros.put("idSolicitacao", String.valueOf(idPedido));

            HttpRequest.post(url, parametros);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new GetSolicitacoesPendentes().execute();
        }
    }

    private class AcaoListaSolicitacoesPendentes implements View.OnClickListener {
        private Pedido pedido;

        private AcaoListaSolicitacoesPendentes(Pedido solicitacao) {
            this.pedido = solicitacao;
        }

        @Override
        public void onClick(View v) {

            new AsyncTask<Void, Void, Usuario>() {
                ProgressDialog progress;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    progress = ProgressDialog.show(context, "Carregando", "Aguarde");
                }

                @Override
                protected Usuario doInBackground(Void... params) {
                    String url = Server.servidor + "apis/android/get_info_usuario.php";

                    HashMap<String, String> parametros = new HashMap<String, String>();
                    parametros.put("idUsuario", String.valueOf(pedido.getIdUsuarioLocatario()));

                    String json = HttpRequest.post(url, parametros);

                    return new Gson().fromJson(json, Usuario.class);
                }

                @Override
                protected void onPostExecute(Usuario usuarioLocatario) {
                    super.onPostExecute(usuarioLocatario);
                    progress.dismiss();

                    AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("Detalhes do Condutor");
                    View dialog_detalhes_solicitacao = LayoutInflater.from(context).inflate(R.layout.dialog_detalhes_solicitacao, null);

                    ImageView iv_foto_usuario = (ImageView) dialog_detalhes_solicitacao.findViewById(R.id.iv_foto_usuario);
                    TextView txt_nome_usuario = (TextView) dialog_detalhes_solicitacao.findViewById(R.id.txt_nome_usuario);
                    TextView txt_localizacao_usuario = (TextView) dialog_detalhes_solicitacao.findViewById(R.id.txt_localizacao_usuario);
                    TextView txt_numero_locacoes = (TextView) dialog_detalhes_solicitacao.findViewById(R.id.txt_numero_locacoes);
                    TextView txt_avaliacao_usuario = (TextView) dialog_detalhes_solicitacao.findViewById(R.id.txt_avaliacao);

                    ImageButton btn_recusar = (ImageButton) dialog_detalhes_solicitacao.findViewById(R.id.ib_recusar);
                    ImageButton btn_aceitar = (ImageButton) dialog_detalhes_solicitacao.findViewById(R.id.ib_aceitar);

                    txt_nome_usuario.setText( String.format(Locale.getDefault(), "%s %s", pedido.getNomeLocatario(), pedido.getSobrenomeLocatario().charAt(0)) );

                    String url = Server.servidor + "img/uploads/usuarios/" + pedido.getImagemPerfilLocatario();
                    new GetImagemSolicitacao(url, iv_foto_usuario, true).execute();

                    txt_localizacao_usuario.setText( String.format(Locale.getDefault(), "%s, %s", pedido.getEstadoLocatario(), pedido.getCidadeLocatario()) );

                    txt_numero_locacoes.setText( String.valueOf(usuarioLocatario.getQtdLocacoes()) );
                    txt_avaliacao_usuario.setText( String.format( Locale.getDefault(), "%d/5", Math.round(usuarioLocatario.getMediaNotas()) ) );

                    builder.setView( dialog_detalhes_solicitacao );

                    final AlertDialog dialog = builder.create();

                    btn_recusar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new AlterarStatusSolicitacao(pedido.getId(), AlterarStatusSolicitacao.RECUSAR).execute();
                            dialog.dismiss();
                        }
                    });

                    btn_aceitar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new AlterarStatusSolicitacao(pedido.getId(), AlterarStatusSolicitacao.ACEITAR).execute();
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }

            }.execute();
        }
    }

    private class SolicitacoesAdapter extends ArrayAdapter<Pedido> {

        private int resource;
        public SolicitacoesAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Pedido> objects) {
            super(context, resource, objects);
            this.resource = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                v = inflater.inflate(resource, null);
            }

            LinearLayout layout_info_solicitacao = (LinearLayout) v.findViewById(R.id.layout_info_solicitacao);
            ImageView foto_veiculo = (ImageView) v.findViewById(R.id.iv_foto_veiculo);
            TextView txt_valor_total = (TextView) v.findViewById(R.id.txt_valor_total);
            TextView txt_data_retirada = (TextView) v.findViewById(R.id.txt_data_retirada);
            TextView txt_data_entrega = (TextView) v.findViewById(R.id.txt_data_entrega);
            ImageButton botao_recusar = (ImageButton) v.findViewById(R.id.ib_recusar);

            final Pedido pedido = getItem(position);

            if( pedido.getImagemPrincipal() != null ) {
                String url_imagem = Server.servidor + "img/uploads/publicacoes/" + pedido.getImagemPrincipal();
                new GetImagemSolicitacao(url_imagem, foto_veiculo, false).execute();
            }

            txt_valor_total.setText( String.format(Locale.getDefault(), "R$%.2f", pedido.getValorTotal()) );

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            try {
                Date data_retirada = formatter.parse( pedido.getDataRetirada() );
                Date data_entrega = formatter.parse( pedido.getDataEntrega() );

                txt_data_retirada.setText( SimpleDateFormat.getDateInstance().format(data_retirada) );
                txt_data_entrega.setText( SimpleDateFormat.getDateInstance().format(data_entrega) );

            } catch (ParseException e) {
                e.printStackTrace();
            }

            if( !pedido.getStatusPedido().equals("Agendado") ) {
                botao_recusar.setVisibility( View.GONE );

                layout_info_solicitacao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent detalhesPedidoWin = new Intent(context, DetalhesPedidoActivity.class);
                        detalhesPedidoWin.putExtra("idPedido", pedido.getId());
                        startActivity(detalhesPedidoWin);
                    }
                });

            } else {

                layout_info_solicitacao.setOnClickListener( new AcaoListaSolicitacoesPendentes( pedido ) );

                botao_recusar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlterarStatusSolicitacao(pedido.getId(), AlterarStatusSolicitacao.RECUSAR).execute();
                    }
                });
            }

            return v;
        }
    }
}
