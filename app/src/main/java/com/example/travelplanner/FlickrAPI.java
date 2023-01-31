package com.example.travelplanner;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class FlickrAPI {
    static final String BASE_URL = "https://api.flickr.com/services/rest";
    static final String API_KEY = "4bfada23cf7be386b75bcf03b3586539";//zmienić tak żeby api_key nie był widoczny jak wrzucę na git
    static final String TAG = "TravelPlannerTag";

    AddTripActivity addActivity;

    public FlickrAPI(AddTripActivity addActivity) {
        this.addActivity = addActivity;
    }

    public void fetchLocationPhotos(String city, String country) {
        String url = constructLocationPhotoListURL(city, country);
        Log.d(TAG,"fetchILocationPhotos: " + url);
        FetchLocationPhotoListAsyncTask asyncTask = new FetchLocationPhotoListAsyncTask();
        asyncTask.execute(url);
    }

    public String constructLocationPhotoListURL(String city, String country) {
        String url = BASE_URL;
        url += "?method=flickr.photos.search";
        url += "&api_key=" + API_KEY;
        url += "&format=json";
        url += "&nojsoncallback=1";
        url += "&tags=" + city + "," + country;
        url += "&tag_mode=all";
        url += "&sort=relevance";
        url += "&content_type=1";
        url += "&is_getty=true";
        url += "&extras=url_c";

        return url;
    }

    class FetchLocationPhotoListAsyncTask extends AsyncTask<String, Void, List<LocationPhoto>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<LocationPhoto> doInBackground(String... strings) {
            String url = strings[0];
            List<LocationPhoto> locationPhotoList = new ArrayList<>();

            try {
                URL urlObject = new URL(url);
                HttpsURLConnection urlConnection = (HttpsURLConnection) urlObject.openConnection();

                String jsonResult = "";
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1) {
                    jsonResult += (char) data;
                    data = reader.read();
                }
                Log.d(TAG,"doInBackground: " + jsonResult);

                JSONObject jsonObject = new JSONObject(jsonResult);
                JSONObject photosObject = jsonObject.getJSONObject("photos");
                JSONArray photoArray = photosObject.getJSONArray("photo");

                for (int i = 0; i < photoArray.length(); i++) {
                    JSONObject singlePhotoObject = photoArray.getJSONObject(i);

                    LocationPhoto locationPhoto = parseLocationPhoto(singlePhotoObject);

                    if (locationPhoto != null) {
                        locationPhotoList.add(locationPhoto);
                    }
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return locationPhotoList;
        }

        private LocationPhoto parseLocationPhoto(JSONObject singlePhotoObject) {
            LocationPhoto locationPhoto = null;
            try {
                String id = singlePhotoObject.getString("id");
                String photoURL = singlePhotoObject.getString("url_c");
                locationPhoto = new LocationPhoto(id,photoURL);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            return locationPhoto;
        }

        @Override
        protected void onPostExecute(List<LocationPhoto> locationPhotos) {
            super.onPostExecute(locationPhotos);

            Log.d(TAG,"onPostExecute: " + locationPhotos.size());
            addActivity.receivedLocationPhotos(locationPhotos);

        }
    }

}
