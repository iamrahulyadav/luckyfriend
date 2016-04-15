package com.init.luckyfriend.activity.EditProfile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.init.luckyfriend.R;
import com.init.luckyfriend.activity.Singleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditProfile extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    ImageButton update;
    String tabname="basic info";
    ProgressDialog prog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
       // initToolbar();
        //retrievedata();


        update=(ImageButton)findViewById(R.id.update);

        initViewPagerAndTabs();
        prog=new ProgressDialog(this);
        prog.setMessage("wait uploading data...");
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Integer pos=tabLayout.getSelectedTabPosition();
               // tabname=tabLayout.getTabAt(pos).getText().toString();

            if(tabname.compareToIgnoreCase("basic info")==0){
                String firstname = BasicInfoFragment.firstname.getText().toString();
                String lastname = BasicInfoFragment.lastname.getText().toString();
                String genderst = BasicInfoFragment.genderstatus;
                String dob = BasicInfoFragment.dateofbirth.getText().toString();
                String country = BasicInfoFragment.country.toString();
                String city = BasicInfoFragment.cityname.getText().toString();
                String marstatus = BasicInfoFragment.relationstatus.toString();
                String sexstatus = BasicInfoFragment.sexstatus.toString();
                String lookingfor = BasicInfoFragment.lookingfor.toString();
                String aboutme = BasicInfoFragment.aboutme.getText().toString();
                String interests = BasicInfoFragment.interests.getText().toString();
                String bodytype = BasicInfoFragment.bodytype.toString();
                String height = BasicInfoFragment.height.getText().toString();
                String weight = BasicInfoFragment.weight.getText().toString();
                String eyes = BasicInfoFragment.eyes.getText().toString();
                String hair = BasicInfoFragment.hair.getText().toString();
                String ethnicity = BasicInfoFragment.ethnicity.getText().toString();
                String starsign = BasicInfoFragment.star_sign.toString();
//                String language=BasicInfoFragment.language.toString();
                ArrayList<String> languages=new ArrayList<>();
                String values="";
                for(Integer pos:BasicInfoFragment.multiSelectionSpinner.getSelectedIndices())
                {
                    values=values+BasicInfoFragment.arrays.get(pos)+",";
                }

                String query_basicinfo = "UPDATE `tbl_user_profiles` SET `person_gender`='" + genderst + "',`person_location`='" + city + "',`person_country`='" + country + "',`person_marital_status`='" + marstatus + "',`person_looking_for`='" + lookingfor + "',`person_height`='" + height + "',`person_about`='" + aboutme + "',`person_body_type`='" + bodytype + "',`person_weight`='" + weight + "',`person_eyes`='" + eyes + "',`person_hairs`='" + hair + "',`person_ethnicity`='" + ethnicity + "',`person_star_sign`='" + starsign + "',`person_dob`='"+dob+"',`person_languages`='" + values + "' WHERE person_id=" + Singleton.pref.getString("person_id", "") + "";
                String query2= "UPDATE `tbl_users` SET `user_first_name`='"+firstname+"',`user_last_name`='"+lastname+"',`user_name`='"+firstname+"' WHERE person_id=" + Singleton.pref.getString("person_id", "") + "";


                Log.e("basicinfo", query_basicinfo+"         "+query2);
                updatedata(query_basicinfo, query2);
            }
                else if(tabname.compareToIgnoreCase("lifestyle")==0){
                String party = PersonalityFragment.partylife.toString();
                String living = PersonalityFragment.livingsituation.toString();
                String kid = PersonalityFragment.kids.toString();
                String drink = PersonalityFragment.drinking.toString();
                String smoke = PersonalityFragment.smoking.toString();

                String query_lifestyle = "UPDATE `tbl_user_profiles` SET `person_party_life`='" + party + "',`person_living_situation`='" + living + "',`person_kids`='" + kid + "',`person_smoking`='" + smoke + "',`person_drinking`='" + drink + "' WHERE person_id=" + Singleton.pref.getString("person_id", "") + "";
                Log.e("lifestyle",query_lifestyle);

                updatedata(query_lifestyle,"");
            }
                else if(tabname.compareToIgnoreCase("professional life")==0){
                String education = ProfessionalFragment.edudetails.getText().toString();
                String occupation = ProfessionalFragment.occupation.getText().toString();
                String query = "UPDATE `tbl_user_profiles` SET `person_education`='" + education + "',`person_occupation`='" + occupation + "' WHERE person_id=" + Singleton.pref.getString("person_id", "")+"";

                Log.e("query",query);

                updatedata(query,"");
            }
            }
        });




    }

    private void updatedata(final String query1, final String query2) {

            //String url ="http://192.168.0.7/test.php";
        prog.show();
            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest sr = new StringRequest(Request.Method.POST,getResources().getString(R.string.url), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    prog.dismiss();
                    try {
                        Intent intent=new Intent();
                        setResult(2, intent);
                        finish();//finishing activity
                    } catch (Exception ex) {
                        Log.e("error", ex.getMessage());
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    prog.dismiss();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("rqid", 11+"");
                    params.put("query1", query1);
                    params.put("query2", query2);
                   // params.put("person_id", Singleton.pref.getString("person_id",""));

                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };
            queue.add(sr);

        }


    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle(getString(R.string.app_name));

        mToolbar.setTitleTextColor(Color.parseColor("#2f6fff"));
    }

    private void initViewPagerAndTabs() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(BasicInfoFragment.createInstance(20), getString(R.string.tab1));
        pagerAdapter.addFragment(PersonalityFragment.createInstance(4), "LIFESTYLE");
        pagerAdapter.addFragment(ProfessionalFragment.createInstance(4), "PROFESSIONAL LIFE");
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0);
        tabLayout.setOnTabSelectedListener(this);



    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
//        Toast.makeText(getApplicationContext(),tab.getText().toString(),Toast.LENGTH_LONG).show();
        tabname=tab.getText().toString();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    static class PagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }
}
