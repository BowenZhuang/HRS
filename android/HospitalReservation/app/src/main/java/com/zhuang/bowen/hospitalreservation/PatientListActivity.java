package com.zhuang.bowen.hospitalreservation;
import android.widget.Toast;
import android.telephony.SmsManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.app.Activity;
import android.app.ListActivity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.List;
import android.support.v4.widget.SwipeRefreshLayout;

public class PatientListActivity extends ListActivity implements SwipeRefreshLayout.OnRefreshListener{
    private HttpClient httpclient = new DefaultHttpClient();
    private HttpResponse response = null;
    private StatusLine statusLine = null;
    public static String Server = "192.168.0.116";
    private String URL = "http://" + Server + "/4x4/patient_getjson.php";
    String responseString = "";
    private JSONArray patientList;
    private List<Patient> patients = new ArrayList<Patient>();
    private SwipeRefreshLayout swipeLayout;
    private ListView mListView;
    private String SMSMessage = "You can see a doctor within 20 minutes.[From Kungfu Panda]";
    public PatientListActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(android.R.color.holo_blue_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        httpclient = new DefaultHttpClient();
        // Retrieve the SwipeRefreshLayout and ListView instances

        //init();
        refreshList();
    }

    private void refreshList(){
        TheTask task =   new TheTask();
        task.context = this;
        task.execute(URL);
    }


    @Override
    public void onRefresh() {
        refreshList();
    }


    class TheTask extends AsyncTask<String,String,String>
    {

        private TableLayout stk;
        private Activity context;
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            // update textview here
            //textView.setText("Server message is "+result);
            responseString = result;
            if(!responseString.equals("No string.")&& !responseString.equals("Network problem")){
                init();
                ListAdapter adapter = new PatientListAdpter(context, patients);
                setListAdapter( adapter);
                swipeLayout.setRefreshing(false);
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


    private void updateSMS(Patient curPatient){
            String urlClick = "http://" + Server + "/4x4/patient_updatesms.php?id=";
            urlClick += curPatient.getId() + "&sms=1";
            new TheUpdateCallTask().execute(urlClick);
            // phonenumber -> the content(sms message)
            sendSMS(curPatient.getPhone(), SMSMessage);
            curPatient.setMsgSent(true);
    }

    //---sends an SMS message to another device---
    private void sendSMS(String phoneNumber, String message)
    {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }


    public void init( ) {
        try {
            patientList = new JSONArray(responseString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(patientList!=null){
            String name;
            String number;
            String phone;
            String isCalled;
            String isTexted;
            String birthday;
            patients.clear();
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
                    birthday = jsonobject.getString("dob");
                    boolean bcalled = false;
                    boolean bTexted = false;
                    if(isCalled.equals("1")){
                        bcalled = true;
                    }
                    if(isTexted.equals("1")){
                        bTexted = true;
                    }

                    Patient patient =new Patient(name,number,phone,birthday,bcalled,bTexted);
                    if(bTexted == false){
                        updateSMS(patient);
                        bTexted = true;
                        patient.setMsgSent(true);
                    }
                    patients.add(patient);

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
