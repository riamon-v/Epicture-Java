package com.example.riamon_v.java_epicture_2017.AddActuality;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.riamon_v.java_epicture_2017.Api.Imgur.ImgurModel.AllObjects;
import com.example.riamon_v.java_epicture_2017.Api.Imgur.Services.EditImageService;
import com.example.riamon_v.java_epicture_2017.DatabaseManagment.DatabaseHandler;
import com.example.riamon_v.java_epicture_2017.DatabaseManagment.User;
import com.example.riamon_v.java_epicture_2017.Api.Imgur.ImgurModel.UploadObject;
import com.example.riamon_v.java_epicture_2017.Api.Imgur.Services.UploadService;
import com.example.riamon_v.java_epicture_2017.ListManagment.CardClass;
import com.example.riamon_v.java_epicture_2017.MainActivity;
import com.example.riamon_v.java_epicture_2017.R;
import com.example.riamon_v.java_epicture_2017.SignLoginHandling.ImgurLoginActivity;
import com.example.riamon_v.java_epicture_2017.SignLoginHandling.LoginActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AddActivity extends AppCompatActivity {

    public static final int PICK_IMAGE_REQUEST = 1;
    private TextView title;
    private ImageView image;
    private EditText titleText;
    private EditText contentText;
    private Button buttonUpload;
    private User user;
    private Uri uri;

    private CardClass editItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        user = DatabaseHandler.getInstance(getApplicationContext()).getUserDao().getUserById(
                getIntent().getIntExtra("idUser", 0));

        editItem = (CardClass) getIntent().getSerializableExtra("item");

        redirectImgur();
        title = findViewById(R.id.textView);
        title.setText(getIntent().getStringExtra("title"));

        image = findViewById(R.id.imageview);
        titleText = findViewById(R.id.titleText);
        contentText = findViewById(R.id.contentText);
        buttonUpload = findViewById(R.id.buttonUpload);

        checkPerm();

        if (Objects.equals(editItem, null)) {
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chooseFile();
                }
            });
            buttonUpload.setEnabled(false);
        }
        else {
            titleText.setText(editItem.getTitle());
            contentText.setText(editItem.getContent());
            buttonUpload.setText(R.string.EditUpload);
            Picasso.with(getApplicationContext()).load(editItem.getUrl()).into(image);
        }

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadObject upload = new UploadObject();

                if (Objects.equals(editItem, null))
                    upload.image = new File(getRealPathFromURI(getApplicationContext(), uri));
                upload.title = titleText.getText().toString();
                upload.description = contentText.getText().toString();
                if (Objects.equals(editItem, null)) {
                    new UploadService(getApplicationContext(), user).
                            Execute(upload, new UiCallback<AllObjects.ImageResponse>());
                }
                else
                    new EditImageService(getApplicationContext(), user, editItem.getId()).
                            Execute(upload, new UiCallback<AllObjects.ManageImg>());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)  {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                image.setImageBitmap(bitmap);
                buttonUpload.setEnabled(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent;

        intent = new Intent(this, MainActivity.class);
        intent.putExtra("idUser", user.getId());
        startActivity(intent);
    }

    public void redirectImgur() {
        if (user.getTokenImgur() == null) {
            Intent intentImgur;

            intentImgur = new Intent(this, ImgurLoginActivity.class);
            intentImgur.putExtra("userId", user.getId());
            startActivity(intentImgur);
            finish();
        }
    }

    public void chooseFile() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE_REQUEST);
    }

    public void checkPerm() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        }
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void clearInput() {
        titleText.setText("");
        contentText.setText("");
        titleText.clearFocus();
        contentText.clearFocus();
        buttonUpload.setEnabled(false);
        image.setImageResource(R.drawable.ic_image_black_24dp);
    }

    private class UiCallback<T> implements Callback<T> {

        @Override
        public void success(T imageResponse, Response response) {
            if (Objects.equals(editItem, null)) {
                clearInput();
                Snackbar.make(findViewById(R.id.uploadView), "Upload success", Snackbar.LENGTH_SHORT).show();
            }
            else {
                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                mainIntent.putExtra("idUser", user.getId());
                startActivity(mainIntent);
                finish();
            }
        }

        @Override
        public void failure(RetrofitError error) {
            //Assume we have no connection, since error is null
            if (error == null) {
                Log.d("SUCCESS", "false");
                Snackbar.make(findViewById(R.id.uploadView), "No internet connection", Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}
