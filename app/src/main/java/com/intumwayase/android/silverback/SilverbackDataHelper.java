package com.intumwayase.android.silverback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by intumwa on 9/23/16.
 */
public class SilverbackDataHelper {

    private String data;
    public String[] titles;
    public String[] imageUrls;


    public SilverbackDataHelper(String input) {
        data = "{\"silverback\":" + input + "}";
    }

    public void processSilverBackData() throws JSONException {

        JSONObject jsonData = new JSONObject(data);
        JSONArray jsonDataArray = jsonData.getJSONArray("silverback");

        titles = new String[jsonDataArray.length()];
        imageUrls = new String[jsonDataArray.length()];

        for(int i = 0; i < jsonDataArray.length(); i++) {
            titles[i] = parseTitle(jsonDataArray.getString(i));
            imageUrls[i] = parseImageUrl(jsonDataArray.getString(i));
        }
    }

    public String parseTitle(String item) throws JSONException {

        JSONObject jsonItem = new JSONObject(item);

        return jsonItem.getString("title");

    }

    public String parseImageUrl(String item) throws JSONException {

        JSONObject jsonItem = new JSONObject(item);

        return jsonItem.getString("thumbs");

    }
}
