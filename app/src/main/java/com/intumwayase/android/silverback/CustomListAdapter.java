package com.intumwayase.android.silverback;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by intumwa on 9/23/16.
 */
public class CustomListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] titles;
    private final String[] imageUrls;

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
        ImageView imageView = (ImageView) rootView.findViewById(R.id.list_tem_silverback_image);

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

        Bitmap bitmap = null;
        BitmapHelper bitmapHelper;

        try {

            bitmapHelper = new BitmapHelper();
            bitmap = bitmapHelper.getBitmap(thumbs[0]);
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        imageView.setImageBitmap(bitmap);

        return rootView;

    }
}
