package ir.rayco.ocr;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;




import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.luseen.spacenavigation.SpaceOnLongClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

import ir.rayco.ocr.fragment.Ocr_Translate;
import ir.rayco.ocr.fragment.ocr;
import ir.rayco.ocr.fragment.sample;
import ir.rayco.ocr.fragment.speed;
import ir.rayco.ocr.fragment.translate;

public class Main2Activity extends AppCompatActivity {


    public  String en;
    String urlJsonObj;
    String jsonResponse;
    SpaceNavigationView spaceNavigationView;



    static String TAG = Main2Activity.class.getSimpleName();

    TextView txtResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main2);


        spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);


        //add item for spaceNavigationView(botoom view)
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("HOME", R.mipmap.ic_launcher));
        spaceNavigationView.addSpaceItem(new SpaceItem("SEARCH", R.mipmap.ic_launcher));


//en=ocr.textview.getText().toString();
en="hi";







//creat three fragment for app
        //ocr_translate,
        // ocr,
        // speed

        final ocr ocrfragment=new ocr();
        final sample Sample=new sample();

        final translate translatefragment=new translate();
        final Ocr_Translate ocr_translate=new Ocr_Translate();
        final speed speedfragment=new speed();
        final FragmentManager manager = getFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();

       manager.beginTransaction().replace(R.id.myfragment, translatefragment).commit();

       //  manager.beginTransaction().replace(R.id.myfragment, ocrfragment).commit();
        transaction.addToBackStack(null);
    //      transaction.remove(translatefragment);


















//click on center button on spaceNavigationView
        //load fragment:ocr

        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {


                manager.beginTransaction().replace(R.id.myfragment, ocrfragment).commit();



            }


            //click on left and right button on  spaceNavigationView
            //left(home) right(sound)
            @Override
            public void onItemClick(int itemIndex, String itemName) {

                //button home
                if (itemName=="HOME") {

                    //load fragment:translate
                   manager.beginTransaction().replace(R.id.myfragment, translatefragment).commit();
                   transaction.addToBackStack(null);
                    transaction.remove(translatefragment);
                }

              //  button sound
                else {

            //load fragment: speedfragment
                    manager.beginTransaction().replace(R.id.myfragment, speedfragment).commit();
                    transaction.addToBackStack(null);
                    transaction.remove(speedfragment);


                }
            }



            //reselect item on    spaceNavigationView

            @Override
            public void onItemReselected(int itemIndex, String itemName) {

                //home

                if (itemName=="HOME") {

                    //load fragment:translate
                          manager.beginTransaction().replace(R.id.myfragment, translatefragment).commit();
                         transaction.addToBackStack(null);
                          transaction.remove(translatefragment);
                }

                //sound
                else {

                    //load fragment: speedfragment
                    manager.beginTransaction().replace(R.id.myfragment, speedfragment).commit();
                    transaction.addToBackStack(null);
                    transaction.remove(speedfragment);



                }



            }
        });











//long click  on CentreButto
        spaceNavigationView.setSpaceOnLongClickListener(new SpaceOnLongClickListener() {
            @Override
            public void onCentreButtonLongClick() {
                //toast
                Toast.makeText(Main2Activity.this,"onCentreButtonLongClick", Toast.LENGTH_SHORT).show();

              /*  String mytexxxt = URLEncoder.encode(en);
                urlJsonObj ="http://api.mymemory.translated.net/get?q="+mytexxxt+"&langpair=en|fa";
                makeJsonObjectRequest();*/
            }

            @Override

           // long click  on left and right item
            public void onItemLongClick(int itemIndex, String itemName) {
                //toast
                Toast.makeText(Main2Activity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
            }
        });

























    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        spaceNavigationView.onSaveInstanceState(outState);
    }


    private void makeJsonObjectRequest() {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                // Log.d(TAG, response.toString());

                try {

                    JSONObject responseData = response.getJSONObject("responseData");
                    String translatedText = responseData.getString("translatedText");

                    jsonResponse = "";
                    jsonResponse += "ترجمه: " + translatedText + "\n\n";

                    txtResponse.setText(jsonResponse);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }
                , new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }
}
