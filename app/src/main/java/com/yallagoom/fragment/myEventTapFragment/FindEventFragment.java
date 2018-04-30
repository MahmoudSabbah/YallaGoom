package com.yallagoom.fragment.myEventTapFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yallagoom.R;
import com.yallagoom.activity.CalenderActivity;
import com.yallagoom.activity.ChooseLocationActivity;
import com.yallagoom.activity.SearchCategoryActivity;
import com.yallagoom.api.SearchEventAsyncTask;
import com.yallagoom.utils.ToolUtils;
import com.yallagoom.widget.segmented.SegmentedGroup;


public class FindEventFragment extends Fragment {


    private TextView header_title;
    private TextView no_msg;
    private boolean auto = true;
    private RecyclerView alert_list;

    private int page = 1;
    private String lastSelect = "";
    private SegmentedGroup segmented2;
    private SegmentedGroup segmented1;
    private RadioButton week_ra;
    private RelativeLayout getLocation;
    private EditText location_EditText;
    private TextInputLayout location_float_label;
    public static EditText choose_dateEditText;
    private RadioButton today_ra;
    private RadioButton tomorrow_ra;
    private RadioButton month_ra;
    private EditText categoryEditText;
    private double lat = -1;
    private double lng = -1;
    public static String start_date;
    public static String end_date;
    private String selectDate;
    private RelativeLayout search_result;
    private RelativeLayout choose_cat_lay;
    private TextInputLayout search_float_label;
    private TextView title_remove;
    private TextView cat_remove;
    private TextView choose_date_remove;
    private TextView location_remove;
    private EditText titleEditText;
    private int cat_id;
    private String checkDateType;
    private ScrollView parent;

    public FindEventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_find_event, container, false);
        parent = (ScrollView) view.findViewById(R.id.parent);
        ToolUtils.hideKeyFromScreen(parent,FindEventFragment.this.getActivity());

        location_float_label = (TextInputLayout) view.findViewById(R.id.location_float_label);

        title_remove = (TextView) view.findViewById(R.id.title_remove);
        cat_remove = (TextView) view.findViewById(R.id.cat_remove);
        choose_date_remove = (TextView) view.findViewById(R.id.choose_date_remove);
        location_remove = (TextView) view.findViewById(R.id.location_remove);

        segmented1 = (SegmentedGroup) view.findViewById(R.id.segmented1);
        segmented2 = (SegmentedGroup) view.findViewById(R.id.segmented2);

        titleEditText = (EditText) view.findViewById(R.id.title);
        categoryEditText = (EditText) view.findViewById(R.id.search_sport);
        location_EditText = (EditText) view.findViewById(R.id.location_edit);
        choose_dateEditText = (EditText) view.findViewById(R.id.choose_date);

        getLocation = (RelativeLayout) view.findViewById(R.id.getLocation);
        search_result = (RelativeLayout) view.findViewById(R.id.search_result);
        search_float_label = (TextInputLayout) view.findViewById(R.id.search_sport_float_label);
        categoryEditText.setFocusable(false);
        choose_cat_lay = (RelativeLayout) view.findViewById(R.id.choose_cat_lay);
        location_EditText.setFocusable(false);
        choose_dateEditText.setFocusable(false);

        titleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (titleEditText.getText().toString().equalsIgnoreCase("")) {
                    title_remove.setVisibility(View.GONE);
                } else {
                    title_remove.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        categoryEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (categoryEditText.getText().toString().equalsIgnoreCase("")) {
                    cat_remove.setVisibility(View.GONE);
                } else {
                    cat_remove.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        choose_dateEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (choose_dateEditText.getText().toString().equalsIgnoreCase("")) {
                    choose_date_remove.setVisibility(View.GONE);
                } else {
                    choose_date_remove.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        location_EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (location_EditText.getText().toString().equalsIgnoreCase("")) {
                    location_remove.setVisibility(View.GONE);
                } else {
                    location_remove.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        title_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleEditText.setText("");
                title_remove.setVisibility(View.GONE);
            }
        });
        cat_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryEditText.setText("");
                cat_remove.setVisibility(View.GONE);
            }
        });
        choose_date_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choose_dateEditText.setText("");
                choose_date_remove.setVisibility(View.GONE);
            }
        });
        location_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location_EditText.setText("");
                location_remove.setVisibility(View.GONE);
                lat = -1;
                lng = -1;
            }
        });

        segmented2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int radioButtonID = segmented2.getCheckedRadioButtonId();
                View radioButton = segmented2.findViewById(radioButtonID);
                int idx = segmented2.indexOfChild(radioButton);
                if (idx == 3) {
                    choose_cat_lay.setEnabled(true);
                    search_float_label.setEnabled(true);
                    choose_cat_lay.setAlpha(1f);

                } else {
                    choose_cat_lay.setEnabled(false);
                    search_float_label.setEnabled(false);
                    choose_cat_lay.setAlpha(.2f);
                }
            }
        });


     /*   today_ra = (RadioButton) view.findViewById(R.id.today_ra);
        tomorrow_ra = (RadioButton) view.findViewById(R.id.tomorrow_ra);
        week_ra = (RadioButton) view.findViewById(R.id.week_ra);
        month_ra = (RadioButton) view.findViewById(R.id.month_ra);*/

        location_EditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FindEventFragment.this.getActivity(), ChooseLocationActivity.class);
                intent.putExtra("check", true);
                startActivityForResult(intent, 101);
            }
        });
        choose_dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FindEventFragment.this.getActivity(), CalenderActivity.class);
                startActivityForResult(intent, 103);
            }
        });
        categoryEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FindEventFragment.this.getActivity(), SearchCategoryActivity.class);
                startActivityForResult(intent, 104);
            }
        });
       /* segmented2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                // find the radio button by returned id
                RadioButton radioButton = (RadioButton) view.findViewById(selectedId);
                if (lastSelect.equalsIgnoreCase(radioButton.getText().toString())) {
                    radioGroup.clearCheck();
                    segmented2.clearCheck();
                    lastSelect = "";
                } else {
                    Toast.makeText(FindEventFragment.this.getActivity(),
                            radioButton.getText(), Toast.LENGTH_SHORT).show();
                    lastSelect = radioButton.getText().toString();
                }


            }
        });*/
        search_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = null;
                int catId = -1;
                int idSegmented1Sellect = -1;
                if (!titleEditText.getText().toString().equalsIgnoreCase("")) {
                    title = titleEditText.getText().toString();
                }
                if (!categoryEditText.getText().toString().equalsIgnoreCase("")) {
                    catId = cat_id;
                }
                int radioButtonIDSegmented1 = segmented1.getCheckedRadioButtonId();
                View radioButtonSegmented1 = segmented1.findViewById(radioButtonIDSegmented1);
                int idSegmented1 = segmented1.indexOfChild(radioButtonSegmented1);
                if (idSegmented1 == 2) {
                    idSegmented1Sellect = -1;
                } else {
                    idSegmented1Sellect = idSegmented1;
                }
                int radioButtonID = segmented2.getCheckedRadioButtonId();
                View radioButton = segmented2.findViewById(radioButtonID);
                int idx = segmented2.indexOfChild(radioButton);
                if (titleEditText.getText().toString().equalsIgnoreCase("") && categoryEditText.getText().toString().equalsIgnoreCase("")&&
                        idSegmented1Sellect==-1&&(idx==-1||idx==3)&&choose_dateEditText.getText().toString().equalsIgnoreCase("")&&
                        location_EditText.getText().toString().equalsIgnoreCase("")) {
                    ToolUtils.showSnak(FindEventFragment.this.getActivity(),"Please select at least one option");

                }else{
                    SearchEventAsyncTask searchEventAsyncTask = new SearchEventAsyncTask(FindEventFragment.this.getActivity(),
                            title, catId, idSegmented1Sellect, idx, checkDateType, lat, lng);
                    searchEventAsyncTask.execute();
                }



                Log.e("idx", "" + idx);
            }
        });
        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("onActivityResult", "onActivityResult" );

        switch (requestCode) {
            case 101:
                if (resultCode == 102) {
                    lat = data.getExtras().getDouble("lat");
                    lng = data.getExtras().getDouble("lng");
                    if (data.getExtras().getString("address").equalsIgnoreCase("")) {
                        location_EditText.setText(lat + " , " + lng);

                    } else {
                        location_EditText.setText(data.getExtras().getString("address"));

                    }

                }
                break;
            case 103:
                if (resultCode == 102) {
                    if (data.getExtras().getString("check") .equalsIgnoreCase("between")) {
                        choose_dateEditText.setText(data.getExtras().getString("date"));
                        start_date = data.getExtras().getString("start_date");
                        end_date = data.getExtras().getString("end_date");
                        checkDateType = data.getExtras().getString("check");
                    } else {
                        choose_dateEditText.setText(data.getExtras().getString("date"));
                        selectDate = data.getExtras().getString("selectDate");
                        checkDateType = data.getExtras().getString("check");
                    }
                }
                break;
            case 104:
                if (resultCode == 102) {
                    cat_id = data.getExtras().getInt("cat_id");
                    categoryEditText.setText(data.getExtras().getString("cat_name"));
                }
                break;
        }

    }
}
