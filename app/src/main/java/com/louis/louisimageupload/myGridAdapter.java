package com.louis.louisimageupload;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by louis on 2016/7/10.
 */
public class myGridAdapter extends BaseAdapter{

    final  static int TYPE_NORMAL=0;
    final  static  int TYPE_PLUS=1;

    public myGridAdapter(List<ImageBean> imageBeanList) {
        this.imageBeanList = imageBeanList;
    }

    List<ImageBean> imageBeanList;
    @Override
    public int getCount() {
        return imageBeanList.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return imageBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
       // return super.getViewTypeCount();
        return 2;
    }


    @Override
    public int getItemViewType(int position) {
       // return super.getItemViewType(position);

        if (position==imageBeanList.size()+1-1){
            return TYPE_PLUS;
        }else
        {
            return  TYPE_NORMAL;
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
           switch (getItemViewType(position)){
               case TYPE_NORMAL:
                   convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid,parent,false);
                   break;
               case TYPE_PLUS:
                   convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_plus,parent,false);
                   break;

           }

           // convertView.findViewById(R.id.id=)

            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }


        return convertView;
    }

    class  ViewHolder{
        ImageView imageView;
    }
}
