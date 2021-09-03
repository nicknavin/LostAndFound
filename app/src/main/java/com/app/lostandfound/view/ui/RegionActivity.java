package com.app.lostandfound.view.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;

import com.app.lostandfound.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RegionActivity extends AppCompatActivity {
    PlacesClient placesClient;
    List<AutocompletePrediction>autocompletePredictionsList= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region);
        String apiKey = getString(R.string.api_key);
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        placesClient = Places.createClient(this);
new MyRegion().execute("");




    }









    public class MyRegion extends AsyncTask <String, String, String>
    {


        @Override
        protected String doInBackground(String... strings)
        {
            autocompletePredictionsList=   getAutocomplete();

            return "";
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            for(int i=0;i<autocompletePredictionsList.size();i++) {
                System.out.println("Region : " + autocompletePredictionsList.get(i));
            }


        }
    }

    private List<AutocompletePrediction> getAutocomplete()
    {

        //Create a RectangularBounds object.
        RectangularBounds bounds = RectangularBounds.newInstance(
                new LatLng(-33.880490, 151.184363),
                new LatLng(-33.858754, 151.229596));


        final FindAutocompletePredictionsRequest.Builder requestBuilder =
                FindAutocompletePredictionsRequest.builder()
                        .setQuery("a")
                        .setCountry("") //Use only in specific country
                        // Call either setLocationBias() OR setLocationRestriction().
                        .setLocationBias(bounds)
//                        .setLocationRestriction(bounds)
                        .setSessionToken(AutocompleteSessionToken.newInstance())
                        .setTypeFilter(TypeFilter.REGIONS);

        Task<FindAutocompletePredictionsResponse> results =
                placesClient.findAutocompletePredictions(requestBuilder.build());


        //Wait to get results.
        try {
            Tasks.await(results, 60, TimeUnit.SECONDS);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }

        if (results.isSuccessful()) {
            if (results.getResult() != null) {
                return results.getResult().getAutocompletePredictions();
            }
            return null;
        } else {
            return null;
        }
    }



}
