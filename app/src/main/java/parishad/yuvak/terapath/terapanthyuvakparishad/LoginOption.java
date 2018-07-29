package parishad.yuvak.terapath.terapanthyuvakparishad;

import android.app.Dialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Calendar;

public class LoginOption extends AppCompatActivity{

    Button btn_signup,btn_login;
    ImageView cross;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_login_option);

        btn_login=(Button)findViewById(R.id.btn_login);
        btn_signup=(Button)findViewById(R.id.btn_signup);
        cross=(ImageView)findViewById(R.id.cross);

        showAd();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login=new Intent(LoginOption.this,LoginActivity.class);
                startActivity(login);
            }
        });
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dashboard=new Intent(LoginOption.this,Dashboard.class);
                dashboard.putExtra("logintype","freeuser");
                dashboard.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(dashboard);
            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup=new Intent(LoginOption.this,SignupActivity.class);
                startActivity(signup);
            }
        });

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

}
