package com.zzy.jackpot;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zerozhao on 2016/12/11.
 */
public class JackpotAdapter extends RecyclerView.Adapter<JackpotAdapter.AskRequestHolder> {
    private static List<JackpotProduct> listproducts;
    private List<JackpotProduct> products;
    private Context context;
    private LayoutInflater la;
    public final static int MAX_SIZE  = 6;


    public static List<JackpotProduct> getData()
    {
        if(listproducts==null) {
            listproducts = new ArrayList<JackpotProduct>();
            listproducts.add(new JackpotProduct(R.mipmap.gift,0));
            listproducts.add(new JackpotProduct(R.mipmap.offer_5,1));
            listproducts.add(new JackpotProduct(R.mipmap.offer_10, 2));
            listproducts.add(new JackpotProduct(R.mipmap.offer_20, 3));
            listproducts.add(new JackpotProduct(R.mipmap.offer_50,4));
            listproducts.add(new JackpotProduct(R.mipmap.beequeen, 5));
        }

        return listproducts;

    }

    public static int getProductCount()
    {
        getData();
        return listproducts.size();
    }


    public JackpotAdapter(Context context) {
        this.products = getData();
        this.context = context;
        this.la = LayoutInflater.from(context);
    }

    @Override
    public AskRequestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = la.inflate(R.layout.jackpot_item_scroll, parent,false);
        AskRequestHolder askRequestHolder = new AskRequestHolder(inflate);
        return askRequestHolder;
    }

    @Override
    public void onBindViewHolder(AskRequestHolder holder, int position) {

        position = position%products.size();
        JackpotProduct product = products.get(position);
        holder.ico.setImageResource(product.getiRes());

    }

    @Override
    public void onViewRecycled(AskRequestHolder holder) {
        super.onViewRecycled(holder);
    }


    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public class AskRequestHolder extends RecyclerView.ViewHolder{
        private ImageView ico;
        public AskRequestHolder(View itemView) {
            super(itemView);
            ico = (ImageView) itemView.findViewById(R.id.iv_ico);
        }
    }

}
