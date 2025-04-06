package com.example.omdb.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.omdb.model.OMDbModel;
import com.example.omdb.utils.ApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class OMDbViewModel extends ViewModel {
    private final MutableLiveData<List<OMDbModel>> OMDbData = new MutableLiveData<>();
    private final MutableLiveData<OMDbModel> OMDbDataFilter = new MutableLiveData<>();

    public LiveData<OMDbModel> getOMDbDataFilter() {
        return OMDbDataFilter;
    }
    public LiveData<List<OMDbModel>> getOMDbData() {
        return OMDbData;
    }
    public void Refresh(String movie) {
        String urlString = "https://www.omdbapi.com/?apikey=1b1801f7&s=" + movie;

        ApiClient.get(urlString, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("APIRequest", "Request Failed", e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                assert response.body() != null;
                String responseOMDb = response.body().string();

                try {
                    JSONObject jsonOMDb = new JSONObject(responseOMDb);
                    JSONArray searchArray = jsonOMDb.getJSONArray("Search");

                    List<OMDbModel> results = new ArrayList<>();
                    for (int i = 0; i < searchArray.length(); i++) {
                        JSONObject result = searchArray.getJSONObject(i);

                        OMDbModel model = new OMDbModel();

                        model.setImdbID(result.getString("imdbID"));
                        model.setTitle(result.getString("Title"));
                        model.setYear(result.getString("Year"));
                        model.setGender(result.getString("Type"));
                        model.setUrlImg(result.getString("Poster"));
                        results.add(model);
                    }

                    OMDbData.postValue(results);
                } catch (JSONException ex) {
                    Log.e("APIRequest", "Parsing Failed", ex);
                }
            }
        });
    }

    public void GetMovieByID(String IMDbID) {
        String url = "https://www.omdbapi.com/?apikey=1b1801f7&i=" + IMDbID;

        ApiClient.get(url, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("APIRequest", "Request Failed", e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                assert response.body() != null;
                String responseOMDb = response.body().string();

                try {
                    JSONObject jsonOMDb = new JSONObject(responseOMDb);
                    OMDbModel model = new OMDbModel();

                    model.setActors(jsonOMDb.getString("Actors"));
                    model.setDirector(jsonOMDb.getString("Director"));
                    model.setGender(jsonOMDb.getString("Genre"));
                    model.setLengthMovie(jsonOMDb.getString("Runtime"));
                    model.setRate(jsonOMDb.getString("imdbRating"));
                    model.setSynopsis(jsonOMDb.getString("Plot"));
                    model.setTitle(jsonOMDb.getString("Title"));
                    model.setType(jsonOMDb.getString("Type"));
                    model.setUrlImg(jsonOMDb.getString("Poster"));
                    model.setYear(jsonOMDb.getString("Year"));

                    OMDbDataFilter.postValue(model);

                } catch (JSONException ex) {
                    Log.e("APIRequest", "Parsing Failed", ex);
                }
            }
        });
    }
}
