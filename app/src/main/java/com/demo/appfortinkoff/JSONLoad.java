package com.demo.appfortinkoff;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class JSONLoad {
    private static String webUrl = "https://developerslife.ru/%s/%s?json=true";

    private static GifToBeShown getGifWithChapterPageId(String chapter, int page, int id) {

        GifToBeShown gifToBeShownToReturned = null;

        DownloadGifJSON loader = new DownloadGifJSON();
        String finalUrl = String.format(webUrl, chapter, page);

        try {
            JSONObject object = loader.execute(finalUrl).get();
            if (object != null) {
                String description;
                String url;

                JSONObject result = object.getJSONArray("result").getJSONObject(id);
                description = result.getString("description");
                url = result.getString("gifURL");

                gifToBeShownToReturned = new GifToBeShown(description, url);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return gifToBeShownToReturned;
    }

    public static GifToBeShown getRandomGif(Chapters chapter) {

        String chapterName = chapter.getName();
        int rndPage = (int) (Math.random() * 2664);
        int rndId = (int) (Math.random() * 5);

        return getGifWithChapterPageId(chapterName, rndPage, rndId);
    }

    private static class DownloadGifJSON extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... strings) {
            StringBuilder sb = new StringBuilder();
            HttpsURLConnection urlConnection = null;
            try {
                URL url = new URL(strings[0]);
                urlConnection = (HttpsURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String str = bufferedReader.readLine();
                while (str != null) {
                    sb.append(str);
                    str = bufferedReader.readLine();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            try {
                JSONObject obj = new JSONObject(sb.toString());
                return obj;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }


    }
}
