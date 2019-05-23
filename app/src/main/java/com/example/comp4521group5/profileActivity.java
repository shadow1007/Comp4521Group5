package com.example.comp4521group5;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class profileActivity extends AppCompatActivity {
    private LinearLayout LL;
    private ImageView imageView;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Initialization
        LL = (LinearLayout) this.findViewById(R.id.LL);
        imageView = (ImageView) findViewById(R.id.userIcon);
        Button Album = (Button) findViewById(R.id.AlbumButton);





        //AlbumButton listener
        Album.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Read picture
                Intent intent = new Intent();

                //Open the Pictures screen and set it to image.
                intent.setType("image/*");

                // Use Intent.ACTION_GET_CONTENT this action
                intent.setAction(Intent.ACTION_GET_CONTENT);

                //Return to this screen after taking the photo
                startActivityForResult(intent, 0);
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:  //Crop after taking the picture
                    Uri uri = data.getData();
                    if (uri != null) {
                        doCropPhoto(uri);
                    }
                    break;
                case 1:  //The cropped image is updated to ImageView
                     // Release the image on the ImageView first
                    if (imageView.getDrawable() != null) {
                        imageView.setImageBitmap(null);
                        System.gc();
                    }
                    //Update the ImageView
                    Bitmap bitmap = data.getParcelableExtra("data");
                    imageView.setImageBitmap(bitmap);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void doCropPhoto(Uri uri) {
        //Photo cropping
        Intent intent = getCropImageIntent(uri);
        startActivityForResult(intent, 1);
    }

    //Crop the Intent settings of the image
    public static Intent getCropImageIntent(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");// crop=true for calling to crop the page
        intent.putExtra("scale", true); //Let the crop box support zoom
        intent.putExtra("aspectX", 1);// The ratio of these two to the crop box.
        intent.putExtra("aspectY", 1);// x:y=1:1
        intent.putExtra("outputX", 500);//Return photo ratio X
        intent.putExtra("outputY", 500);//Return photo ratio Y
        intent.putExtra("return-data", true);
        return intent;
    }

    @Override
    protected void onDestroy() {        //Free memory
        try {
            super.onDestroy();
            //Release the entire interface and image
            imageView.setImageBitmap(null);
            imageView = null;
            LL.removeAllViews();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("text", "New_DISS_Main.onDestroy()=" + e.toString());
        }
    }
}