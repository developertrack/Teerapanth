package parishad.yuvak.terapath.terapanthyuvakparishad;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import utils.AppController;
import utils.UrlConstant;

public class SignupActivity extends AppCompatActivity {

    static EditText dob;
    String[] blood_group;
    String[] blood_group_id;
    String[] location_name;
    String[] location_name_id;
    ArrayAdapter<String> location_name_group;
    ArrayAdapter<String> spinner_blood_group;
    EditText first_name;
    EditText last_name;
    EditText email;
    EditText mobnumber;
    EditText bloodgroup;
    EditText location;
    EditText pincode;
    EditText confirmpassword;
    EditText password;
    int LocationId,BloodGroupId;
    Button btn_signup;
    String TAG = "SignupActivity_TAG";
    ProgressDialog pDialog;
    JSONObject data_jobject,data_post;
    StringRequest strReq,locationReq,postCreateUser;
    String str_bloodgroup,str_dob,str_location,str_firstname,str_lastname,str_email,str_mobnumber,str_pincode,str_password;

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        bloodgroup=(EditText)findViewById(R.id.bloodgroup);
        dob=(EditText)findViewById(R.id.dob);
        location=(EditText)findViewById(R.id.location);
        first_name=(EditText)findViewById(R.id.first_name);
        last_name=(EditText)findViewById(R.id.last_name);
        email=(EditText)findViewById(R.id.email);
        mobnumber=(EditText)findViewById(R.id.mobnumber);
        pincode=(EditText)findViewById(R.id.pincode);
        confirmpassword=(EditText)findViewById(R.id.confirmpassword);
        password=(EditText)findViewById(R.id.password);


        btn_signup=(Button)findViewById(R.id.btn_signup);

        pDialog= new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_bloodgroup=bloodgroup.getText().toString();
                str_dob=dob.getText().toString();
                str_location=location.getText().toString();
                str_firstname=first_name.getText().toString();
                str_lastname=last_name.getText().toString();
                str_email=email.getText().toString();
                str_mobnumber=mobnumber.getText().toString();
                str_pincode=pincode.getText().toString();
                str_password=password.getText().toString();


                if (!validateEmail()) {
                    return;
                }

                if (!validatePassword()) {
                    return;
                }
                if (!validateConfirmPassword()) {
                    return;
                }
                if (!validateFirstname()) {
                    return;
                }
                if (!isValidMobile()) {
                    return;
                }
                if (!isValidPincode()) {
                    return;
                }
                if (!isValidBloodGroup()) {
                    return;
                }
                if (!isValidDob()) {
                    return;
                }
                if (!isValidLocation()) {
                    return;
                }
                if (!validatePasswordMatch()) {
                    return;
                }

                createUser();


            }
        });

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
                            spinner_blood_group = new  ArrayAdapter<String>(SignupActivity.this,android.R.layout.simple_spinner_dropdown_item, blood_group);


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
                    location_name_group = new  ArrayAdapter<String>(SignupActivity.this,android.R.layout.simple_spinner_dropdown_item, location_name);


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

        bloodgroup.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                new AlertDialog.Builder(SignupActivity.this)
                        .setTitle("Select Blood Group")
                        .setAdapter(spinner_blood_group, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                bloodgroup.setText(blood_group[which].toString());
                                BloodGroupId=which;
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });

        location.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                new AlertDialog.Builder(SignupActivity.this)
                        .setTitle("Select Location")
                        .setAdapter(location_name_group, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                location.setText(location_name[which].toString());
                                LocationId=which;
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        showAd();

    }



    public void showAd(){
        Dialog mSplashDialog;
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);

        mSplashDialog = new Dialog(this);

        mSplashDialog.requestWindowFeature((int) Window.FEATURE_NO_TITLE);

        mSplashDialog.setContentView(R.layout.interstial_ads);

        ImageView splash=(ImageView)mSplashDialog.findViewById(R.id.splash);

        int[] myImageList = new int[]{R.drawable.luka,R.drawable.oswal};

        if(seconds%2==0){
            splash.setImageResource(R.drawable.luka);
        }else{
            splash.setImageResource(R.drawable.oswal);
        }

        mSplashDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mSplashDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        mSplashDialog.setCancelable(true);

        mSplashDialog.show();
    }

    private boolean validatePassword() {
        if (password.getText().toString().trim().isEmpty() && password.getText().toString().length()<6 ) {

            Toast.makeText(SignupActivity.this,getString(R.string.err_msg_password),Toast.LENGTH_LONG).show();
            return false;
        } else {

        }

        return true;
    }

    private boolean validateConfirmPassword() {
        if (confirmpassword.getText().toString().trim().isEmpty() && confirmpassword.getText().toString().length()<6) {

            Toast.makeText(SignupActivity.this,getString(R.string.err_msg_password),Toast.LENGTH_LONG).show();

            return false;
        } else {

        }

        if (!confirmpassword.getText().toString().trim().equals(password.getText().toString().trim())) {

            Toast.makeText(SignupActivity.this,getString(R.string.err_msg_password),Toast.LENGTH_LONG).show();

            return false;
        } else {

        }


        return true;
    }

    private boolean validatePasswordMatch() {


        if (!confirmpassword.getText().toString().trim().equals(password.getText().toString().trim())) {
           Toast.makeText(SignupActivity.this,"Password Mismatch",Toast.LENGTH_LONG).show();

            return false;
        } else {

        }


        return true;
    }

    private boolean validateEmail() {
        String email1 = email.getText().toString().trim();

        if (email1.isEmpty() || !isValidEmail(email1)) {
             Toast.makeText(SignupActivity.this,getString(R.string.err_msg_email),Toast.LENGTH_LONG).show();

            return false;
        } else {

        }

        return true;
    }

    private boolean validateFirstname() {
        str_firstname = first_name.getText().toString().trim();

        if (str_firstname.isEmpty()) {
           Toast.makeText(SignupActivity.this,"Please enter  name",Toast.LENGTH_LONG).show();

            return false;
        } else {

        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean isValidMobile() {

        str_mobnumber = mobnumber.getText().toString().trim();

        if (str_mobnumber.isEmpty() || str_mobnumber.length()<10 ) {

            Toast.makeText(SignupActivity.this,"Not Valid Number",Toast.LENGTH_LONG).show();

            return false;
        } else {

        }

        return true;
    }

    private boolean isValidPincode() {

        str_pincode = pincode.getText().toString().trim();

        if (str_pincode.isEmpty() || str_pincode.length()<6) {
            Toast.makeText(SignupActivity.this,"Not Valid Pincode",Toast.LENGTH_LONG).show();


            return false;
        } else {

        }
        return true;
    }

    private boolean isValidBloodGroup() {

        str_bloodgroup = bloodgroup.getText().toString().trim();

        if (str_bloodgroup.isEmpty() ) {
            Toast.makeText(SignupActivity.this,"Select Blood Group",Toast.LENGTH_LONG).show();

            return false;
        } else {

        }
        return true;
    }

    private boolean isValidLocation() {

        str_location = location.getText().toString().trim();

        if (str_location.isEmpty() ) {
            Toast.makeText(SignupActivity.this,"Select Location",Toast.LENGTH_LONG).show();

            return false;
        } else {

        }
        return true;
    }

    private boolean isValidDob() {

        str_dob = dob.getText().toString().trim();

        if (str_dob.isEmpty() ) {

            Toast.makeText(SignupActivity.this,"Set date of birth",Toast.LENGTH_LONG).show();

            return false;
        } else {

        }
        return true;
    }

    public void createUser(){

        // Tag used to cancel the request
        String tag_json_obj = "json_obj_req";

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        data_jobject = new JSONObject();
        try {
            data_jobject.put("FirstName",str_firstname);
            if(last_name.getText().toString().length()<1){
                data_jobject.put("LastName","NA");
            }else{
                data_jobject.put("LastName",str_lastname);
            }

            data_jobject.put("EmailId",str_email);
            data_jobject.put("MobileNumber",str_mobnumber);
            data_jobject.put("BloodGroupId",blood_group_id[BloodGroupId]);
            data_jobject.put("LocationId",location_name_id[LocationId]);
            data_jobject.put("NativePlace",str_location);
            data_jobject.put("DOB",str_dob);
            data_jobject.put("PinCode",str_pincode);
            data_jobject.put("Password",str_password);
        } catch (Exception e) {

        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                UrlConstant.CREATE_USER, data_jobject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(final JSONObject response) {
                        Log.e(TAG, response.toString());
                        try{
                            String Status=response.getString("Status");

                            if(Status.equals("false")){

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(SignupActivity.this);
                                        try {
                                            dlgAlert.setMessage(response.getString("Message"));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        dlgAlert.setPositiveButton("OK", null);
                                        dlgAlert.setCancelable(true);
                                        dlgAlert.create().show();
                                    }
                                });

                            }else{
                                Intent verification=new Intent(SignupActivity.this,MessageverificationActivity.class);
                                verification.putExtra("regiterdeddata",response.toString());
                                startActivity(verification);
                            }

                        }catch (Exception e){

                        }

                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(SignupActivity.this);
                        dlgAlert.setMessage("Error while signup in, please try again");
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

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);


    }

    @SuppressLint("ValidFragment")
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
            dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
            return dialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            dob.setText(year + "-" + (month + 1) + "-" + day);
        }
    }



}
