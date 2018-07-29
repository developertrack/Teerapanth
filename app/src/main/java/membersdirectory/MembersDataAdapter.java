package membersdirectory;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.Locale;

import news.NewsData;
import parishad.yuvak.terapath.terapanthyuvakparishad.R;
import utils.AppController;

public class MembersDataAdapter  extends ArrayAdapter<MembersData> {

    Context context;
    int layoutResourceId;
    ArrayList<MembersData> dataget = null;
    ViewHolder holder;
    int pos = 0;
    MembersData getdata;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    class ViewHolder {
        TextView membername, member_address,member_contact,member_blood_group;
    }


    @Override
    public int getCount() {
        return dataget.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    public MembersDataAdapter(FragmentActivity activity, int resource, ArrayList<MembersData> save_news) {
        super(activity, resource, save_news);

        context=activity;
        layoutResourceId=resource;
        dataget=save_news;

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        holder = null;
        LayoutInflater inflater = LayoutInflater.from(context);

        if (convertView == null) {

            convertView = inflater.inflate(layoutResourceId, parent, false);

            holder = new ViewHolder();
            holder.membername = (TextView) convertView
                    .findViewById(R.id.membername);
            holder.member_address = (TextView) convertView
                    .findViewById(R.id.member_address);
            holder.member_contact = (TextView) convertView
                    .findViewById(R.id.member_contact);
            holder.member_blood_group = (TextView) convertView
                    .findViewById(R.id.member_blood_group);

            holder. member_blood_group .setTag(position);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        getdata = dataget.get(position);
        pos = position;

        holder.membername.setText(getdata.getName());
        holder.member_address.setText(getdata.getAddress()+" ,"+getdata.getZone());
        holder.member_contact.setText(getdata.getMobileNumber()+" , "+getdata.getEmailId());

        holder.member_blood_group.setText("Blood Group:- "+getdata.getBloodGroup());


        return convertView;

    }



}
