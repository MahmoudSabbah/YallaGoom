package com.yallagoom.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yallagoom.R;
import com.yallagoom.adapter.ListAdapterCategory;
import com.yallagoom.api.GetGategorysAsyncTask;
import com.yallagoom.entity.Category;
import com.yallagoom.entity.Sport;
import com.yallagoom.interfaces.GetCategoryCallback;
import com.yallagoom.utils.ToolUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchCategoryActivity extends AppCompatActivity {

    private LinearLayout parent;
    private ListAdapterCategory recycleViewSearchCategory;
    private EditText search_edit;
    private ListView category_list2;
    private List<String> categorySearches;
    private TextView ok;
    private TextView cancel;
    private ArrayList<Category.CategoryList> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_category);
        ToolUtils.hideStatus(SearchCategoryActivity.this);

        categorySearches = new ArrayList<>();

        ok = (TextView) findViewById(R.id.ok);
        cancel = (TextView) findViewById(R.id.cancel);
        search_edit = (EditText) findViewById(R.id.search_edit);
        category_list2 = (ListView) findViewById(R.id.category_list);
        //category_list = (RecyclerView) findViewById(R.id.category_list);
        parent = (LinearLayout) findViewById(R.id.parent);
        ToolUtils.setLightStatusBar(parent, SearchCategoryActivity.this);
      /*  RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        category_list.setLayoutManager(mLayoutManager);
        category_list.setItemAnimator(new DefaultItemAnimator());*/

        search_edit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                ArrayList<Category.CategoryList> listData = new ArrayList<>();

                for (int i = 0; i < categoryList.size(); i++) {
                    if (hexChecker(cs.toString().toUpperCase(), categoryList.get(i).getCategoryName().toUpperCase())) {
                        // list.add(list.get(i));
                        listData.add(categoryList.get(i));

                    }

                }
                recycleViewSearchCategory.updateList(listData);
                category_list2.setAdapter(recycleViewSearchCategory);
                //    recycleViewSearchCategory.getFilter().filter(cs);

                //   recycleViewSearchCategory.notifyDataSetChanged();
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
        category_list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView check = (TextView) view.findViewById(R.id.check);
                // TextView check2 = (TextView) ToolUtils.getViewByPosition(prePosition,category_list2).findViewById(R.id.check);
                TextView name = (TextView) view.findViewById(R.id.name);
                for (int j = 0; j < categoryList.size(); j++) {
                    if (categoryList.get(j).getCategoryName().equalsIgnoreCase(name.getText().toString())) {
                        categoryList.get(j).setVis(1);

                    } else {
                        categoryList.get(j).setVis(0);

                    }
                }
                recycleViewSearchCategory.notifyDataSetChanged();
             /*   Log.e("sportList1",""+name.getText().toString());

                for (int j=0;j<categoryList.size();j++){
                    Log.e("sportList5",""+j);

                    Log.e("sportList2",""+categoryList.get(j).getName_en());
                    Log.e("sportList3",""+categoryList.get(j).getName_en().equalsIgnoreCase(name.getText().toString()));
                    Log.e("sportList4",""+categoryList.size());

                    if (categoryList.get(j).getName_en().equalsIgnoreCase(name.getText().toString())){
                        categoryList.get(j).setVis(1);
                        check.setVisibility(View.VISIBLE);
                    }else {
                        categoryList.get(j).setVis(0);
                        TextView check2 = (TextView) ToolUtils.getViewByPosition(j,category_list2).findViewById(R.id.check);
                        check2.setVisibility(View.GONE );

                    }
                }*/

                //  nameCheck=name;


                //       prePosition=Integer.parseInt(id_value.getText().toString());


             /*   if (check.getVisibility() == View.VISIBLE) {
                    check.setVisibility(View.GONE);
                    list.get(Integer.parseInt(id_value.getText().toString())).setVis(0);
                    categorySearches.remove(name.getText().toString());
                } else {
                    check.setVisibility(View.VISIBLE);
                    list.get(Integer.parseInt(id_value.getText().toString())).setVis(1);
                    categorySearches.add(name.getText().toString());


                }*/
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = -1;
                String cat_name = null;
                for (int i = 0; i < categoryList.size(); i++) {
                    if (categoryList.get(i).getVis() == 1) {
                        id = categoryList.get(i).getId();
                        cat_name = categoryList.get(i).getCategoryName();
                    }
                }
                if (id != -1) {
                    Intent intent = new Intent();
                    intent.putExtra("cat_id", id);
                    intent.putExtra("cat_name", cat_name);
                    setResult(102, intent);
                    finish();
                }else {
                    ToolUtils.showSnak(SearchCategoryActivity.this,getString(R.string.select_cat));
                }
             /*   String cat = "";
                Intent intent = new Intent();

                for (int i = 0; i < categorySearches.size(); i++) {

                    if (i == 0) {
                        cat = categorySearches.get(i);
                    } else {
                        cat = cat + " - " + categorySearches.get(i);

                    }

                }
                intent.putExtra("cat", cat);
                setResult(102, intent);
                finish();*/
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSports();
    }

   /* public static void clearLightStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            Window window = activity.getWindow();
            window.setStatusBarColor(ContextCompat
                    .getColor(activity, R.color.colorPrimaryDark));

        }
    }*/


    public boolean hexChecker(String c, String value) {
        String string = value;
        return string.indexOf(c) > -1;
    }

    private void getSports() {
        GetGategorysAsyncTask getCategoryFindEventAsyncTask = new GetGategorysAsyncTask(SearchCategoryActivity.this, new GetCategoryCallback() {
            @Override
            public void processFinish(Category  category) {
                categoryList = category.getData();
                recycleViewSearchCategory = new ListAdapterCategory(SearchCategoryActivity.this, category.getData());

                category_list2.setAdapter(recycleViewSearchCategory);
            }
        });
        getCategoryFindEventAsyncTask.execute();
    }


}
