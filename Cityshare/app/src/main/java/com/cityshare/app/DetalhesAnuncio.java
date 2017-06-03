package com.cityshare.app;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ViewFlipper;

import com.cityshare.app.model.Anuncio;
import com.cityshare.app.model.DatePickerFragment;
import com.cityshare.app.model.HttpRequest;
import com.cityshare.app.model.Server;
import com.cityshare.app.model.TimePickerFragment;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DetalhesAnuncio extends AppCompatActivity {

    private TextView titulo_anuncio, valor_diaria, valor_diaria_reserva, valor_combustivel, valor_quilometragem, valor_total, qtd_portas, tipo_combustivel, localizacao, limite_quilometragem, descricao_publicacao;
    private ViewPager imagens_anuncio;
    private FrameLayout content;
    private ViewFlipper flipper;
    private EditText dataRetirada, horaRetirada;
    private EditText dataEntrega, horaEntrega;

    Context context;
    Menu activity_menu;
    Calendar data_retirada, data_entrega;

    int id_anuncio;
    Anuncio anuncio;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_detalhes:
                    flipper.setDisplayedChild(0);
                    activity_menu.getItem(0).setVisible(false);
                    return true;
                case R.id.navigation_locador:
                    return true;
                case R.id.navigation_reserva:
                    flipper.setDisplayedChild(1);
                    activity_menu.getItem(0).setVisible(true);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_anuncio);

        context = this;

        imagens_anuncio = (ViewPager) findViewById(R.id.vp_imagens_anuncio);
        titulo_anuncio = (TextView) findViewById(R.id.txt_titulo_anuncio);
        valor_diaria = (TextView) findViewById(R.id.txt_valor_diaria);
        valor_diaria_reserva = (TextView) findViewById(R.id.txt_valor_diaria_reserva);
        valor_combustivel = (TextView) findViewById(R.id.txt_valor_combustivel);
        valor_quilometragem = (TextView) findViewById(R.id.txt_valor_quilometragem);
        valor_total = (TextView) findViewById(R.id.txt_valor_total);
        qtd_portas = (TextView) findViewById(R.id.txt_qtd_portas);
        tipo_combustivel = (TextView) findViewById(R.id.txt_tipo_combustivel);
        localizacao = (TextView) findViewById(R.id.txt_localizacao);
        limite_quilometragem = (TextView) findViewById(R.id.txt_limite_quilometragem);
        descricao_publicacao = (TextView) findViewById(R.id.txt_descricao_publicacao);

        data_retirada = Calendar.getInstance();
        data_entrega = Calendar.getInstance();

        content = (FrameLayout) findViewById( R.id.content );
        flipper = (ViewFlipper) findViewById( R.id.vf_flipper );

        dataRetirada = (EditText) findViewById( R.id.txt_data_retirada );
        horaRetirada = (EditText) findViewById( R.id.txt_hora_retirada );

        dataEntrega = (EditText) findViewById( R.id.txt_data_entrega );
        horaEntrega = (EditText) findViewById( R.id.txt_hora_entrega );

        dataRetirada.setKeyListener( null );
        dataEntrega.setKeyListener( null );

        horaRetirada.setKeyListener( null );
        horaEntrega.setKeyListener( null );

        dataRetirada.setOnFocusChangeListener( new AcaoDataLocacao("Data de Retirada", dataRetirada, AcaoDataLocacao.RETIRADA) );
        dataEntrega.setOnFocusChangeListener( new AcaoDataLocacao("Data de Entrega", dataEntrega, AcaoDataLocacao.ENTREGA) );

        horaRetirada.setOnFocusChangeListener( new AcaoHoraLocacao("Hora de Retirada", horaRetirada, AcaoHoraLocacao.RETIRADA) );
        horaEntrega.setOnFocusChangeListener( new AcaoHoraLocacao("Hora de Entrega", horaEntrega, AcaoHoraLocacao.ENTREGA) );

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        id_anuncio = getIntent().getIntExtra("idAnuncio", -1);

        if( id_anuncio == -1 ) {
            startActivity( new Intent(context, MainActivity.class) );
            return;
        }

        new GetInfoAnuncio().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detalhes_anuncio, menu);
        activity_menu = menu;
        activity_menu.getItem(0).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch( item.getItemId() ) {
            case R.id.action_confirmar_reserva:
                Intent confirmarInfoReservaWin = new Intent(context, ConfirmarInfoReserva.class);
                confirmarInfoReservaWin.putExtra("idAnuncio", id_anuncio);
                confirmarInfoReservaWin.putExtra("dataRetirada", data_retirada.getTimeInMillis());
                confirmarInfoReservaWin.putExtra("dataEntrega", data_entrega.getTimeInMillis());
                confirmarInfoReservaWin.putExtra("valorDiaria", anuncio.getValorDiaria());

                startActivity( confirmarInfoReservaWin );
        }

        return super.onOptionsItemSelected(item);
    }

    private class AcaoDataLocacao implements View.OnFocusChangeListener {
        public static final int RETIRADA = 0, ENTREGA = 1;
        private String tituloPicker;
        private EditText componenteAlvo;
        private int modo;

        public AcaoDataLocacao(String titulo, EditText componente, int modo) {
            this.tituloPicker = titulo;
            this.componenteAlvo = componente;
            this.modo = modo;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if( !hasFocus ) return;

            DatePickerFragment fragment = new DatePickerFragment(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    String data_formatada = "";
                    if( modo == RETIRADA ) {

                        data_retirada.set(year, month, dayOfMonth);

                        Date data = new Date();
                        data.setTime( data_retirada.getTimeInMillis() );

                        java.text.DateFormat f = android.text.format.DateFormat.getDateFormat(getApplicationContext());
                        data_formatada = new SimpleDateFormat().format( data ).split(" ")[0];

                    } else if( modo == ENTREGA ) {

                        data_entrega.set(year, month, dayOfMonth);

                        Date data = new Date();
                        data.setTime( data_entrega.getTimeInMillis() );

                        java.text.DateFormat f = android.text.format.DateFormat.getDateFormat(getApplicationContext());
                        data_formatada = new SimpleDateFormat().format( data ).split(" ")[0];

                    }

                    if( data_retirada.getTimeInMillis() > 0 && data_entrega.getTimeInMillis() > 0 ) {
                        long diarias = TimeUnit.MILLISECONDS.toDays(data_entrega.getTimeInMillis() - data_retirada.getTimeInMillis());

                        double total = diarias * anuncio.getValorDiaria();
                        valor_total.setText( String.format(Locale.getDefault(), "R$%.2f", total) );
                    }

                    componenteAlvo.setText( data_formatada );
                }
            });

            fragment.show( getFragmentManager(), tituloPicker );
        }
    }

    private class AcaoHoraLocacao implements View.OnFocusChangeListener {
        private static final int RETIRADA = 0, ENTREGA = 1;
        private String tituloPicker;
        private EditText componenteAlvo;
        private int modo;

        private AcaoHoraLocacao(String titulo, EditText componente, int modo) {
            this.tituloPicker = titulo;
            this.componenteAlvo = componente;
            this.modo = modo;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if( !hasFocus ) return;

            TimePickerFragment fragment = new TimePickerFragment(new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    String hora_formatada = "";
                    if( modo == RETIRADA ) {

                        data_retirada.set( data_retirada.get( Calendar.YEAR ), data_retirada.get( Calendar.MONTH ), data_retirada.get( Calendar.DAY_OF_MONTH ), hourOfDay, minute );

                        Date hora = new Date( data_retirada.getTimeInMillis() );

                        hora_formatada = new SimpleDateFormat().format(hora).split(" ")[1];

                    } else if( modo == ENTREGA ) {

                        data_entrega.set( data_entrega.get( Calendar.YEAR ), data_entrega.get( Calendar.MONTH ), data_entrega.get( Calendar.DAY_OF_MONTH ), hourOfDay, minute );

                        Date hora = new Date( data_entrega.getTimeInMillis() );
                        DateFormat f = DateFormat.getTimeInstance();

                        hora_formatada = new SimpleDateFormat().format(hora).split(" ")[1];

                    }

                    componenteAlvo.setText( hora_formatada );
                }
            });

            fragment.show(getFragmentManager(), tituloPicker);
        }
    }

    private class GetImagensAnuncio extends AsyncTask<Void, Void, Void> {
        private List<Bitmap> bitmaps = new ArrayList<>();

        @Override
        protected Void doInBackground(Void... params) {
            String url = Server.servidor + "img/uploads/publicacoes/";

            try {
                List<String> imagens_anuncio = Arrays.asList( anuncio.getImagemPrincipal(), anuncio.getImagemA(), anuncio.getImagemB(), anuncio.getImagemC(), anuncio.getImagemD() );

                for( int i = 0; i < imagens_anuncio.size(); ++i ) {
                    Bitmap imagem = Picasso.with(context).load( url + imagens_anuncio.get(i) ).get();

                    if( imagem != null ) {
                        bitmaps.add( imagem );
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.d("IMAGENS", String.valueOf(bitmaps.size()));
            SlideAdapter adapter = new SlideAdapter(context, this.bitmaps);

            imagens_anuncio.setAdapter( adapter );
        }
    }

    private class GetInfoAnuncio extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(context, "Carregando", "Aguarde...");
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = Server.servidor + "apis/android/get_info_publicacao.php";
            HashMap<String, String> parametros = new HashMap<>();
            parametros.put("idPublicacao", String.valueOf(id_anuncio));

            String json = HttpRequest.post(url, parametros);

            anuncio = new Gson().fromJson(json, Anuncio.class);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();

            titulo_anuncio.setText( anuncio.getTitulo() );
            valor_diaria.setText( String.format(Locale.getDefault(), "%.2f", anuncio.getValorDiaria()) );
            valor_diaria_reserva.setText( String.format(Locale.getDefault(), "%.2f", anuncio.getValorDiaria()) );
            valor_combustivel.setText( String.format(Locale.getDefault(), "%.2f", anuncio.getValorCombustivel()) );
            valor_quilometragem.setText( String.format(Locale.getDefault(), "%.2f", anuncio.getValorDiaria()) );
            qtd_portas.setText( String.format(Locale.getDefault(), "%d", anuncio.getQtdPortas()) );
            tipo_combustivel.setText( anuncio.getCombustivel() );
            localizacao.setText( anuncio.getCidade() + ", " + anuncio.getEstado() );
            limite_quilometragem.setText( String.format(Locale.getDefault(), "%d", anuncio.getLimiteQuilometragem()) );
            descricao_publicacao.setText( anuncio.getDescricao() );

            new GetImagensAnuncio().execute();
        }
    }
}