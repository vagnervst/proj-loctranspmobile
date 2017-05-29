package com.cityshare.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Created by vagne_000 on 28/05/2017.
 */

public class SlideAdapter extends PagerAdapter {
    private List<Bitmap> bitmapList;
    private Context context;

    public SlideAdapter(Context context, List<Bitmap> list ) {
        this.bitmapList = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return bitmapList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return ( view == (LinearLayout) object );
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View item = inflater.inflate(R.layout.swipe_layout, container, false);

        ImageView imagem = (ImageView) item.findViewById(R.id.iv_image);
        imagem.setImageBitmap( this.bitmapList.get(position) );

        container.addView(item);

        return item;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView( (LinearLayout) object);
    }
}
