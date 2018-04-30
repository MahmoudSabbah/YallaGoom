package com.yallagoom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.yallagoom.R;
import com.yallagoom.adapter.ListAdapterCategory;
import com.yallagoom.adapter.ListAdapterCountry;
import com.yallagoom.api.GetCountriesAsyncTask;
import com.yallagoom.api.GetGategorysAsyncTask;
import com.yallagoom.entity.Category;
import com.yallagoom.entity.Country;
import com.yallagoom.interfaces.GetCategoryCallback;
import com.yallagoom.interfaces.GetCountriesCallback;
import com.yallagoom.utils.Constant;
import com.yallagoom.utils.ToolUtils;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/10/2018.
 */

public class SearchCountryActivity extends AppCompatActivity {
    private TextView ok;
    private TextView cancel;
    private EditText search_edit;
    private ListView country_list;
    private LinearLayout parent;
    private ArrayList<Country.Data> countryListData;
    private ListAdapterCountry listAdapterCountry;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_country);
        ToolUtils.hideStatus(SearchCountryActivity.this);

        ok = (TextView) findViewById(R.id.ok);
        cancel = (TextView) findViewById(R.id.cancel);
        search_edit = (EditText) findViewById(R.id.search_edit);
        country_list = (ListView) findViewById(R.id.country_list);
        parent = (LinearLayout) findViewById(R.id.parent);
        ToolUtils.setLightStatusBar(parent, SearchCountryActivity.this);


        search_edit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                ArrayList<Country.Data> listData = new ArrayList<>();

                for (int i = 0; i < countryListData.size(); i++) {
                    if (hexChecker(cs.toString().toUpperCase(), countryListData.get(i).getName_en().toUpperCase())) {
                        // list.add(list.get(i));
                        listData.add(countryListData.get(i));

                    }

                }
                listAdapterCountry.updateList(listData);
                country_list.setAdapter(listAdapterCountry);
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

        country_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView check = (TextView) view.findViewById(R.id.check);
                // TextView check2 = (TextView) ToolUtils.getViewByPosition(prePosition,category_list2).findViewById(R.id.check);
                TextView name = (TextView) view.findViewById(R.id.name);
                for (int j = 0; j < countryListData.size(); j++) {
                    if (countryListData.get(j).getName_en().equalsIgnoreCase(name.getText().toString())) {
                        countryListData.get(j).setVis(1);

                    } else {
                        countryListData.get(j).setVis(0);

                    }
                }
                listAdapterCountry.notifyDataSetChanged();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = -1;
                String country_name = null;
                String code_3 = null;
                String phone_code = null;
                for (int i = 0; i < countryListData.size(); i++) {
                    if (countryListData.get(i).getVis() == 1) {
                        id = countryListData.get(i).getId();
                        country_name = countryListData.get(i).getName_en();
                        code_3 = countryListData.get(i).getCode_3();
                        phone_code= countryListData.get(i).getPhone_code();

                    }
                }
                if (id != -1) {
                    Intent intent = new Intent();
                    intent.putExtra("country_id", id);
                    intent.putExtra("country_name", country_name);
                    intent.putExtra("code_3", code_3);
                    intent.putExtra("phone_code", phone_code);
                    setResult(102, intent);
                    finish();
                } else {
                    ToolUtils.showSnak(SearchCountryActivity.this, getString(R.string.select_cat));
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getCountry();
    }


    public boolean hexChecker(String c, String value) {
        String string = value;
        return string.indexOf(c) > -1;
    }

    private void getCountry() {
       /* GetCountriesAsyncTask getCountriesAsyncTask = new GetCountriesAsyncTask(SearchCountryActivity.this, new KProgressHUD(this), new GetCountriesCallback() {
            @Override
            public void processFinish(Country country) {*/

        countryListData = Constant.countriesData.getData();
        for (int j = 0; j < countryListData.size(); j++) {
            if (countryListData.get(j).getId() == getIntent().getExtras().getInt("id")) {
                countryListData.get(j).setVis(1);

            }
        }

        listAdapterCountry = new ListAdapterCountry(SearchCountryActivity.this, Constant.countriesData.getData());
        country_list.setAdapter(listAdapterCountry);

    }
     /*   });
        getCountriesAsyncTask.execute();*/


}