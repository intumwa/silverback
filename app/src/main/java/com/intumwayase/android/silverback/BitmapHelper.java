package com.intumwayase.android.silverback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by intumwa on 9/23/16.
 */
public class BitmapHelper {

    public Bitmap getBitmap(String imageUrl) throws IOException {

        URL url = new URL(imageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setDoInput(true);
        connection.connect();

        InputStream input = connection.getInputStream();
        Bitmap bitmap = BitmapFactory.decodeStream(input);

        return bitmap;
    }

}
