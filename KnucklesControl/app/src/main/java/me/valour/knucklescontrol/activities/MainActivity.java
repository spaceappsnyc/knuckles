package me.valour.knucklescontrol.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import me.valour.knucklescontrol.fragments.PlaceholderFragment;
import me.valour.knucklescontrol.R;
import me.valour.knucklescontrol.fragments.PrefsFragment;
import me.valour.knucklescontrol.helpers.Commander;


public class MainActivity extends ActionBarActivity implements PlaceholderFragment.PlaceholderFragmentListener {

    PlaceholderFragment frag;
    FragmentManager manager;
    RequestQueue requestQueue;

    public static String boardHost = "";
    public static final String PREFS_NAME = "SETTINGS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Startup","Initiating startup sequence.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frag = new PlaceholderFragment();
        manager = getFragmentManager();

        if (savedInstanceState == null) {
            manager.beginTransaction().add(R.id.body, frag).commit();
        }


        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            private long time = 0;

            @Override
            public void run()
            {
                // do stuff then
                // can call h again after work!
                time += 2000;
                Log.d("TimerExample", "Going for... " + time);
                updateStatus();
                h.postDelayed(this, 2000);
            }
        }, 1000);

         // 1 second delay (takes millis)
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
            manager.beginTransaction().replace(R.id.body, new PrefsFragment()).commit();
            return true;
        }

        else if (id == R.id.action_dashbaord){
            manager.beginTransaction().replace(R.id.body, frag).commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static final int SPEECH_REQUEST_CODE = 0;

    // Create an intent that can start the Speech Recognizer activity
    public void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        // Start the activity, the intent will be populated with the speech text
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    public void updatePreferences(){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
              //  getSharedPreferences(PREFS_NAME, 0);

        boardHost = settings.getString(getString(R.string.host_key), getString(R.string.host_vaue_default));
        Log.d("url", boardHost);
    }

    // This callback is invoked when the Speech Recognizer returns.
    // This is where you process the intent and extract the speech text from the intent.
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            Log.d("voice", spokenText);

            int command = Commander.recognize(spokenText);

            if(command==Commander.LIGHTS){
                toggleLights();
                if(frag.isLightOn()){
                    frag.setText("Let there be light!");
                } else {
                    frag.setText("It's more fun in the dark");
                }

            } else if (command==Commander.COLDER || command==Commander.HOTTER) {
                requestTemperatureChange(command);
                frag.setText("Temperature Adjusted");
            } else {
                frag.setText("Unknown command: "+spokenText);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(this);
        }
        return requestQueue;
    }

    private void updateStatus() {
        requestQueue = getRequestQueue();
        String url = boardHost+"/status";

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("json", response.toString());
                try {
                    boolean isHeating = response.getBoolean("is_heating");
                    if(isHeating) {

                        frag.registerTempChange(1);
                        Log.d("Heat","Heating");
                    } else {

                        frag.registerTempChange(-1);
                        Log.d("Cool","Cooling");
                    }
                    JSONArray temps = response.getJSONArray("temps");
                    int len = temps.length();
                    for(int i=0; i<len; i++){
                        frag.registerFingerTemperatures(i, (float)temps.getDouble(i) );
                    }
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e("json", error.getMessage());
                Log.e("json", "Error reaching server");
            }
        });

        requestQueue.add(jsonRequest);
    }

    private void toggleLights() {
        Log.d("LIGHTS","LIGHTS");
        frag.toogleLights();
        /*
        requestQueue = getRequestQueue();
        String url = boardHost+"/light";

        JSONObject obj = new JSONObject();
        try {
            obj.put("on", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("json", response.toString());
                try {
                    boolean success = response.getBoolean("on");
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String msg = (error == null) ? "Error reaching server" : error.getMessage();
                Log.e("json", msg);
            }
        });

        requestQueue.add(jsonRequest);
        */
    }

    private void requestTemperatureChange(int delta) {
        if(delta != Commander.COLDER && delta != Commander.HOTTER) {
           return;
        }
        requestQueue = getRequestQueue();
        String url = boardHost+"/heat";

        Log.d("Request temp change", ""+delta);
        String mode = delta==1 ? "on" : "off";

        JSONObject obj = new JSONObject();
        try {
            obj.put("mode", mode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        frag.registerTempChange(delta);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("json", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("json", "error reaching server");
            }
        });

        requestQueue.add(jsonRequest);
    }

}
