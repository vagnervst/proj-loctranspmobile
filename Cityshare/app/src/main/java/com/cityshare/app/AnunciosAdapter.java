package com.cityshare.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cityshare.app.model.Anuncio;
import com.cityshare.app.model.Server;
import com.cityshare.app.model.Utils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by vagne_000 on 07/05/2017.
 */

public class AnunciosAdapter extends ArrayAdapter<Anuncio> {

    private int resource;
    public AnunciosAdapter(Context context, int resource, List<Anuncio> lista_anuncios) {
        super(context, resource, lista_anuncios);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if( v == null ) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(resource, null);
        }

        Anuncio anuncio = getItem(position);

        TextView quilometragem = (TextView) v.findViewById(R.id.txt_quilometragem);
        ImageView img_veiculo = (ImageView) v.findViewById(R.id.img_veiculo);
        TextView titulo_anuncio = (TextView) v.findViewById(R.id.txt_titulo_anuncio);
        TextView valor_diaria = (TextView) v.findViewById(R.id.txt_valor_diaria);
        TextView qtd_portas = (TextView) v.findViewById( R.id.txt_qtd_portas );
        TextView localizacao = (TextView) v.findViewById( R.id.txt_localizacao );

        quilometragem.setText( String.format(Locale.getDefault(), "%dKm rodados", anuncio.getQuilometragemAtual()) );

        String url = Server.servidor + "/img/uploads/publicacoes/" + anuncio.getImagemPrincipal();
        new GetImagem(url, img_veiculo).execute();

        titulo_anuncio.setText( anuncio.getTitulo() );
        valor_diaria.setText( String.format(Locale.getDefault(), "R$%.2f", anuncio.getValorDiaria()) );

        if( anuncio.getQtdPortas() > 0 ) {
            qtd_portas.setText(String.format(Locale.getDefault(), "Quantidade de Portas: %d", anuncio.getQtdPortas()));
        } else {
            qtd_portas.setVisibility( View.INVISIBLE );
        }

        String localizacao_anuncio = ( anuncio.getLocalizacaoAgencia() != null )? anuncio.getLocalizacaoAgencia() :  anuncio.getLocalizacaoUsuario();
        localizacao.setText( localizacao_anuncio );

        return v;
    }

    private class GetImagem extends AsyncTask<Void, Void, Void> {
        private String url;
        private ImageView imageView;
        private Bitmap image;

        private GetImagem(String url, ImageView target) {
            this.url = url;
            this.imageView = target;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                this.image = Picasso.with(getContext()).load( url ).get();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if( this.image != null ) {
                this.image = Utils.resize(this.image, this.imageView.getWidth(), this.imageView.getHeight());
                RoundedBitmapDrawable rounded = RoundedBitmapDrawableFactory.create(null, this.image);
                rounded.setCircular(true);

                this.imageView.setImageDrawable(rounded);
            }
        }
    }
}
