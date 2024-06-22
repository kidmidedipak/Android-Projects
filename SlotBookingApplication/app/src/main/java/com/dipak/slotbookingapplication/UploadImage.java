package com.dipak.slotbookingapplication;



import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dipak.slotbookingapplication.Database.DatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadImage extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private ImageView imageView,dbimg;
    private DatabaseHelper databaseHelper;
    EditText getimgeId,getImgUrl;
    String imageId="2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        imageView = findViewById(R.id.imageView);

        dbimg = findViewById(R.id.dbimg);
        getimgeId = findViewById(R.id.getimgeId);
        getImgUrl = findViewById(R.id.getImgUrl);
        Button btnChoose = findViewById(R.id.btnChoose);
        Button dbbtn = findViewById(R.id.dbbtn);
        Button btnAddImgFormUrl = findViewById(R.id.btnAddImgFormUrl);

        databaseHelper = new DatabaseHelper(this);

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
//                Intent intent=new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("http://www.javatpoint.com"));
//                startActivity(intent);
            }
        });

        dbbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageId=getimgeId.getText().toString();
                if(imageId.equals("") || imageId==null)
                {
                    imageId="2";
                }
                displayImageFromDatabase();
            }
        });

        btnAddImgFormUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imageUrl = getImgUrl.getText().toString().trim();
                new FetchImageTask().execute(imageUrl);
            }
        });
    }

    private class FetchImageTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {
            String imageUrl = urls[0];
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);

                // Convert bitmap to byte array
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] imageData = baos.toByteArray();

                // Save image data to the database
                saveImageData(imageData);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);

                // Convert bitmap to byte array
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] imageData = baos.toByteArray();

                // Save image data to the database
                saveImageData(imageData);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveImageData(byte[] imageData) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_IMAGE, imageData);
        db.insert(DatabaseHelper.TABLE_NAME, null, values);
        db.close();
    }

    private Bitmap retrieveImageData() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, new String[]{DatabaseHelper.COLUMN_IMAGE},
                "id=?", new String[]{imageId}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            byte[] imageData = cursor.getBlob(cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE));
            cursor.close();
            db.close();
            return BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
        }else{
            Toast.makeText(this, "Image not found", Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    // Use this method to display the image from the database
    private void displayImageFromDatabase() {
        Bitmap imageBitmap = retrieveImageData();
        if (imageBitmap != null) {
            dbimg.setImageBitmap(imageBitmap);
        }
    }
}
