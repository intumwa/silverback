package com.intumwayase.android.silverback;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_item_silverback, null,true);

        TextView title = (TextView) rowView.findViewById(R.id.list_item_silverback_textview);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.list_tem_silverback_image);

        title.setText(titles[position]);


        // App icon will be shown before loading image
        int loader = R.drawable.ic_launcher;

        // ImageLoader class instance
        ImageLoader imgLoader = new ImageLoader(context);

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

        // Loading image from url
        imgLoader.DisplayImage(thumbs[0], loader, imageView);

        return rowView;

    };
}
