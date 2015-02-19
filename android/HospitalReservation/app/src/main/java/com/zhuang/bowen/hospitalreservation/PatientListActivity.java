package com.zhuang.bowen.hospitalreservation;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.view.Gravity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PatientListActivity extends ActionBarActivity {
    private HttpClient httpclient = new DefaultHttpClient();
    private HttpResponse response = null;
    private StatusLine statusLine = null;
    private String URL = "http://10.113.21.167/4x4/patient_getJson.php";
    String responseString = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);
        httpclient = new DefaultHttpClient();
        //init();

            new TheTask().execute(URL);


    }


    class TheTask extends AsyncTask<String,String,String>
    {

        private TableLayout stk;

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            // update textview here
            //textView.setText("Server message is "+result);
            responseString = result;
            if(!responseString.equals("No string.")&& !responseString.equals("Network problem")){
                init();
            }

        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try
            {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost method = new HttpPost(params[0]);
                HttpResponse response = httpclient.execute(method);
                HttpEntity entity = response.getEntity();
                if(entity != null){
                    return EntityUtils.toString(entity);
                }
                else{
                    return "No string.";
                }
            }
            catch(Exception e){
                return "Network problem";
            }

        }

    }

    private JSONArray patientList;
    public void init( ) {
        try {
            patientList = new JSONArray(responseString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setText("Patient No ");
        tv0.setTextColor(Color.BLACK);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" Name ");
        tv1.setTextColor(Color.BLACK);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText("Date of Birth ");
        tv2.setTextColor(Color.BLACK);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText("Phone Number");
        tv3.setTextColor(Color.BLACK);
        tbrow0.addView(tv3);
        /*TextView tv4 = new TextView(this);
        tv3.setText(" Is Called ");
        tv3.setTextColor(Color.BLACK);
        tbrow0.addView(tv4);*/
        stk.addView(tbrow0);
        String name ="";
        String phone="";
        String birthday="";
        String number = "";
        String isCalled ="";
        String isTexted = "";
        if(patientList!=null){
            for (int i = 0; i < patientList.length(); i++) {
                JSONObject jsonobject = null;
                try {
                    //{"id":"1","pname":"becky liu","dob":"1989-12-25","phone":"1-233-123-1234","bCalled":"0","bSentSMS":"0"}
                    jsonobject = patientList.getJSONObject(i);
                    name = jsonobject.getString("pname");
                    number = jsonobject.getString("id");
                    phone = jsonobject.getString("phone");
                    isCalled = jsonobject.getString("bCalled");
                    isTexted = jsonobject.getString("bSentSMS");

                    TableRow tbrow = new TableRow(this);
                    TextView t1v = new TextView(this);
                    t1v.setText(number);
                    t1v.setTextColor(Color.BLACK);
                    t1v.setGravity(Gravity.CENTER);
                    tbrow.addView(t1v);
                    TextView t2v = new TextView(this);
                    t1v.setText(name);
                    t1v.setTextColor(Color.BLACK);
                    t1v.setGravity(Gravity.CENTER);
                    tbrow.addView(t2v);
                    TextView t3v = new TextView(this);
                    t1v.setText(birthday);
                    t1v.setTextColor(Color.BLACK);
                    t1v.setGravity(Gravity.CENTER);
                    tbrow.addView(t3v);
                    TextView t4v = new TextView(this);
                    t1v.setText(phone);
                    t1v.setTextColor(Color.BLACK);
                    t1v.setGravity(Gravity.CENTER);
                    tbrow.addView(t4v);
                    stk.addView(tbrow);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_patient_list, menu);
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
}
