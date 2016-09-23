package com.intumwayase.android.silverback;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by intumwa on 9/23/16.
 */
public class CustomListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private String[] titles;
    private String[] imageUrls;
    private Bitmap bitmap;
    private ImageView imageView;

    public CustomListAdapter(Activity context, String[] titles, String[] imageUrls) {
        super(context, R.layout.list_item_silverback, titles);

        this.context=context;
        this.titles = titles;
        this.imageUrls = imageUrls;
    }

    public View getView(int position, View view, ViewGroup container) {

        LayoutInflater inflater=context.getLayoutInflater();
        View rootView=inflater.inflate(R.layout.list_item_silverback, container, false);

        TextView title = (TextView) rootView.findViewById(R.id.list_item_silverback_textview);
        imageView = (ImageView) rootView.findViewById(R.id.list_tem_silverback_image);

        title.setText(titles[position]);

        String imgUrl = "{\"thumbnails\":" + imageUrls[position] + "}";
        String[] thumbs = null;

        try {

            JSONObject imgJson = new JSONObject(imgUrl);
            JSONArray thumbsArray = imgJson.getJSONArray("thumbnails");
            thumbs = new String[thumbsArray.length()];

            for(int i = 0; i < thumbsArray.length(); i++) {
                thumbs[i] = thumbsArray.getString(i);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        BitmapTask bitmapTask = new BitmapTask();
        bitmapTask.execute(thumbs[0]);

        return rootView;

    }

    public class BitmapTask extends AsyncTask<String, Bitmap, InputStream> {

        @Override
        protected InputStream doInBackground(String... params) {

            String imgUrl = params[0];

            try {

                URL url = new URL(imgUrl);


                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {

                    InputStream in = conn.getInputStream();

                    Log.v("SilverBack", "Response: " + in);

                    return in;

                }
                else {
                    Log.v("SilverBack", "Response code: " + conn.getResponseCode());
                    return null;
                }
            }
            catch(Exception e){
                Log.v("SilverBack", "Response code: " + e.getMessage());
                return null;
            }

        }

        @Override
        protected void onPostExecute(InputStream result) {

            Log.v("SilverBack", "Result: " + result);

            bitmap = BitmapFactory.decodeStream(result);

            Log.v("SilverBack", "Bitmap: " + bitmap);

            imageView.setImageBitmap(bitmap);

        }
    }
}
