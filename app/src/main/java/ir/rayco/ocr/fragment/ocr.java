package ir.rayco.ocr.fragment;


import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;

import ir.rayco.ocr.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ocr extends Fragment {




    //ocr

    SurfaceView cameraview;
   public static TextView textview;
    CameraSource cameraSource;
    final int id = 1001;






    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {

            case  id:
            {
                if (grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED);
                    return;
                }

                try {
                    cameraSource.start(cameraview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }








    public ocr() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view= inflater.inflate(R.layout.fragment_ocr, container, false);


        cameraview = (SurfaceView)view.findViewById(R.id.surface);
        textview = (TextView)view.findViewById(R.id.textview);
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getActivity()).build();

        if (!textRecognizer.isOperational()) {
            Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
        } else {

            cameraSource = new CameraSource.Builder(getActivity(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();
            cameraview.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {

                    try {
                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.

                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.CAMERA},
                                    id);




                            return;
                        }
                        cameraSource.start(cameraview.getHolder());

                    } catch (IOException e) {

                    }


                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {

                    cameraSource.stop();

                }
            });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {

                    final SparseArray<TextBlock> items=detections.getDetectedItems();
                    if (items.size()!=0 )
                    {
                        textview.post(new Runnable() {
                            @Override
                            public void run() {

                                StringBuilder stringBuilder=new StringBuilder();

                                for (int i=0;i<items.size();++i)
                                {
                                    TextBlock item=items.valueAt(i);
                                    stringBuilder.append(item.getValue());
                                    stringBuilder.append("");
                                }
                                textview.setText(stringBuilder.toString());

                            }
                        });

                    }

                }
            });


        }



//click on textview for do translate
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(),"",Toast.LENGTH_SHORT).show();
              /*  onPause();
                onDestroy();
                onStop();*/




                //load ocr_translate for translate word what load by ocr
                final Ocr_Translate ocr_translate=new Ocr_Translate();






                final FragmentManager manager = getFragmentManager();
                final FragmentTransaction transaction = manager.beginTransaction();

                manager.beginTransaction().replace(R.id.myfragment, ocr_translate).commit();



             //   transaction.remove(ocrr);

             //   transaction.remove(ocrFragment);
             /*   transaction.addToBackStack(null);
             */










            }
        });











        return view;
    }



}
