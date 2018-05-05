package com.oxygen.yallagoom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.entity.Country;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahmoud Sabbah on 2/7/2018.
 */
public class ListAdapterCountry extends BaseAdapter {
    private final ArrayList<Country.Data> arraylist;
    private final ArrayList<Country.Data> mStringFilterList;
   // private final int id;
    List<Country.Data> arrayList;
    private ArrayList<Country.Data> categorySearchesList;    // Values to be displayed
    LayoutInflater inflater;
    private List<Country.Data> arrayListNames;

    public ListAdapterCountry(Context context, ArrayList<Country.Data> categorySearchesList) {
        this.categorySearchesList = categorySearchesList;
        mStringFilterList = categorySearchesList;
       // this.id = id;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<Country.Data>();
        this.arraylist.addAll(categorySearchesList);
    }

    public void updateList(ArrayList<Country.Data> mDisplayedValues) {
        this.categorySearchesList = mDisplayedValues;
       /* for (int i = 0; i < mProductArrayList.size(); i++) {
            Log.e("mDisplayedValues", "" + mDisplayedValues.get(i).getName() + " " + mDisplayedValues.get(i).getVis());

        }*/
        //   notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return categorySearchesList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView name;
        TextView check;
        TextView id_value;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item_category, null);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.check = (TextView) convertView.findViewById(R.id.check);
            holder.id_value = (TextView) convertView.findViewById(R.id.id_value);
            holder.name.setText(categorySearchesList.get(position).getName_en());
            holder.id_value.setText(categorySearchesList.get(position).getId() + "");
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (categorySearchesList.get(position).getVis() == 0) {
            holder.check.setVisibility(View.GONE);

        } else {
            holder.check.setVisibility(View.VISIBLE);

        }
       /* if (id != -1) {
           if (categorySearchesList.get(position).getId()==id){
               categorySearchesList.get(position).setVis(1);
               holder.check.setVisibility(View.VISIBLE);
           }
        }*/
        return convertView;
    }

}