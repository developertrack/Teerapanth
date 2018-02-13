package parishad.yuvak.terapath.terapanthyuvakparishad;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class SignupActivity extends AppCompatActivity {

    String[] blood_group={"O +","O -","A +","A -","B +","B -","AB +","AB -"};
    ArrayAdapter<String> spinner_countries;
    TextInputLayout input_fname,input_lname,input_email,input_mobnumber,input_bloodgroup,input_dob,input_location,input_pincode,input_confirmpassword,input_password;
    EditText first_name;
    EditText last_name;
    EditText email;
    EditText mobnumber;
    EditText bloodgroup;
    static EditText dob;
    EditText location;
    EditText pincode;
    EditText confirmpassword;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        bloodgroup=(EditText)findViewById(R.id.bloodgroup);
        input_dob=(TextInputLayout)findViewById(R.id.input_dob);
        dob=(EditText)findViewById(R.id.dob);

        spinner_countries = new  ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, blood_group);

        bloodgroup.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                new AlertDialog.Builder(SignupActivity.this)
                        .setTitle("Select Blood Group")
                        .setAdapter(spinner_countries, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                bloodgroup.setText(blood_group[which].toString());
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
            return  dialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
//            dob.setText(ConverterDate.ConvertDate(year, month + 1, day));
           dob.setText(year + " / " + (month + 1) + " / "
                    + day);
        }
    }
}
