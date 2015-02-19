package example.com.hrs;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import example.com.hrs.JSONParser;
import example.com.hrs.PatientInfo;


public class MainActivity extends ActionBarActivity {
    //URL to get JSON Array
    public static String url = "http://192.168.0.116/4x4/patient_getjson.php";
    //JSON Node Names
    private static final String TAG_KEY = "patient";
    private static final String TAG_NAME = "pname";
    private static final String TAG_ID = "id";
    private static final String TAG_DOB = "dob";
    private static final String TAG_PHONE = "phone";
    private static final String TAG_BCALLED = "bCalled";
    private static final String TAG_BSENTSMS = "bSentSMS";
    // Maximum number of array
    private static final int MAX_PATIENT = 20;

    // patient information array
    PatientInfo[] pInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pInfo = new PatientInfo[MAX_PATIENT];
        for(int k=0; k<MAX_PATIENT; k++) {
            pInfo[k] = new PatientInfo();
        }

        GetPatientInfo();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    private void GetPatientInfo(){
        new HttpAsyncTask().execute();
    }

    private class HttpAsyncTask extends AsyncTask<String, String, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... urls) {
            // Creating new JSON Parser
            JSONParser jParser = new JSONParser();
            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(url);
            return json;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(JSONObject json) {
            boolean bRet = true;
            try {
                // Getting JSON Array
                JSONArray user = json.getJSONArray(TAG_KEY);

                // Getting JSON Array
                for(int k=0; k<MAX_PATIENT; k++) {

                    if(k >= user.length()) break;

                    JSONObject c = user.getJSONObject(k);
                    // Storing  JSON item in a Variable
                    pInfo[k].id = c.getInt(TAG_ID);
                    pInfo[k].name = c.getString(TAG_NAME);
                    pInfo[k].dob = c.getString(TAG_DOB);
                    pInfo[k].phone = c.getString(TAG_PHONE);
                    pInfo[k].bCalled = c.getInt(TAG_BCALLED);
                    pInfo[k].bSentSMS = c.getInt(TAG_BSENTSMS);
                }
                if(user.length() < MAX_PATIENT){
                    for(int k=user.length(); k<MAX_PATIENT; k++) {
                        pInfo[k].id = 0;
                        pInfo[k].name = "";
                        pInfo[k].dob = "";
                        pInfo[k].phone = "";
                        pInfo[k].bCalled = 0;
                        pInfo[k].bSentSMS = 0;
                    }
                }
            } catch (JSONException e) {
                bRet = false;
                e.printStackTrace();
            }
            if(bRet) {
                //Importing TextView
                //Set JSON Data in TextView
                // for test
                TextView tv = (TextView)findViewById(R.id.textView);
                tv.setText(pInfo[0].id + ":" + pInfo[0].name);
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
}
