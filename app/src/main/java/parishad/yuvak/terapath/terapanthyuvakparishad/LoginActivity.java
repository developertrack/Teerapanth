package parishad.yuvak.terapath.terapanthyuvakparishad;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import utils.AppController;
import utils.CallMethodRequest;
import utils.UrlConstant;
import utils.UserSessionManager;

public class LoginActivity extends AppCompatActivity {

    private EditText  inputEmail, inputPassword;
    private TextInputLayout  inputLayoutEmail, inputLayoutPassword;
    private Button btn_login;
    JSONObject data_jobject,result_data;
    String email,password;
    String TAG = "LoginActivity_TAG";
    String UserId,EmailId,Password,MobileNumber,FirstName,Status;
    UserSessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        btn_login = (Button) findViewById(R.id.btn_login);

        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });

        session=new UserSessionManager(LoginActivity.this);

        if(session.isUserLoggedIn()==true){
            email=session.getKeyEmail();
            password=session.getKeyPassword();

            userLogin();
        }
    }

    private void submitForm() {

        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

       userLogin();
    }

    private boolean validateEmail() {
        email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        password = inputPassword.getText().toString().trim();
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
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

                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
            }
        }
    }


    public void userLogin() {

        // Tag used to cancel the request
        String tag_json_obj = "json_obj_req";



        data_jobject = new JSONObject();
        try {
            data_jobject.put("EmailId", email);

            data_jobject.put("Password", password);
        } catch (Exception e) {

        }

        String result= new CallMethodRequest().POSTCallMethodRequest(LoginActivity.this,UrlConstant.LOGIN_URL,data_jobject.toString());

        Log.e("result",result);

        try {
            result_data=new JSONObject(result);
            Status=result_data.getString("Status");
            if(Status.equals("true")) {

                UserId = result_data.getString("UserId");
                EmailId = result_data.getString("EmailId");
                Password = result_data.getString("Password");
                MobileNumber = result_data.getString("MobileNumber");
                FirstName = result_data.getString("FirstName");

                session.createUserLoginSession(FirstName,EmailId,Password,UserId,MobileNumber);
            Intent dashboard = new Intent(LoginActivity.this, Dashboard.class);
            startActivity(dashboard);
            finish();
            }else{
               runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(LoginActivity.this);
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
//
        }catch(Exception e){

        }


        }


}
