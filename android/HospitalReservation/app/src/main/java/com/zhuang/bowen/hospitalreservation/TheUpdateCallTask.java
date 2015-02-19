package com.zhuang.bowen.hospitalreservation;

import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by Bowem on 2015-02-19.
 */
class TheUpdateCallTask extends AsyncTask<String,String,String>
{
    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        //Toast.makeText(getBaseContext(), result,
          //      Toast.LENGTH_SHORT).show();

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

