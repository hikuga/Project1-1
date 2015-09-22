package com.example.hitesh.project1_1;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.squareup.picasso.Picasso;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
   // private ImageView mImageView;
    private TableLayout mLayout;
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        //mImageView = (ImageView) v.findViewById(R.id.imageButton );
        mLayout = (TableLayout) v.findViewById(R.id.tableLayout);
        DownloadWebpageTask downloadTask = new DownloadWebpageTask();
       // downloadTask.ImageDownloader((ImageView) temp);
        downloadTask.execute("no_param");

        return v;
    }

    public void uploadImg(MovieDetails movieData){
        ArrayList<Movie> currentMovies = movieData.getMoviesListLookup().get("default");
        Log.e("MovieFragment", "This shoudl have updated Image view with -->" + currentMovies.get(0).getImageUrl());
        //for( String url : urls)
        //final String[] urlArray = urls.toArray(new String[urls.size()]);
        for (int i = 0; i < mLayout.getChildCount(); i++) {
            View parentRow = mLayout.getChildAt(i);
            if (parentRow instanceof TableRow) {
                for (int j = 0; j < ((TableRow) parentRow).getChildCount(); j++) {
                    final Movie text = currentMovies.get(i * 2 + j);
                    ImageView imv = (ImageView) ((TableRow) parentRow).getChildAt(j);
                    if (imv instanceof ImageButton) {
                        ((ImageButton) imv).setId(2 * i + j);
                        ((ImageButton) imv).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(), DetailActivity.class);
                                //EditText editText = (EditText) findViewById(R.id.edit_message);
                                //String message = editText.getText().toString();
                                intent.putExtra(Intent.EXTRA_TEXT, text);
                                intent.putExtra("movieinfo", text);
                                startActivity(intent);
                                //Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
                            }
                        });
                        Picasso.with(getActivity())
                                 .load( currentMovies.get(i*2 + j).getImageUrl() )
                                .resize(625, 500)
                                .into(imv);
                        }
                }
            }
        }
    }


    private class DownloadWebpageTask extends AsyncTask<String, Void, MovieDetails> {
        private final String LOG_TAG = DownloadWebpageTask.class.getSimpleName();
        //ImageView mImageView;

       // public void ImageDownloader(ImageView imageView) {
       //     mImageView = imageView;
       // }

        public String[] getImgUrls(String responseData)
                throws JSONException{

            JSONObject respJson = new JSONObject(responseData);
            JSONArray movies = respJson.getJSONArray("results");
            String[] imgurls = new String[movies.length()];
            for(int i=0; i < movies.length(); ++i){
                imgurls[i] = "http://image.tmdb.org/t/p/w185//"+movies.getJSONObject(i).getString("backdrop_path");

            }
            return imgurls;

        }

        @Override
        protected MovieDetails doInBackground(String... params)
         {
            // If there's no zip code, there's nothing to look up.  Verify size of params.
            if (params.length == 0) {
                return null;
            }

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

            String format = "json";
            String units = "metric";
            int numDays = 7;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                final String FORECAST_BASE_URL =
                        "http://api.openweathermap.org/data/2.5/forecast/daily?";
                final String QUERY_PARAM = "q";
                final String FORMAT_PARAM = "mode";
                final String UNITS_PARAM = "units";
                final String DAYS_PARAM = "cnt";

                final String MOVIE_BASE_URL =
                        "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=08158a0a95f48cf8eb04e0b22571a8d0";


                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(QUERY_PARAM, params[0])
                        .appendQueryParameter(FORMAT_PARAM, format)
                        .appendQueryParameter(UNITS_PARAM, units)
                        .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
                        .build();

                //URL url = new URL(builtUri.toString());
                URL url = new URL(MOVIE_BASE_URL);

                Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                MovieDetails movieData = new MovieDetails(buffer.toString(), buffer.toString(), buffer.toString());
                forecastJsonStr = buffer.toString();
                Log.i(LOG_TAG, "Response = "+forecastJsonStr);
                String [] results = getImgUrls(forecastJsonStr);
                for(String imgUrl : results ){
                    Log.e(LOG_TAG, "imageurl -->"+imgUrl);
                }
                Log.e(LOG_TAG, "returning -->"+results[0]);

                List<String> stringList = new ArrayList<String>(Arrays.asList(results));
                return movieData;
            }
            catch(org.json.JSONException ee){
                return null;
            }
            catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            //return null;

        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(MovieDetails movieData) {
            //Log.i(LOG_TAG, "onPostExecute result -->" + results.get(0));

//Loading image from below url into imageView

           uploadImg( movieData );
        }


    }
}
