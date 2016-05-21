package com.init.luckyfriend.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.init.luckyfriend.R;

import java.util.ArrayList;


public class MaritalFilter extends AppCompatActivity implements AdapterView.OnItemClickListener {


    String[] statusArray = {"Single","Widowed","Separated","Divorced"};
    ImageView check;
    ListView listview;
    MySimpleArrayAdapter adapter;
    boolean[] mVisisbilityList = { false, false, false, false };
    ImageButton back;
    String val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marital_filter);

        back=(ImageButton)findViewById(R.id.back);
        listview=(ListView)findViewById(R.id.list);
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < statusArray.length; ++i) {
            list.add(statusArray[i]);
        }
        adapter = new MySimpleArrayAdapter(this,mVisisbilityList,statusArray);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(val==null){

                    Toast.makeText(getApplicationContext(), "Select Status first ", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent();
                    intent.putExtra("statusval", val);
                    setResult(RESULT_OK, intent);
                    finish();
                }
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

        val =(String) adapterView.getItemAtPosition(position);
        //   Log.e("country selected",val);

        for (int i = 0; i < 4; i++)
        {
            mVisisbilityList[i] = false;
        }
        mVisisbilityList[position] = true;

        adapter.setVisibilityList(mVisisbilityList);

        adapter.notifyDataSetChanged();
    }


    public class MySimpleArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] values;
        private boolean[] mVisibilityList = null;

        public MySimpleArrayAdapter(Context context,boolean[] iVisibilityList, String[] values) {
            super(context, -1, values);
            this.context = context;
            this.values = values;
            mVisibilityList = iVisibilityList;
        }

        public void setVisibilityList(boolean[] iVisibilityList)
        {
            mVisibilityList = iVisibilityList;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.countryrowlayout, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.countryname);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.check_mark);
            textView.setText(values[position]);

            if (mVisibilityList[position])
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
