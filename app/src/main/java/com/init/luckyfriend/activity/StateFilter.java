package com.init.luckyfriend.activity;

import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.init.luckyfriend.R;

import java.util.ArrayList;

public class StateFilter extends AppCompatActivity implements  AdapterView.OnItemClickListener {

    String[] indiaState = {"Haryana","Uttarakand","Chandigarh","M.P","Punjab"};
    String[] australiaState = {"Melbourne","Sydney"};
    String[] array1={"Ontario","Alberta","Quebec","Manitoba"};
    String[] array2={"Cambridge","Edinburgh"};
    String[] array3={"California","Hawai", "Florida","Texas","Alaska","Georgia","North Carolina"};



    ImageView check;
    ListView listview;
    MySimpleArrayAdapter adapter;

    ImageButton back;
    String val;
    int arraylength;
     ArrayList<StateBean> list=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_filter);

        back = (ImageButton) findViewById(R.id.back);
        listview = (ListView) findViewById(R.id.list);

        String country = getIntent().getStringExtra("countryselect");


        if (country.equalsIgnoreCase("india")) {
            for (int i = 0; i < indiaState.length; ++i) {
                StateBean sb = new StateBean();
                sb.setStates(indiaState[i]);
                sb.setVisibility(false);
                list.add(sb);
            }
            adapter=new MySimpleArrayAdapter(getApplicationContext(),list);
            listview.setAdapter(adapter);
        }

   else if (country.equalsIgnoreCase("australia")) {
            for (int i = 0; i < australiaState.length; ++i) {
                StateBean sb = new StateBean();
                sb.setStates(australiaState[i]);
                sb.setVisibility(false);
                list.add(sb);
            }
            adapter=new MySimpleArrayAdapter(getApplicationContext(),list);
            listview.setAdapter(adapter);
        }

        else if (country.equalsIgnoreCase("canada")) {
            for (int i = 0; i < array1.length; ++i) {
                StateBean sb = new StateBean();
                sb.setStates(array1[i]);
                sb.setVisibility(false);
                list.add(sb);
            }
            adapter=new MySimpleArrayAdapter(getApplicationContext(),list);
            listview.setAdapter(adapter);
        }

        else if (country.equalsIgnoreCase("u.s.a")) {
            for (int i = 0; i < array3.length; ++i) {
                StateBean sb = new StateBean();
                sb.setStates(array3[i]);
                sb.setVisibility(false);
                list.add(sb);
            }
            adapter=new MySimpleArrayAdapter(getApplicationContext(),list);
            listview.setAdapter(adapter);
        }
        else if (country.equalsIgnoreCase("u.k")) {
            for (int i = 0; i < array2.length; ++i) {
                StateBean sb = new StateBean();
                sb.setStates(array2[i]);
                sb.setVisibility(false);
                list.add(sb);
            }
            adapter=new MySimpleArrayAdapter(getApplicationContext(),list);
            listview.setAdapter(adapter);
        }





        listview.setOnItemClickListener(this);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                      /* ArrayList<String> states=new ArrayList<String>();
                        for(StateBean sb:list)
                        {
                            if(sb.getVisibility())
                                states.add(sb.getStates());
                        }
*/

                        Intent intent = new Intent();
                        intent.putExtra("stateval", val);
                        setResult(RESULT_OK, intent);
                        finish();
                }
            });

        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_country_filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {;

        //val =(String) adapterView.getItemAtPosition(position);
        //   Log.e("country selected",val);
        for(StateBean sb:list)
        {
            sb.setVisibility(false);

        }

          StateBean sb=list.get(position);
           sb.setVisibility(true);
        val=list.get(position).getStates();
        Log.e("value",val);


        adapter.notifyDataSetChanged();
    }


    public class MySimpleArrayAdapter extends BaseAdapter {
         Context context;
        String[] values;
         ArrayList<StateBean> values1;


        public MySimpleArrayAdapter(Context context, ArrayList<StateBean> values1) {

            this.context = context;
            this.values1 = values1;
        }






        @Override
        public int getCount() {
            return values1.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.countryrowlayout, parent, false);

            StateBean sb=values1.get(position);

            TextView textView = (TextView) rowView.findViewById(R.id.countryname);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.check_mark);
            textView.setText(sb.getStates());

            if (sb.getVisibility())
            {
                imageView.setVisibility(View.VISIBLE);

            }
            else
            {
                imageView.setVisibility(View.INVISIBLE);
            }
            return rowView;
        }


    }


}
