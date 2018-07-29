package membersdirectory;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import news.NewsAdapter;
import news.NewsData;
import parishad.yuvak.terapath.terapanthyuvakparishad.R;
import parishad.yuvak.terapath.terapanthyuvakparishad.SignupActivity;
import utils.AppController;
import utils.UrlConstant;

public class MembersDirectoryListing extends Fragment {

    LinearLayout search, filter,layout_filter,layout_search;
    Fragment fragment = null;
    String[] search_list={"NAME","NUMBER","ADDRESS"};
    String[] filter_list={"BLOOD GROUP", "ZONE"};
    TextView txt_search,txt_filter;
    ArrayAdapter<String> spinner_search;
    ArrayAdapter<String> spinner_filter;
    ProgressDialog pDialog;
    ListView memberlist ;
    String coreurl_member="http://admin.typdelhi.org/api/User/GetMemberShipData?Name=&MobileNumber=&BloodGroup=&Zone=&NativePlace=&Address=";
    String TAG = "Usefull_Link_TAG";
    JSONArray result_data;
    String tag_json_obj = "json_obj_req";
    String[] Name,MobileNumber,EmailId,BloodGroup,Address,Zone;;
    JSONObject data;
    ArrayList<MembersData> members_data;
    MembersDataAdapter memberssadapter;
    EditText et_search,et_filter;
    ArrayList<MembersData> tempArrayList ;
    String search_type,filter_typ;
    String[] blood_group;
    String[] blood_group_id;
    StringRequest strReq,locationReq;
    JSONObject data_jobject;
    ArrayAdapter<String> spinner_blood_group;
    String str_bloodgroup,str_location;
    String[] location_name;
    String[] location_name_id;
    ArrayAdapter<String> location_name_group;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Member List");
        View view = inflater.inflate(R.layout.layout_membersdirectory, container, false);
        filter = (LinearLayout) view.findViewById(R.id.filter);
        search = (LinearLayout) view.findViewById(R.id.search);
        layout_filter = (LinearLayout) view.findViewById(R.id.layout_filter);
        layout_search = (LinearLayout) view.findViewById(R.id.layout_search);
        txt_search = (TextView) view.findViewById(R.id.txt_search);
        txt_filter = (TextView) view.findViewById(R.id.txt_filter);
        memberlist=(ListView)view.findViewById(R.id.memberlist);
        et_search=(view).findViewById(R.id.et_search);
        et_filter=(view).findViewById(R.id.et_filter);

        memberlist.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                memberlist.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        members_data = new ArrayList<MembersData>();
        spinner_search = new  ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item, search_list);
        spinner_filter = new  ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item, filter_list);

        memberListData();
        tempArrayList = new ArrayList<MembersData>();
        getLocation();
        getBloodGroup();
        search.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Select Search Criteria")
                        .setAdapter(spinner_search, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                txt_search.setText(search_list[which].toString());
                                search_type=search_list[which].toString();
                                layout_filter.setVisibility(View.GONE);
                                layout_search.setVisibility(View.VISIBLE);
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });

        et_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
                int textlength = cs.length();
                tempArrayList.clear();
                if (textlength > 0) {

                    if(search_type.equals("NAME")){
                        for (MembersData c : members_data) {
                            if (textlength <= c.getName().length()) {
                                if (c.getName().toLowerCase().contains(cs.toString().toLowerCase())) {
                                    Log.e("getName",c.getName().toLowerCase());
                                    tempArrayList.add(c);
                                }
                            }
                        }
                    }

                    if(search_type.equals("NUMBER")){
                        for (MembersData c : members_data) {
                            if (textlength <= c.getMobileNumber().length()) {
                                if (c.getMobileNumber().toLowerCase().contains(cs.toString().toLowerCase())) {
                                    Log.e("getName",c.getName().toLowerCase());
                                    tempArrayList.add(c);
                                }
                            }
                        }
                    }

                    if(search_type.equals("ADDRESS")){
                        for (MembersData c : members_data) {
                            if (textlength <= c.getAddress().length()) {
                                if (c.getAddress().toLowerCase().contains(cs.toString().toLowerCase())) {
                                    Log.e("getName",c.getName().toLowerCase());
                                    tempArrayList.add(c);
                                }
                            }
                        }
                    }

                    if(search_type.equals("NATIVE PlACE")){
                        for (MembersData c : members_data) {
                            if (textlength <= c.getAddress().length()) {
                                if (c.getAddress().toLowerCase().contains(cs.toString().toLowerCase())) {
                                    Log.e("getName",c.getName().toLowerCase());
                                    tempArrayList.add(c);
                                }
                            }
                        }
                    }


                    memberssadapter = new MembersDataAdapter(getActivity(), R.layout.seller_layout_listing_adapter, tempArrayList);
                    memberlist.setAdapter(memberssadapter);
                    memberssadapter.notifyDataSetChanged();
                } else {
                    memberssadapter=new MembersDataAdapter(getActivity(),R.layout.seller_layout_listing_adapter,members_data);
                    memberlist.setAdapter(memberssadapter);
                    memberssadapter.notifyDataSetChanged();
                    memberlist.invalidateViews();
                }
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Select filter Criteria ")
                        .setAdapter(spinner_filter, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                txt_filter.setText(filter_list[which].toString());
                                filter_typ=filter_list[which].toString();
                                layout_filter.setVisibility(View.VISIBLE);
                                layout_search.setVisibility(View.GONE);
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });

        et_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filter_typ.equals("BLOOD GROUP")){
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Select Blood Group")
                            .setAdapter(spinner_blood_group, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    et_filter.setText(blood_group[which].toString());
                                    str_bloodgroup=blood_group[which].toString();
                                    dialog.dismiss();
                                }
                            }).create().show();
                }else{
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Select Location")
                            .setAdapter(location_name_group, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    et_filter.setText(location_name[which].toString());
                                    str_location=filter_list[which].toString();
                                    dialog.dismiss();
                                }
                            }).create().show();
                }
            }
        });

        et_filter.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
                int textlength = cs.length();
                tempArrayList.clear();
                if (textlength > 0) {

                    if(filter_typ.equals("BLOOD GROUP")){
                        for (MembersData c : members_data) {
                            if (textlength <= c.getBloodGroup().length()) {
                                if (c.getBloodGroup().toLowerCase().contains(cs.toString().toLowerCase())) {
                                    Log.e("getName",c.getName().toLowerCase());
                                    tempArrayList.add(c);
                                }
                            }
                        }
                    }

                    if(filter_typ.equals("ZONE")){
                        for (MembersData c : members_data) {
                            if (textlength <= c.getZone().length()) {
                                if (c.getZone().toLowerCase().contains(cs.toString().toLowerCase())) {
                                    Log.e("getName",c.getName().toLowerCase());
                                    tempArrayList.add(c);
                                }
                            }
                        }
                    }

                    memberssadapter = new MembersDataAdapter(getActivity(), R.layout.seller_layout_listing_adapter, tempArrayList);
                    memberlist.setAdapter(memberssadapter);
                    memberssadapter.notifyDataSetChanged();
                } else {
                    memberssadapter=new MembersDataAdapter(getActivity(),R.layout.seller_layout_listing_adapter,members_data);
                    memberlist.setAdapter(memberssadapter);
                    memberssadapter.notifyDataSetChanged();
                    memberlist.invalidateViews();
                }
            }
        });



        return view;

    }

    public void getLocation(){
        locationReq  = new StringRequest(Request.Method.GET,
                UrlConstant.TYP_Location, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());

                try {
                    JSONArray data_array = new JSONArray(response.toString());
                    location_name_id=new String[data_array.length()];
                    location_name=new String[data_array.length()];

                    for(int i=0;i<data_array.length();i++){
                        data_jobject=data_array.getJSONObject(i);
                        location_name[i]=data_jobject.getString("LocationName");
                        location_name_id[i]=data_jobject.getString("Id");
                    }
                    location_name_group = new  ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item, location_name);


                }catch (Exception e){

                }

                pDialog.hide();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                pDialog.hide();
            }
        });

        AppController.getInstance().addToRequestQueue(locationReq, TAG);
    }

    public void getBloodGroup(){
        strReq  = new StringRequest(Request.Method.GET,
                UrlConstant.BLOOD_GROUP_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());

                try {
                    JSONArray data_array = new JSONArray(response.toString());
                    blood_group_id=new String[data_array.length()];
                    blood_group=new String[data_array.length()];

                    for(int i=0;i<data_array.length();i++){
                        data_jobject=data_array.getJSONObject(i);
                        blood_group[i]=data_jobject.getString("GroupName");
                        blood_group_id[i]=data_jobject.getString("GroupId");
                    }
                    spinner_blood_group = new  ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item, blood_group);


                }catch (Exception e){

                }

                pDialog.hide();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                pDialog.hide();
            }
        });

        AppController.getInstance().addToRequestQueue(strReq, TAG);
    }



    public void memberListData(){


        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                coreurl_member, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(final JSONArray response) {
                        Log.e(TAG, response.toString());
                        try {
                            result_data=new JSONArray(response.toString());

                            if(result_data.length()>0) {


                                Name=new String[result_data.length()];
                                MobileNumber=new String[result_data.length()];
                                EmailId=new String[result_data.length()];
                                BloodGroup=new String[result_data.length()];
                                Address=new String[result_data.length()];
                                Zone=new String[result_data.length()];

                                for(int i=0;i<result_data.length();i++){
                                    data=result_data.getJSONObject(i);
                                    Name[i]=data.getString("Name").trim();
                                    Log.e(TAG, Name[i]);

                                    MobileNumber[i]=data.getString("MobileNumber").trim();
                                    Log.e(TAG, MobileNumber[i]);

                                    EmailId[i]=data.getString("EmailId").trim();
                                    Log.e(TAG, EmailId[i]);

                                    BloodGroup[i]=data.getString("BloodGroup").trim();
                                    Log.e(TAG, BloodGroup[i]);

                                    Address[i]=data.getString("Address").trim();
                                    Log.e(TAG, Address[i]);

                                    Zone[i]=data.getString("Zone").trim();
                                    Log.e(TAG, Zone[i]);

                                    members_data.add(new MembersData(Name[i],MobileNumber[i],EmailId[i],BloodGroup[i],Address[i],Zone[i]));
                                }
                                memberssadapter=new MembersDataAdapter(getActivity(),R.layout.seller_layout_listing_adapter,members_data);
                                memberlist.setAdapter(memberssadapter);
                                memberssadapter.notifyDataSetChanged();
                                memberlist.invalidateViews();

                            }else{
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getActivity());
                                        dlgAlert.setMessage("No Data Available");
                                        dlgAlert.setPositiveButton("OK", null);
                                        dlgAlert.setCancelable(true);
                                        dlgAlert.create().show();
                                    }
                                });
                            }

                        }catch(Exception e){

                        }
                        pDialog.hide();
                    }


                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getActivity());
                        dlgAlert.setMessage("Error, please try again");
                        dlgAlert.setPositiveButton("OK", null);
                        dlgAlert.setCancelable(true);
                        dlgAlert.create().show();
                    }
                });
                pDialog.hide();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }


        };

        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);


    }

}