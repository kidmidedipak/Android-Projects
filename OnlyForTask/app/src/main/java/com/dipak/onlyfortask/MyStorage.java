package com.dipak.onlyfortask;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import com.google.android.material.button.MaterialButton;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyStorage extends AppCompatActivity {

    AppCompatEditText edFileName, edFileContent, edUpdateFile;
    MaterialButton btnRead, btnSave, btnUpdateRead, btnUpdate, btnDelete;
    AppCompatTextView tvFileContent;
    private ActivityResultLauncher<Intent> createFileLauncher, readFileLauncher, deleteFileLauncher;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_storage);

        btnSave = findViewById(R.id.btnSave);
        btnRead = findViewById(R.id.btnRead);
        btnUpdateRead = findViewById(R.id.btnUpdateRead);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        edFileName = findViewById(R.id.edFileName);
        edFileContent = findViewById(R.id.edFileContent);
        edUpdateFile = findViewById(R.id.edUpdateFile);
        tvFileContent = findViewById(R.id.tvFileContent);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createFile();
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readFile();
            }
        });

        btnUpdateRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readFile();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateFile(uri, edUpdateFile.getText().toString());
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {
                deleteFile( );
            }
        });

        createFileLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null) {
                                Uri uri = data.getData();
                                if (uri != null) {
                                    createFile(uri);
                                    Log.i("createFileLauncher", uri + "");
                                }
                            }
                        }
                    }
                });

        deleteFileLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.R)
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null) {
                                uri = data.getData();
                                if (uri != null) {
                                    deleteFile(uri);
                                    Log.i("deleteFileLauncher ", uri + "");
                                }
                            }
                        }
                    }
                });

        readFileLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null) {
                                uri = data.getData();
                                if (uri != null) {
                                    String fileContent = readFile(uri);
                                    tvFileContent.setText(fileContent);
                                    edUpdateFile.setText(fileContent);
                                    Log.i("readFileLauncher ", uri + "");
                                }
                            }
                        }
                    }
                });

    }




    private void deleteFile() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/*");
        deleteFileLauncher.launch(intent);

    }


    private void deleteFile(Uri uri) {
        Cursor cursor = null;
        try {
            cursor =  getContentResolver().query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                DocumentsContract.deleteDocument( getContentResolver(), uri);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    private void updateFile(Uri uri, String content) {
        try {
            ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "rwt");
            if (parcelFileDescriptor != null) {
                FileOutputStream fileOutputStream = new FileOutputStream(parcelFileDescriptor.getFileDescriptor());
                fileOutputStream.write(content.getBytes());
                fileOutputStream.close();
                parcelFileDescriptor.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFile() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/*");
        readFileLauncher.launch(intent);
    }

    private String readFile(Uri uri) {
        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(getContentResolver().openInputStream(uri)));
            StringBuilder line = new StringBuilder();
            String l;
            while ((l = bufferedReader.readLine()) != null) {
                line.append(l);
            }
            bufferedReader.close();
            return line.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private void createFile() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, edFileName.getText().toString());
        createFileLauncher.launch(intent);
    }

    private void createFile(Uri uri) {
        try {
            ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "w");
            FileOutputStream fileOutputStream = new FileOutputStream(parcelFileDescriptor.getFileDescriptor());
            fileOutputStream.write(edFileContent.getText().toString().getBytes());
            fileOutputStream.close();
            parcelFileDescriptor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}