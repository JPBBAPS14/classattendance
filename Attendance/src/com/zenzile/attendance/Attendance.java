package com.zenzile.attendance;

import java.util.StringTokenizer;

import com.sihle.register.Student;
import com.sihle.register.repository.DatasourceDAO;
import com.zenzile.attendance.repository.DatasourceDAOImpl;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Handler;
//import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
//import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Button;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.AutoFocusCallback;
//import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.widget.TextView;
//import android.graphics.ImageFormat;

import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;
import net.sourceforge.zbar.Config;

//import java.util.*;

public class Attendance extends Activity {
    //database process
    final DatasourceDAO dao = new DatasourceDAOImpl(this);


    private Camera mCamera;
    private CameraPreview mPreview;
    private Handler autoFocusHandler;

    TextView scanText;
    Button scanButton;

    ImageScanner scanner;

    private boolean barcodeScanned = false;
    private boolean previewing = true;

    static {
        System.loadLibrary("iconv");
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_attendance);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        autoFocusHandler = new Handler();
        mCamera = getCameraInstance();

        // Instance barcode scanner
        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);

        mPreview = new CameraPreview(this, mCamera, previewCb, autoFocusCB);
        FrameLayout preview = (FrameLayout)findViewById(R.id.cameraPreview);
        preview.addView(mPreview);

        scanText = (TextView)findViewById(R.id.scanText);

        scanButton = (Button)findViewById(R.id.ScanButton);

        scanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (barcodeScanned) {
                    barcodeScanned = false;
                    scanText.setText("Scanning...");
                    mCamera.setPreviewCallback(previewCb);
                    mCamera.startPreview();
                    previewing = true;
                    mCamera.autoFocus(autoFocusCB);
                }
            }
        });
    }

    public void onPause() {
        super.onPause();
        releaseCamera();
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e){
        }
        return c;
    }
    
    public Student studentObject(String scan)
    {
    	Student student = new Student();
    	
    	StringTokenizer tk = new StringTokenizer(scan," ");/**/
        String studNo = tk.nextToken();
        String initials  = tk.nextToken();
        String surname = tk.nextToken();

        student.setStudentNumber(studNo);
        student.setInitials(initials);
        student.setStudentNumber(surname);
    	
    	return student;
    }

    private void releaseCamera() {
        if (mCamera != null) {
            previewing = false;
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            if (previewing)
                mCamera.autoFocus(autoFocusCB);
        }
    };

    Camera.PreviewCallback previewCb = new Camera.PreviewCallback() {
        public void onPreviewFrame(byte[] data, Camera camera) {
            Camera.Parameters parameters = camera.getParameters();
            Camera.Size size = parameters.getPreviewSize();

            Image barcode = new Image(size.width, size.height, "Y800");
            barcode.setData(data);

            //final Student student = new Student();

            int result = scanner.scanImage(barcode);

            if (result != 0) {
                previewing = false;
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();

                SymbolSet syms = scanner.getResults();

                


                for (Symbol sym : syms) {

                    scanText.setText("barcode result " + sym.getData());
                    
                    
                    Student student = studentObject(sym.getData());
                    try{
                    dao.createStudent(student);
                    }
                    catch (SQLiteException excep)
                    {
                     String asd = "";
                     
                    }


                    barcodeScanned = true;
                }
            }
        }
    };

    // Mimic continuous auto-focusing
    Camera.AutoFocusCallback autoFocusCB = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            autoFocusHandler.postDelayed(doAutoFocus, 1000);
        }
    };
}
