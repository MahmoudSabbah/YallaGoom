package com.yallagoom.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yallagoom.R;
import com.yallagoom.adapter.ListAdapterSport;
import com.yallagoom.api.GetSportsListAsyncTask;
import com.yallagoom.entity.Sport;
import com.yallagoom.interfaces.GetSportCallback;
import com.yallagoom.utils.ToolUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchSportsActivity extends AppCompatActivity {
    private LinearLayout parent;
    private ListAdapterSport listAdapterSport;
    private EditText search_edit;
    private ListView sports_list;
    private List<String> categorySearches;
    private TextView ok;
    private TextView cancel;
    private ArrayList<Sport.Data> sportList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_sports);
        ToolUtils.hideStatus(SearchSportsActivity.this);

        categorySearches = new ArrayList<>();

        ok = (TextView) findViewById(R.id.ok);
        cancel = (TextView) findViewById(R.id.cancel);
        search_edit = (EditText) findViewById(R.id.search_edit);
        sports_list = (ListView) findViewById(R.id.sports_list);
        //category_list = (RecyclerView) findViewById(R.id.category_list);
        parent = (LinearLayout) findViewById(R.id.parent);
        ToolUtils.setLightStatusBar(parent, SearchSportsActivity.this);
      /*  RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        category_list.setLayoutManager(mLayoutManager);
        category_list.setItemAnimator(new DefaultItemAnimator());*/

        search_edit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                ArrayList<Sport.Data> listData = new ArrayList<>();

                for (int i = 0; i < sportList.size(); i++) {
                    if (hexChecker(cs.toString().toUpperCase(), sportList.get(i).getName_en().toUpperCase())) {
                        // list.add(list.get(i));
                        listData.add(sportList.get(i));

                    }

                }
                listAdapterSport.updateList(listData);
                sports_list.setAdapter(listAdapterSport);
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
        sports_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView check = (TextView) view.findViewById(R.id.check);
                // TextView check2 = (TextView) ToolUtils.getViewByPosition(prePosition,sports_list).findViewById(R.id.check);
                TextView name = (TextView) view.findViewById(R.id.name);
                for (int j = 0; j < sportList.size(); j++) {
                    if (sportList.get(j).getName_en().equalsIgnoreCase(name.getText().toString())) {
                        sportList.get(j).setVis(1);

                    } else {
                        sportList.get(j).setVis(0);

                    }
                }
                listAdapterSport.notifyDataSetChanged();

            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = -1;
                String cat_name = null;
                for (int i = 0; i < sportList.size(); i++) {
                    if (sportList.get(i).getVis() == 1) {
                        id = sportList.get(i).getSport_id();
                        cat_name = sportList.get(i).getName_en();
                    }
                }
                if (id != -1) {
                    Intent intent = new Intent();
                    intent.putExtra("sport", id);
                    intent.putExtra("sport_name", cat_name);
                    setResult(102, intent);
                    finish();
                }else {
                    ToolUtils.showSnak(SearchSportsActivity.this,getString(R.string.select_cat));
                }

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

    public void Cancel(View view) {
        finish();
    }
    public boolean hexChecker(String c, String value) {
        String string = value;
        return string.indexOf(c) > -1;
    }

    private void getSports() {
        GetSportsListAsyncTask getSportsAsyncTask = new GetSportsListAsyncTask(SearchSportsActivity.this, new GetSportCallback() {
            @Override
            public void processFinish(Sport sport) {
                sportList = sport.getData();
                listAdapterSport = new ListAdapterSport(SearchSportsActivity.this, sport.getData());
                sports_list.setAdapter(listAdapterSport);
            }
        });
        getSportsAsyncTask.execute();
    }

}
