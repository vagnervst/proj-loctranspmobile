package com.cityshare.app;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cityshare.app.model.Pedido;
import com.cityshare.app.model.Server;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by vagne_000 on 09/05/2017.
 */

public class PedidosAdapter extends ArrayAdapter<Pedido> {

    private int resource;
    public PedidosAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Pedido> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if( convertView == null ) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(resource, null);
        }

        Pedido pedido = getItem(position);

        ImageView foto_pedido = (ImageView) v.findViewById(R.id.img_veiculo);

        String url = Server.servidor + "img/uploads/publicacoes/" + pedido.getImagemPrincipal();
        Log.d("PATH", url);
        Picasso.with(getContext()).load( url ).into(foto_pedido);

        TextView veiculo = (TextView) v.findViewById(R.id.txt_modelo_veiculo);
        TextView data_retirada = (TextView) v.findViewById(R.id.txt_data_retirada);
        TextView data_entrega = (TextView) v.findViewById(R.id.txt_data_entrega);
        TextView status = (TextView) v.findViewById(R.id.txt_status_pedido);

        veiculo.setText( pedido.getVeiculo() );

        Date dt_retirada = null;
        Date dt_entrega = null;
        data_retirada.setText( pedido.getDataRetirada() );
        data_entrega.setText( pedido.getDataEntrega() );

        try {
            dt_retirada = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse( pedido.getDataRetirada() );
            dt_entrega = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse( pedido.getDataEntrega() );

            data_retirada.setText( new SimpleDateFormat().format(dt_retirada) );
            data_entrega.setText( new SimpleDateFormat().format(dt_entrega) );

        } catch (ParseException e) {
            e.printStackTrace();
        }

        status.setText( pedido.getStatusPedido() );

        return v;
    }
}
