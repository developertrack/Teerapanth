package parishad.yuvak.terapath.terapanthyuvakparishad;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Calendar;

import ProfileFragment.Profile;
import advertisewithus.AdvertiseWithUs;
import events.EventListing;
import extypmembers.ExMembersDataListing;
import gallery.GalleryList;
import kishoremandal.KishoreMembersDataListing;
import membersdirectory.MembersDirectoryListing;
import news.NewsFragment;
import notification.NotiFicationFragmentNew;
import usefull.links.UsefullLinksFragment;
import utils.UserSessionManager;
import utils.WebpageLoad;
import workingcommitee.WorkingCommittee;

public class LoginUserDashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final Integer[] IMAGES = {R.drawable.banner_first, R.drawable.banner_second, R.drawable.banner_third, R.drawable.banner_fourth};
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    UserSessionManager session;
    Fragment fragment = null;
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        showAd();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        session=new UserSessionManager(LoginUserDashboard.this);
        fragment = new HomePage();
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.flContent, fragment, "home");
        tx.commit();

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

        if(minutes/4==0){
            splash.setImageResource(R.drawable.luka);
        }else{
            splash.setImageResource(R.drawable.oswal);
        }

        mSplashDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mSplashDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        mSplashDialog.setCancelable(true);

        mSplashDialog.show();
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
//
        if (id == R.id.nav_logout) {
            session.logoutUser();
        } else if (id == R.id.nav_news) {
            clearBackStack();
            fragment = new NewsFragment();
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.flContent, fragment, "news");
            tx.commit();
            tx.addToBackStack(null);
        }else if(id==R.id.nav_membersdirectory){
            clearBackStack();
            fragment = new MembersDirectoryListing();
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.flContent, fragment, "memberdir");
            tx.commit();
            tx.addToBackStack(null);
        }
        else if (id == R.id.nav_extypmember) {
            clearBackStack();
            fragment = new ExMembersDataListing();
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.flContent, fragment, "exmember");
            tx.commit();
            tx.addToBackStack(null);
        } else if (id == R.id.nav_kishoremandal) {
            clearBackStack();
            fragment = new KishoreMembersDataListing();
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.flContent, fragment, "kishoremember");
            tx.commit();
            tx.addToBackStack(null);
    } else if (id == R.id.nav_home) {
            clearBackStack();
            fragment = new HomePage();
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.flContent, fragment, "home");
            tx.commit();
            tx.addToBackStack(null);
    }else if (id == R.id.nav_usefulllink) {
            clearBackStack();
            fragment = new UsefullLinksFragment();
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.flContent, fragment, "usefull");
            tx.commit();
            tx.addToBackStack(null);
        }if (id == R.id.nav_myprofile) {
            clearBackStack();
            fragment = new Profile();
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.flContent, fragment, "profile");
            tx.commit();
            tx.addToBackStack(null);
        }  else if (id == R.id.nav_aboutus) {
            clearBackStack();
            Bundle bundle = new Bundle();
            bundle.putString("nav_aboutus","http://trkus.tarule.com/Home/AboutUs");
            fragment = new WebpageLoad();
            fragment.setArguments(bundle);
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.flContent, fragment, "abouturkus");
            tx.commit();
            tx.addToBackStack(null);

        } else if (id == R.id.nav_contact) {
            clearBackStack();
            Bundle bundle = new Bundle();
            bundle.putString("nav_aboutus", "http://typdelhi.org/Home/Contactus");
            fragment = new WebpageLoad();
            fragment.setArguments(bundle);
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.flContent, fragment, "aboutyp");
            tx.commit();
            tx.addToBackStack(null);

        } else if (id == R.id.nav_rateus) {
            rateApp();
        } else if (id == R.id.nav_events) {
            clearBackStack();
            fragment = new EventListing();
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.flContent, fragment, "news");
            tx.commit();
            tx.addToBackStack(null);
        }else if(id==R.id.nav_advertisement){
            clearBackStack();
            fragment = new AdvertiseWithUs();
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.flContent, fragment, "advertise");
            tx.commit();
            tx.addToBackStack(null);
        }else if (id == R.id.nav_share) {
            shareAppNew();
        }else if(id==R.id.nav_workingcomittee){
            clearBackStack();
            fragment = new WorkingCommittee();
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.flContent, fragment, "wcomittee");
            tx.commit();
            tx.addToBackStack(null);
        }else if(id==R.id.nav_notification){
            clearBackStack();
            fragment = new NotiFicationFragmentNew();
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.flContent, fragment, "NotiFNew");
            tx.commit();
            tx.addToBackStack(null);
        }else if (id == R.id.nav_gallery) {
            clearBackStack();
            fragment = new GalleryList();
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.flContent, fragment, "cGalleryList");
            tx.commit();
            tx.addToBackStack(null);
        }

//        AwardsAchieve
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void rateApp() {
        try {
            Uri marketUri = Uri.parse("market://details?id=" + getPackageName());
            Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
            startActivity(marketIntent);
        } catch (ActivityNotFoundException e) {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details?id=parishad.yuvak.terapath.terapanthyuvakparishad");
            startActivity(rateIntent);
        }
    }


    private Intent rateIntentForUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 16) {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        } else {
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }


    private void clearBackStack() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            //FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }


    public void shareAppNew() {
        String url = "https://play.google.com/store/apps/details?id=parishad.yuvak.terapath.terapanthyuvakparishad";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Use and like this app");

        stringBuilder.append(url);
        String shareMsg = stringBuilder.toString();

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMsg);
        shareIntent.setType("text/plain");
        startActivity(shareIntent);
    }


    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        } else {
            super.onBackPressed();
        }

    }


}
