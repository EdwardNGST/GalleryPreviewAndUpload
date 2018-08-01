package com.example.alan_.gallerypreviewandupload;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.alan_.gallerypreviewandupload.model.Utility;
import com.example.alan_.gallerypreviewandupload.model.VolleyAppController;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ImageView imgPrev1, imgPrev2, imgPrev3;
    ImageView imgPrevSupr1, imgPrevSupr2, imgPrevSupr3;
    ImageView imgPrevZoom1, imgPrevZoom2, imgPrevZoom3;
    ImageView imgClosePrev;
    ImageView imgPreview;
    CardView cardPrev1, cardPrev2, cardPrev3;
    Button btnUpload;
    ImageView btnCamera, btnFolder;
    Boolean card1, card2, card3;
    View view;
    String userChoosenTask;
    int seconds;
    long startTime = 0;
    final int SELECT_FILE = 1;
    private final int REQUEST_CAMERA = 0;
    private Bitmap bitmap1, bitmap2, bitmap3;
    private Uri uriImage1, uriImage2, uriImage3;
    private StorageReference nStorage1, nStorage2, nStorage3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgPrev1=findViewById(R.id.imgPrev1);
        imgPrev2=findViewById(R.id.imgPrev2);
        imgPrev3=findViewById(R.id.imgPrev3);

        imgClosePrev=findViewById(R.id.imgClosePrev);
        imgClosePrev.setVisibility(view.INVISIBLE);

        imgPrevSupr1=findViewById(R.id.imgPrevSupr1);
        imgPrevSupr2=findViewById(R.id.imgPrevSupr2);
        imgPrevSupr3=findViewById(R.id.imgPrevSupr3);

        cardPrev1=findViewById(R.id.cardPrev1);
        cardPrev2=findViewById(R.id.cardPrev2);
        cardPrev3=findViewById(R.id.cardPrev3);

        cardPrev1.setVisibility(view.INVISIBLE);
        cardPrev2.setVisibility(view.INVISIBLE);
        cardPrev3.setVisibility(view.INVISIBLE);

        btnUpload=findViewById(R.id.btnUpload);
        btnCamera=findViewById(R.id.btnCamera);
        btnFolder=findViewById(R.id.btnFolder);

        imgPreview=findViewById(R.id.imgPreview);

        imgPrevZoom1=findViewById(R.id.imgPrevZoom1);
        imgPrevZoom2=findViewById(R.id.imgPrevZoom2);
        imgPrevZoom3=findViewById(R.id.imgPrevZoom3);

        card1=false;
        card2=false;
        card3=false;
        uriImage1=null;
        uriImage2=null;
        uriImage3=null;

        nStorage1 = FirebaseStorage.getInstance().getReference();
        nStorage2 = FirebaseStorage.getInstance().getReference();
        nStorage3 = FirebaseStorage.getInstance().getReference();

        imgPrevZoom1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    imgPreview.setImageBitmap(bitmap1);
                    imgClosePrev.setVisibility(view.VISIBLE);
                }
                return true;
            }
        });

        imgClosePrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgClosePrev.setVisibility(view.INVISIBLE);
                imgPreview.setImageResource(0);
            }
        });

        imgPrevSupr1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    imgPrev1.setVisibility(view.INVISIBLE);
                    imgPrevSupr1.setVisibility(view.INVISIBLE);
                    imgPrevZoom1.setVisibility(view.INVISIBLE);
                    card1=false;
            }
        });

        imgPrevZoom2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    imgPreview.setImageBitmap(bitmap2);
                    imgClosePrev.setVisibility(view.VISIBLE);
                }
                return true;
            }
        });
        imgPrevSupr2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgPrev2.setVisibility(view.INVISIBLE);
                imgPrevSupr2.setVisibility(view.INVISIBLE);
                imgPrevZoom2.setVisibility(view.INVISIBLE);
                card2=false;
            }
        });

        imgPrevZoom3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    imgPreview.setImageBitmap(bitmap3);
                    imgClosePrev.setVisibility(view.VISIBLE);
                }
                return true;
            }
        });
        imgPrevSupr3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgPrev3.setVisibility(view.INVISIBLE);
                imgPrevSupr3.setVisibility(view.INVISIBLE);
                imgPrevZoom3.setVisibility(view.INVISIBLE);
                card3=false;
            }
        });

        /*btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Camara", Toast.LENGTH_SHORT).show();
            }
        });
        btnFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Folder", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            timerHandler.postDelayed(this, 500);
            if (seconds==3){
                imgPreview.setImageResource(0);
                timerHandler.removeCallbacks(timerRunnable);
            }
        }
    };


    public void btnGalleryClick(View view) {
        galleryIntent();
    }

    public void btnCameraClick(View view) {
        cameraIntent();
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("loadgaleria"))
                        galleryIntent();
                    else if(userChoosenTask.equals("loadcamera"))
                        cameraIntent();
                    else if(userChoosenTask.equals("subirimagen"))
                        Log.i("MSG", "Imagen");
                } else {
                    Log.i("MSG", "Error");
                }
                break;
        }
    }

    private void galleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }
    private void onCaptureImageResult(Intent data) {
        Bitmap bitmap = null;
        Uri uriImage = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            uriImage = data.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap5 = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap5.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        imgPrev1.setRotation(90);

        if(card1==false&&card2==false&&card3==false){
            card1=true;
            imgPrev1.setImageBitmap(bitmap); //Asignar el bitmap a el ImageView
            bitmap1=bitmap;
            cardPrev1.setVisibility(view.VISIBLE);
            imgPrev1.setVisibility(view.VISIBLE);
            imgPrevZoom1.setVisibility(view.VISIBLE);
            imgPrevSupr1.setVisibility(view.VISIBLE);
            uriImage1=uriImage;
        }else if(card1==true&&card2==false&&card3==false){
            card2=true;
            imgPrev2.setImageBitmap(bitmap);
            bitmap2=bitmap;
            cardPrev2.setVisibility(view.VISIBLE);
            imgPrev2.setVisibility(view.VISIBLE);
            imgPrevZoom2.setVisibility(view.VISIBLE);
            imgPrevSupr2.setVisibility(view.VISIBLE);
            uriImage2=uriImage;
        }else if(card1==true&&card2==true&&card3==false){
            card3=true;
            imgPrev3.setImageBitmap(bitmap);
            bitmap3=bitmap;
            cardPrev3.setVisibility(view.VISIBLE);
            imgPrev3.setVisibility(view.VISIBLE);
            imgPrevZoom3.setVisibility(view.VISIBLE);
            imgPrevSupr3.setVisibility(view.VISIBLE);
            uriImage3=uriImage;
        }else if(card1==false&&card2==true&&card3==true){
            card1=true;
            imgPrev1.setImageBitmap(bitmap);
            bitmap1=bitmap;
            cardPrev1.setVisibility(view.VISIBLE);
            imgPrev1.setVisibility(view.VISIBLE);
            imgPrevZoom1.setVisibility(view.VISIBLE);
            imgPrevSupr1.setVisibility(view.VISIBLE);
            uriImage1=uriImage;
        }else if(card1==true&&card2==false&&card3==true){
            card2=true;
            imgPrev2.setImageBitmap(bitmap);
            bitmap2=bitmap;
            cardPrev2.setVisibility(view.VISIBLE);
            imgPrev2.setVisibility(view.VISIBLE);
            imgPrevZoom2.setVisibility(view.VISIBLE);
            imgPrevSupr2.setVisibility(view.VISIBLE);
            uriImage2=uriImage;
        }else if(card1==false&&card2==false&&card3==true){
            card1=true;
            imgPrev1.setImageBitmap(bitmap);
            bitmap1=bitmap;
            cardPrev1.setVisibility(view.VISIBLE);
            imgPrev1.setVisibility(view.VISIBLE);
            imgPrevZoom1.setVisibility(view.VISIBLE);
            imgPrevSupr1.setVisibility(view.VISIBLE);
            uriImage1=uriImage;
        }
        /*BitmapDrawable ob = new BitmapDrawable(getResources(), bitmap2);
        imagePreview.setBackground(null);
        imagePreview.setBackground(ob);*/
    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bitmap=null;
        Uri uriImage = null;
        if (data != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                uriImage = data.getData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(card1==false&&card2==false&&card3==false){
            card1=true;
            imgPrev1.setImageBitmap(bitmap); //Asignar el bitmap a el ImageView
            bitmap1=bitmap;
            cardPrev1.setVisibility(view.VISIBLE);
            imgPrev1.setVisibility(view.VISIBLE);
            imgPrevZoom1.setVisibility(view.VISIBLE);
            imgPrevSupr1.setVisibility(view.VISIBLE);
            uriImage1=uriImage;
        }else if(card1==true&&card2==false&&card3==false){
            card2=true;
            imgPrev2.setImageBitmap(bitmap);
            bitmap2=bitmap;
            cardPrev2.setVisibility(view.VISIBLE);
            imgPrev2.setVisibility(view.VISIBLE);
            imgPrevZoom2.setVisibility(view.VISIBLE);
            imgPrevSupr2.setVisibility(view.VISIBLE);
            uriImage2=uriImage;
        }else if(card1==true&&card2==true&&card3==false){
            card3=true;
            imgPrev3.setImageBitmap(bitmap);
            bitmap3=bitmap;
            cardPrev3.setVisibility(view.VISIBLE);
            imgPrev3.setVisibility(view.VISIBLE);
            imgPrevZoom3.setVisibility(view.VISIBLE);
            imgPrevSupr3.setVisibility(view.VISIBLE);
            uriImage3=uriImage;
        }else if(card1==false&&card2==true&&card3==true){
            card1=true;
            imgPrev1.setImageBitmap(bitmap);
            bitmap1=bitmap;
            cardPrev1.setVisibility(view.VISIBLE);
            imgPrev1.setVisibility(view.VISIBLE);
            imgPrevZoom1.setVisibility(view.VISIBLE);
            imgPrevSupr1.setVisibility(view.VISIBLE);
            uriImage1=uriImage;
        }else if(card1==true&&card2==false&&card3==true){
            card2=true;
            imgPrev2.setImageBitmap(bitmap);
            bitmap2=bitmap;
            cardPrev2.setVisibility(view.VISIBLE);
            imgPrev2.setVisibility(view.VISIBLE);
            imgPrevZoom2.setVisibility(view.VISIBLE);
            imgPrevSupr2.setVisibility(view.VISIBLE);
            uriImage3=uriImage;
        }else if(card1==false&&card2==false&&card3==true){
            card1=true;
            imgPrev1.setImageBitmap(bitmap);
            bitmap1=bitmap;
            cardPrev1.setVisibility(view.VISIBLE);
            imgPrev1.setVisibility(view.VISIBLE);
            imgPrevZoom1.setVisibility(view.VISIBLE);
            imgPrevSupr1.setVisibility(view.VISIBLE);
            uriImage1=uriImage;
        }

        //BitmapDrawable ob = new BitmapDrawable(getResources(), bitmap);
        //imagePreview.setBackground(null);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    public void uploadImage(View view) {
        StorageReference filepath1=nStorage1.child("images/").child(uriImage1.getLastPathSegment());
        StorageReference filepath2=nStorage2.child("images/").child(uriImage2.getLastPathSegment());
        StorageReference filepath3=nStorage3.child("images/").child(uriImage3.getLastPathSegment());

        filepath1.putFile(uriImage1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            }
        });
        filepath2.putFile(uriImage2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            }
        });
        filepath3.putFile(uriImage3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            }
        });
        Toast.makeText(this, "Imagenes Subidas Correctamente", Toast.LENGTH_SHORT).show();
    }
}
