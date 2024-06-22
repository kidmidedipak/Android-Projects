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
import com.dipak.snapshop.admin.activity.fragment.Categorys;
import com.dipak.snapshop.common.activity.model.Category;

import java.util.List;


public class CategoryAdapter extends BaseAdapter {

    private Context context;
    private List<Category> categoryList;
    CategoryService categoryService;

    public CategoryAdapter(Context context, CategoryService categoryService, List<Category> productList) {
        this.context = context;
        this.categoryService = categoryService;
        this.categoryList = productList;
    }
    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
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
            convertView = inflater.inflate(R.layout.category_item, viewGroup, false);
            holderView=new HolderView(convertView);
            convertView.setTag(holderView);
        }else {
            holderView=(HolderView) convertView.getTag();
        }

        int desireWidth=170;
        int desireHeight=170;

        // Get the current contact
        Category category = categoryList.get(position);


        // Convert color resource to actual color value
        int colorResourceId =  ContextCompat.getColor(context, R.color.white);;

        if(category.getStatus().equalsIgnoreCase("active"))
        {
             colorResourceId = ContextCompat.getColor(context, R.color.teal);;
        }else if(category.getStatus().equalsIgnoreCase("deactive"))
        {
             colorResourceId = ContextCompat.getColor(context, R.color.red);;
        }
        holderView.tv_status.setText(category.getStatus());

        ColorStateList colorStateList = ColorStateList.valueOf(colorResourceId);
        holderView.tv_status.setBackgroundTintList(colorStateList);

        holderView.pTitle.setText("Category : "+category.getCname());
        String desc="Description : "+category.getDescription();
        holderView.pDescription.setText(desc);

        holderView.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryService.onDelete(category.getId());
            }
        });

        holderView.iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryService.onEdit(category);
            }
        });

        //list animation
        convertView.setScaleX(0.8f);
        convertView.setScaleY(0.8f);
        convertView.animate().scaleX(1).scaleY(1).setDuration(500).start();

        return convertView;
    }

    private static class HolderView{
        private final TextView pTitle,pDescription,tv_status;
        ImageView iv_delete,iv_edit;

        public HolderView(View view)
        {
            iv_delete=view.findViewById(R.id.iv_delete);
            iv_edit=view.findViewById(R.id.iv_edit);
            pTitle=view.findViewById(R.id.pTitle);
            pDescription=view.findViewById(R.id.pDescription);
            tv_status=view.findViewById(R.id.tv_status);
        }
    }

    public interface CategoryService {
        public void onDelete(int id);

        public void onEdit(Category category);
    }


}

