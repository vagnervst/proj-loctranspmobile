package com.cityshare.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.LayoutRes;
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
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Locale;

/**
 * Created by vagne_000 on 07/06/2017.
 */

public class MeuAnuncioAdapter extends ArrayAdapter<Anuncio> {

    private int resource;
    public MeuAnuncioAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull Anuncio[] objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if( v == null ) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(this.resource, null);
        }

        final Anuncio anuncio = getItem(position);

        final ImageView imagem_anuncio = (ImageView) v.findViewById(R.id.iv_imagem_anuncio);
        TextView modelo_veiculo = (TextView) v.findViewById(R.id.txt_modelo_veiculo);
        TextView titulo_anuncio = (TextView) v.findViewById(R.id.txt_titulo_anuncio);
        TextView status_anuncio = (TextView) v.findViewById(R.id.txt_status_anuncio);
        TextView valor_diaria = (TextView) v.findViewById(R.id.txt_valor_diaria);

        new AsyncTask<Void, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap imagem = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.ic_car_black);
                try {

                    if (anuncio.getImagemPrincipal() != null) {
                        String urlImagem = Server.servidor + "img/uploads/publicacoes/" + anuncio.getImagemPrincipal();
                        imagem = Picasso.with(getContext()).load(urlImagem).get();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return imagem;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);

                RoundedBitmapDrawable round = RoundedBitmapDrawableFactory.create(null, bitmap);
                round.setCircular(true);

                imagem_anuncio.setImageDrawable( round );
            }

        }.execute();


        titulo_anuncio.setText( anuncio.getTitulo() );
        modelo_veiculo.setText( anuncio.getModeloVeiculo() );
        valor_diaria.setText( String.format(Locale.getDefault(), "R$%.2f", anuncio.getValorDiaria()) );

        if( anuncio.getIdStatusPublicacao() == Anuncio.INDISPONIVEL ) {
            status_anuncio.setText(anuncio.getStatusPedido());
            status_anuncio.setVisibility(View.VISIBLE);
        }

        return v;
    }
}
