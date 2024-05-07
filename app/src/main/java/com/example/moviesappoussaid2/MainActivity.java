package com.example.moviesappoussaid2;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText searchEditText;
    private Button searchButton;
    private RecyclerView movieRecyclerView;
    private MovieAdapter movieAdapter;

    private final String TMDB_API_KEY = "b89782f16822f7998ade6775a10b8a24"; // Replace with your TMDB API key
    private final String TMDB_API_URL = "https://api.themoviedb.org/3/search/movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEditText = findViewById(R.id.search_editText);
        searchButton = findViewById(R.id.search_button);
        movieRecyclerView = findViewById(R.id.movie_recycler_view);

        movieAdapter = new MovieAdapter(this);
        movieRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        movieRecyclerView.setAdapter(movieAdapter);

        searchButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String query = searchEditText.getText().toString().trim();
        if (!query.isEmpty()) {
            new FetchMoviesTask().execute(buildUrl(query));
        }
    }

    private String buildUrl(String query) {
        StringBuilder urlBuilder = new StringBuilder(TMDB_API_URL);
        urlBuilder.append("?api_key=" + TMDB_API_KEY);
        urlBuilder.append("&query=" + Uri.encode(query));
        return urlBuilder.toString();
    }

    private class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(String... urls) {
            String url = urls[0];
            try {
                URL tmdbUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) tmdbUrl.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    // Parse JSON response using a JSON parsing library like GSON
                    return parseJson(response.toString());
                } else {
                    Log.e(MainActivity.class.getSimpleName(), "Error fetching movies: " + responseCode);
                }
            } catch (Exception e) {
                Log.e(MainActivity.class.getSimpleName(), "Error fetching movies: " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            if (movies != null) {
                movieAdapter.setMovies(movies);
            } else {
                // Handle error or empty results
            }
        }

        private List<Movie> parseJson(String json) {
            List<Movie> movies = new ArrayList<>();

            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray resultsArray = jsonObject.getJSONArray("results");

                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject movieObject = resultsArray.getJSONObject(i);

                    String title = movieObject.getString("title");
                    String releaseDate = movieObject.getString("release_date");
                    String overview = movieObject.getString("overview");
                    String imageUrl = movieObject.getString("poster_path"); // Adjust this field based on the actual image URL field in your API response


                    // Create a new Movie object and add it to the list
                    Movie movie = new Movie(title, releaseDate, overview, imageUrl);
                    movies.add(movie);
                }
            } catch (JSONException e) {
                Log.e(MainActivity.class.getSimpleName(), "Error parsing JSON: " + e.getMessage());
            }

            return movies;
        }

    }
}
