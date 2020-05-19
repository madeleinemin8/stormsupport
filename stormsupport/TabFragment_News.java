package com.minlabs.stormsupport;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TabFragment_News extends Fragment implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 0;
    List<Alert> myData = new ArrayList<Alert>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    View originalView;
    EditText mSearch;
    Button mSearchButton;
    String locQuery;
    String latlong;
    String searchCoord;
    String latitude;
    String longitude;
    String stormDistance;
    TextView mTextView;
    GoogleApiClient mGoogleApiClient;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        originalView = inflater.inflate(R.layout.tab_fragment_news, container, false);
        mTextView = (TextView) originalView.findViewById(R.id.news_text);
        mSearch = (EditText) originalView.findViewById(R.id.search_bar);
        mGoogleApiClient = new GoogleApiClient.Builder(this.getContext()).addApi(LocationServices.API).build();

        mSearchButton = (Button) originalView.findViewById(R.id.search_button);
        mSearchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mTextView.setText("Finding location...");
                emptyData();
                locQuery = mSearch.getText().toString();
                loadLocation();

            }
        });
        mSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mSearch.setText(locQuery);
                mSearch.setSelection(mSearch.getText().length());
            }
        });
        mRecyclerView = (RecyclerView) originalView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new myAdapter(myData);
        mRecyclerView.setAdapter(mAdapter);
        /*------ latlong = "25.5516,-80.6327";*/
        //refresh();
        //Alert newAlert = new Alert("Flood Warning", "description", "google.com", "vienna, vienna, fairfax", "urgent");
        //myData.add(newAlert);
        return originalView;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_COARSE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // All good!
                } else {
                    Toast.makeText(this.getContext(), "StormSupport needs your location!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }
    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
    @Override
    public void onConnected(Bundle bundle) {
        if (ContextCompat.checkSelfPermission(this.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            String lat = "" + lastLocation.getLatitude();
            String lon = "" + lastLocation.getLongitude();
            latlong = lat + "," + lon;
        }
    }
    @Override
    public void onConnectionSuspended(int i) {

    }

    public void loadAlerts() {
        String loc;
        if(mSearch.getText().toString() != null) {
            loc = searchCoord;
        }
        else
            loc = latlong;
        //Key: 2e43796886b69426ac09106d1b74f950
        String apiKey = "2e43796886b69426ac09106d1b74f950";
        String url = "https://api.darksky.net/forecast/" + apiKey + "/" + loc;
        final String[] titles = new String[12];
        final String[] descriptions = new String[12];
        final String[] urls = new String[12];
        final String[] severity = new String[12];
        final String[] locations = new String [12];

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject current = response.getJSONObject("currently");
                            stormDistance = current.getString("nearestStormDistance");
                            mTextView.setText("Nearest storm is " + stormDistance + " miles away.");
                            mTextView.setBackgroundColor(Color.parseColor("#F9FAF9"));
                            JSONArray alertArr = response.getJSONArray("alerts");
                            for (int i = 0; i < alertArr.length(); i++) {
                                JSONObject jsonObject = (JSONObject) alertArr.get(i);
                                titles[i] = jsonObject.getString("title");

                                String loc = jsonObject.getString("regions");
                                loc = loc.replaceAll("\\p{P}"," ");
                                loc = loc.replaceAll("  ", ", ");
                                loc = loc.substring(1, loc.length()-2);
                                locations[i] = loc;
                                descriptions[i] = jsonObject.getString("description");
                                urls[i] = jsonObject.getString("uri");
                                severity[i] = jsonObject.getString("severity");
                                Alert newAlert = new Alert(titles[i], descriptions[i], urls[i], locations[i], severity[i]);
                                myData.add(newAlert);
                                //mAdapter.notifyDataSetChanged();
                            }
                            mAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            mTextView.setText("No severe alerts." + "\n \n" + "Nearest storm is " + stormDistance + " miles away.");
                            mTextView.setBackgroundColor(Color.parseColor("#bbff8e"));
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mTextView.setText("Hmm..we couldn't find anything for that zip code");
                        mTextView.setBackgroundColor(Color.parseColor("#F9FAF9"));
                    }
                });
        Singleton.getInstance(originalView.getContext()).addToRequestQueue(jsObjRequest);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    public void loadLocation() {
        /*----------Geocoding-------------*/
        //SENSE COUNTRY (reverse geocode)
        if (latlong != null && mSearch.getText().toString() == null) {
            String rev_url = " http://nominatim.openstreetmap.org/reverse?format=json&lat=" + latitude + "&lon=" + longitude;
            JsonObjectRequest jsRevRequest = new JsonObjectRequest
                    (Request.Method.GET, rev_url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                latitude = response.getString("lat");
                                longitude = response.getString("lon");
                                latlong = latitude + "," + longitude;
                                searchCoord = latitude + "," + longitude;
                                loadAlerts();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });
            Singleton.getInstance(originalView.getContext()).addToRequestQueue(jsRevRequest);

        } else { //assume country is US
            locQuery = mSearch.getText().toString();
            String geo_url = "http://nominatim.openstreetmap.org/search?format=json&country=US&limit=1&postalcode=" + locQuery;
            JsonArrayRequest jsGeoRequest = new JsonArrayRequest
                    (Request.Method.GET, geo_url, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                JSONObject address = (JSONObject) response.get(0);
                                latitude = address.getString("lat");
                                longitude = address.getString("lon");
                                String[] add_array = address.getString("display_name").split(",");
                                mSearch.setText(add_array[0] + ", "+ add_array[1] + ", " + add_array[2]);
                                latlong = latitude + "," + longitude;
                                searchCoord = latitude + "," + longitude;
                                loadAlerts();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                mTextView.setText("Hmm..we couldn't find anything for that zip code");
                                mTextView.setBackgroundColor(Color.parseColor("#F9FAF9"));
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            mTextView.setText("Hmm..we couldn't find anything for that zip code");
                            mTextView.setBackgroundColor(Color.parseColor("#F9FAF9"));
                        }
                    });
            Singleton.getInstance(originalView.getContext()).addToRequestQueue(jsGeoRequest);
        }
    }
    public void emptyData(){
        int size = myData.size();
        for(int x=0; x<size; x++){
            myData.remove(0);
        }
    }
}

