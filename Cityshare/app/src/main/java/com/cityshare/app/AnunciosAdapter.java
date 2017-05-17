package com.cityshare.app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cityshare.app.model.Anuncio;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by vagne_000 on 07/05/2017.
 */

public class AnunciosAdapter extends ArrayAdapter<Anuncio> {

    int resource;
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

        ImageView img_veiculo = (ImageView) v.findViewById(R.id.img_veiculo);
        String url = getContext().getString(R.string.serverAddr) + "/img/uploads/publicacoes/" + anuncio.getImagemPrincipal();
        Picasso.with(getContext()).load( url ).into( img_veiculo );

        TextView titulo_anuncio = (TextView) v.findViewById(R.id.txt_titulo_anuncio);

        titulo_anuncio.setText( anuncio.getTitulo() );

        return v;
    }
}
