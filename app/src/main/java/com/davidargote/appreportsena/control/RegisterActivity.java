package com.davidargote.appreportsena.control;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.davidargote.appreportsena.R;
import com.davidargote.appreportsena.model.ManagerHelper;
import com.davidargote.appreportsena.model.Report;
import com.frosquivel.magicalcamera.MagicalCamera;
import com.frosquivel.magicalcamera.MagicalPermissions;
import com.frosquivel.magicalcamera.Utilities.ConvertSimpleImage;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Formatter;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnReturn, btnCalendar, btnPencil, btnRecord, btnStop;
    private ImageView imgPhoto;
    private EditText editTitle;
    private Button btnSave;
    private ConstraintLayout layout;

    public String[] PERMISSONS = {Manifest.permission.CAMERA, Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO};

    public int CODE_PERMISSONS = 111;

    private int RESIZE_PHOTO_PIXEL = 80;

    MagicalPermissions mp;
    MagicalCamera mc;

    private byte[] mPhoto;
    private String fichero;

    MediaRecorder mediaRecorder;

    LocationManager locationManager;
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        referent();

        //Instacias Magical Camera
        mp = new MagicalPermissions(this, PERMISSONS);
        mc = new MagicalCamera(RegisterActivity.this, RESIZE_PHOTO_PIXEL, mp);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkCallingOrSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                    checkCallingOrSelfPermission(Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED &&
                    checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    checkCallingOrSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    checkCallingOrSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED &&
                    checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    checkCallingOrSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, PERMISSONS, CODE_PERMISSONS);

            }

        }

        btnStop.setVisibility(View.INVISIBLE);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


    }

    private void referent() {

        btnReturn = findViewById(R.id.btnVolverReg);
        btnReturn.setOnClickListener(this);

        btnCalendar = findViewById(R.id.btnCalendar);
        btnCalendar.setOnClickListener(this);

        btnPencil = findViewById(R.id.btnPicel);
        btnPencil.setOnClickListener(this);

        btnRecord = findViewById(R.id.btnMicro);
        btnRecord.setOnClickListener(this);

        imgPhoto = findViewById(R.id.imgFoto);
        imgPhoto.setOnClickListener(this);

        btnSave = findViewById(R.id.btnGuardar);
        btnSave.setOnClickListener(this);

        btnStop = findViewById(R.id.btnStop);
        btnStop.setOnClickListener(this);

        editTitle = findViewById(R.id.editTitulo);

        layout = findViewById(R.id.constReport);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnVolverReg:
                finish();
                break;

            case R.id.btnCalendar:
                ejetcCalendar();
                break;

            case R.id.btnPicel:
                // Pincel - falta implementar
                break;

            case R.id.btnMicro:
                recordAudio();
                break;

            case R.id.imgFoto:
                takePhto();
                break;

            case R.id.btnGuardar:
                saveReport();
                break;

            case R.id.btnStop:
                stopRecording();
                break;

        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {

            mc.resultPhoto(requestCode, resultCode, data, mc.ORIENTATION_ROTATE_90);

            imgPhoto.setImageBitmap(mc.getPhoto());

            mPhoto = ConvertSimpleImage.bitmapToBytes(mc.getPhoto(), MagicalCamera.PNG);

        } catch (Exception e) {
            e.printStackTrace();
            imgPhoto.setImageResource(R.drawable.logo_cam);
        }
    }

    private void takePhto() {

        mc.takePhoto();

    }

    private void ejetcCalendar() {

        Calendar cal = Calendar.getInstance();

        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", cal.getTimeInMillis());
        intent.putExtra("allDay", true);
        intent.putExtra("rrule", "FREQ=YEARLY");
        intent.putExtra("endTime", cal.getTimeInMillis() + 60 * 60 * 60 * 1000);

        if (editTitle.getText().toString().equals("")) {
            Toast.makeText(this, "Pon un titulo, por favor.", Toast.LENGTH_SHORT).show();
            editTitle.requestFocus();
            editTitle.setError("Escribe un titulo");
        } else {
            intent.putExtra("title", editTitle.getText().toString());
            startActivity(intent);
        }

    }

    private void recordAudio() {

        if (editTitle.getText().toString().equals("")) {
            Toast.makeText(this, "Escribe un titulo, por favor", Toast.LENGTH_SHORT).show();
            editTitle.requestFocus();
            editTitle.setError("Escribe un titulo");
        } else {

            fichero = Environment.getExternalStorageDirectory() + "/audio" + editTitle.getText().toString() + ".3gp";

            mediaRecorder = new MediaRecorder();
            mediaRecorder.setOutputFile(fichero);
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            try {
                mediaRecorder.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mediaRecorder.start();

            btnRecord.setEnabled(false);
            btnStop.setVisibility(View.VISIBLE);
        }


    }

    private void stopRecording() {
        mediaRecorder.stop();
        btnRecord.setEnabled(true);
        btnStop.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mediaRecorder != null) {
            mediaRecorder.stop();
            btnRecord.setEnabled(true);
            btnStop.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mediaRecorder != null) {
            mediaRecorder.stop();
            btnRecord.setEnabled(true);
            btnStop.setVisibility(View.INVISIBLE);
        }

    }

    private void saveReport() {

        ManagerHelper managerHelper = new ManagerHelper(RegisterActivity.this);

        Report report = new Report();

        if (editTitle.getText().toString().isEmpty())
            editTitle.setText("Llena el campo, por favor");
        else report.setTitleReport(editTitle.getText().toString());

        report.setImageReport(mPhoto);
        report.setUrlAudio(fichero);
        report.setLatReport(location.getLatitude());
        report.setLongReport(location.getLongitude());

        long mInsert = managerHelper.insertDatReport(report);

        if (mInsert > 0) {
            Snackbar.make(layout, "Reporte registrado", Snackbar.LENGTH_SHORT).show();
            clear();
        } else {
            Snackbar.make(layout, "Reporte no registrado", Snackbar.LENGTH_SHORT).show();
        }

    }

    private void clear() {

        editTitle.setText("");

    }

}
