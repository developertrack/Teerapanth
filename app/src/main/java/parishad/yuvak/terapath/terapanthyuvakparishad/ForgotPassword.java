package parishad.yuvak.terapath.terapanthyuvakparishad;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.AppController;
import utils.UrlConstant;

public class ForgotPassword extends AppCompatActivity {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    TextInputLayout input_layout_resendotp,input_layout_cpassword,input_layout_password,input_layout_mobile_number;
    LinearLayout forgotpassword,resend_mobile_verify;
    EditText input_resendotp,input_password,input_cpassword,input_mobile_number;
    Button btn_send,btn_confirm;
    ProgressDialog pDialog;
    TextView counter;
    String TAG = "MessageVerificationActivity_TAG";
    JSONObject data_jobject,json_forgot_password,result_data;
    String otp,str_mobnumber,str_password,str_cpassword,Status;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String txt_otp = intent.getStringExtra("message");
                input_resendotp.setText(txt_otp);


            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        if (checkAndRequestPermissions()) {

        }

        forgotpassword=(LinearLayout)findViewById(R.id.forgotpassword);
        resend_mobile_verify=(LinearLayout)findViewById(R.id.resend_mobile_verify);
        forgotpassword.setVisibility(View.GONE);
        btn_send=(Button)findViewById(R.id.btn_send);
        btn_confirm=(Button)findViewById(R.id.btn_confirm);
        counter=(TextView)findViewById(R.id.counter);

        input_layout_mobile_number=(TextInputLayout)findViewById(R.id.input_layout_mobile_number);
        input_layout_mobile_number.setVisibility(View.GONE);
        input_mobile_number=(EditText)findViewById(R.id.input_mobile_number);

        input_layout_resendotp=(TextInputLayout)findViewById(R.id.input_layout_resendotp);
        input_layout_resendotp.setVisibility(View.GONE);
        input_resendotp=(EditText)findViewById(R.id.input_resendotp);

        input_layout_password=(TextInputLayout)findViewById(R.id.input_layout_password);

        input_layout_password.setVisibility(View.GONE);
        input_password=(EditText)findViewById(R.id.input_password);

        input_layout_cpassword=(TextInputLayout)findViewById(R.id.input_layout_cpassword);

        input_layout_cpassword.setVisibility(View.GONE);
        input_cpassword=(EditText)findViewById(R.id.input_cpassword);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isValidMobile()) {
                    return;
                }

                ResendOtp();

            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (!isValidOTP()) {
                    return;
                }
                if (!validatePassword()) {
                    return;
                }
                if (!validateConfirmPassword()) {
                    return;
                }

                str_password=input_password.getText().toString();
                str_cpassword=input_cpassword.getText().toString();
                otp=input_resendotp.getText().toString();

                ForgotPasswordReset();

            }
        });


    }

    public void ResendOtp(){

        String tag_json_obj = "json_obj_req";

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        data_jobject = new JSONObject();
        try {
            data_jobject.put("MobileNumber",input_mobile_number.getText().toString());


        } catch (Exception e) {

        }

        Log.e("Resend OTP",data_jobject.toString());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                UrlConstant.RESEND_OTP, data_jobject,
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
                                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(ForgotPassword.this);
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
//                                otp=response.getString("OTP");
//                                input_mobile_number.setText(otp);
                                resend_mobile_verify.setVisibility(View.GONE);
                                forgotpassword.setVisibility(View.VISIBLE);

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
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(ForgotPassword.this);
                        dlgAlert.setMessage("Error while logging in, please try again");
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

    private boolean isValidOTP() {

        otp = input_resendotp.getText().toString().trim();

        if (otp.isEmpty() || otp.length()<4) {
            input_layout_resendotp.setError("Not Valid OTP");
            requestFocus(input_layout_resendotp);
            return false;
        } else {
            input_layout_resendotp.setErrorEnabled(false);
        }
        return true;
    }

    private boolean isValidMobile() {

        str_mobnumber = input_mobile_number.getText().toString().trim();
        if (str_mobnumber.isEmpty() || str_mobnumber.length()<10 ) {
            input_layout_mobile_number.setError("Not Valid Number");
            requestFocus(input_layout_mobile_number);
            return false;
        } else {
            input_layout_mobile_number.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {

        if (input_password.getText().toString().trim().isEmpty() && input_password.getText().toString().length()<6 ) {
            input_layout_password.setError(getString(R.string.err_msg_password));
            requestFocus(input_layout_password);
            return false;
        } else {
            input_layout_password.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateConfirmPassword() {

        if (input_cpassword.getText().toString().trim().isEmpty() && input_cpassword.getText().toString().length()<6) {
            input_layout_cpassword.setError(getString(R.string.err_msg_password));
            requestFocus(input_layout_cpassword);
            return false;
        } else {
            input_layout_cpassword.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private  boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);

        int receiveSMS = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS);

        int readSMS = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (receiveSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_MMS);
        }
        if (readSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    public void ForgotPasswordReset( ) {
        final String response_string;

        String tag_json_obj = "json_obj_req";

        json_forgot_password = new JSONObject();
        try {
            json_forgot_password.put("MobileNumber", str_mobnumber);
            json_forgot_password.put("NewPassword", str_password);
            json_forgot_password.put("OTP", otp);
        } catch (Exception e) {

        }
        Log.e("json_forgot_password",json_forgot_password.toString());


        pDialog = new ProgressDialog(ForgotPassword.this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                UrlConstant.Forgot_Password_URL, json_forgot_password,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(final JSONObject response) {
                        Log.e(TAG, response.toString());
                        try {
                            String Status = response.getString("Status");

                            if (Status.equals("false")) {

                               runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(ForgotPassword.this);
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

                            } else {
                                try {
                                    result_data=new JSONObject(response.toString());
                                    Status=result_data.getString("Status");
                                    if(Status.equals("true")) {
                                        Intent dashboard = new Intent(ForgotPassword.this, LoginActivity.class);
                                        dashboard.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(dashboard);
                                        finish();
                                    }else{
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(ForgotPassword.this);
                                                try {
                                                    dlgAlert.setMessage(result_data.getString("Message"));
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                dlgAlert.setPositiveButton("OK", null);
                                                dlgAlert.setCancelable(true);
                                                dlgAlert.create().show();
                                            }
                                        });
                                    }

                                }catch(Exception e){

                                }

                            }

                        } catch (Exception e) {

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
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(ForgotPassword.this);
                        dlgAlert.setMessage("Error while logging in, please try again");
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

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {

                case R.id.input_mobile_number:
                    isValidMobile();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
                case R.id.input_cpassword:
                    validateConfirmPassword();
                    break;
                case R.id.input_otp:
                    isValidOTP();
                    break;

            }
        }
    }

}
