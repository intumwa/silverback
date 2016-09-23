package com.intumwayase.android.silverback;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONException;

/**
 * Created by intumwa on 9/23/16.
 */
public class SilverbackFragment extends Fragment {

    ListView listView;

    private String[] titles;
    private String[] imageUrls;

    public SilverbackFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        listView = (ListView) rootView.findViewById(R.id.listview_silverback);

        return rootView;
    }

    private void populateScreen() {
        SilverbackDataTask silverbackDataTask = new SilverbackDataTask();
        silverbackDataTask.execute();
    }

    @Override
    public void onStart() {
        super.onStart();
        populateScreen();
    }

    public class SilverbackDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... args) {

            SilverbackDataRetrieveTask dataTask;
            SilverbackDataHelper silverbackData;
            String silverbackJsonData;
            String[] silverbackDataString = null;

            dataTask = new SilverbackDataRetrieveTask();
            silverbackJsonData = dataTask.connect();


            silverbackData = new SilverbackDataHelper(silverbackJsonData);

            try {

                silverbackData.processSilverBackData();
                titles = silverbackData.titles;
                imageUrls = silverbackData.imageUrls;

                return silverbackDataString;

            } catch (JSONException e) {

                e.printStackTrace();

                return silverbackDataString;

            }

        }

        @Override
        protected void onPostExecute(String[] result) {

            CustomListAdapter adapter=new CustomListAdapter(getActivity(), titles, imageUrls);
            listView.setAdapter(adapter);

        }
    }
}
