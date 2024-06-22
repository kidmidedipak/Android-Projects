package com.dipak.snapshop.admin.activity.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.dipak.snapshop.R;
import com.dipak.snapshop.common.activity.config.UrlConfig;
import com.dipak.snapshop.common.activity.model.Product;
import com.squareup.picasso.Picasso;
import java.util.List;

public class ProductAdapter extends BaseAdapter {

    private Context context;
    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }
    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        HolderView holderView;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.product_item, viewGroup, false);
            holderView=new HolderView(convertView);
            convertView.setTag(holderView);
        }else {
            holderView=(HolderView) convertView.getTag();
        }

        int desireWidth=150;
        int desireHeight=150;

        // Get the current contact
        Product product = productList.get(position);
        String imgUrl= UrlConfig.BASE_URL+"images/"+product.getImgname();

        Picasso.get().
                load(imgUrl)
                .resize(desireWidth,desireHeight)
                .centerCrop()
                .into(holderView.pImage);

        holderView.pTitle.setText(product.getName());
        holderView.price.setText("Price : "+product.getPrice() );
        holderView.quantity.setText("Quantity : "+product.getQuantity() );
        String desc=product.getDescription().trim();
        if(desc.length()>115)
        {
        holderView.pDescription.setText(desc.substring(0,115)+"...");
        }else{
        holderView.pDescription.setText(desc);
        }

        int color = ContextCompat.getColor(context, R.color.white);
        if(product.getStatus().trim().equalsIgnoreCase("active")){
             color = ContextCompat.getColor(context, R.color.teal); // Replace "your_color" with your color resource
         }else{
             color = ContextCompat.getColor(context, R.color.red); // Replace "your_color" with your color resource
        }

        ColorStateList colorStateList = ColorStateList.valueOf(color);
        holderView.tv_status.setBackgroundTintList(colorStateList);
        holderView.tv_status.setText(product.getStatus());

        //list animation
        convertView.setScaleX(0.8f);
        convertView.setScaleY(0.8f);
        convertView.animate().scaleX(1).scaleY(1).setDuration(500).start();

        return convertView;
    }

    private static class HolderView{
        private  ImageView pImage;
        private  TextView pTitle,pDescription,quantity,price,tv_status;

        public HolderView(View view)
        {
            pImage=view.findViewById(R.id.pImage);
            pTitle=view.findViewById(R.id.pTitle);
            pDescription=view.findViewById(R.id.pDescription);
            tv_status=view.findViewById(R.id.tv_status);
            quantity=view.findViewById(R.id.tv_quantity);
            price=view.findViewById(R.id.tv_price);
        }
    }
}
