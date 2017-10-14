package ir.rayco.ocr.fragment;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

import ir.rayco.ocr.AppController;
import ir.rayco.ocr.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class translate extends Fragment {


    public translate() {
        // Required empty public constructor
    }



    Intent intent;
    //TextView speech_output;
    private final int SPEECH_REQUEST_CODE = 123;


    // json object response url
    String urlJsonObj ;

    // json array response url

    static String TAG = translate.class.getSimpleName();
    Button btnMakeObjectRequest, entofa;

    // Progress dialog
    ProgressDialog pDialog;

    TextView txtResponse;

    EditText mytext;
    String jsonResponse;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_translate, container, false);








        btnMakeObjectRequest = (Button)view.findViewById(R.id.btnObjRequest);
        entofa = (Button)view.findViewById(R.id.entofa);
        txtResponse = (TextView)view.findViewById(R.id.txtResponse);
        mytext = (EditText)view.findViewById(R.id.myText);




        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("در حال ترجمه.....");
        pDialog.setCancelable(false);


/*

        mytext.setDrawableClickListener(new DrawableClickListener() {


            public void onClick(DrawablePosition target) {
                switch (target) {
                    case LEFT:
                        //Do something here
                        break;

                    default:
                        break;
                }
            }

        });
*/






        btnMakeObjectRequest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String mytexxxt = URLEncoder.encode(mytext.getText().toString());
                urlJsonObj ="http://api.mymemory.translated.net/get?q="+mytexxxt+"&langpair=fa|en";
                makeJsonObjectRequest();
            }
        });

        entofa.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String mytexxxt = URLEncoder.encode(mytext.getText().toString());
                urlJsonObj ="http://api.mymemory.translated.net/get?q="+mytexxxt+"&langpair=en|it";
                makeJsonObjectRequest();
            }
        });
























        Button btn = (Button)view.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // promtSpeechInput();
            }
        });


        return view;
    }








    private void makeJsonObjectRequest() {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {

                    JSONObject responseData = response.getJSONObject("responseData");
                    String translatedText = responseData.getString("translatedText");

                    jsonResponse = "";
                    jsonResponse += "ترجمه: " + translatedText + "\n\n";

                    txtResponse.setText(jsonResponse);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }








}
