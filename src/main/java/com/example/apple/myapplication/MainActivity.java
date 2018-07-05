package com.example.apple.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.exception.ApolloException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import javax.annotation.Nonnull;

import api.ClientQuery;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        String url = "https://data.heavyset16.hasura-app.io/v1alpha1/graphql";

        try {
            OkHttpClient client = new OkHttpClient();


            Request request = new Request.Builder()
                    .url(url)

                    .addHeader("Authorization", "Bearer 16f79f90057d840dbfa6076eb352714cea6aa709fd0b61d3")
                    .addHeader("X-Hasura-Role", "admin")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {

                }

                @Override
                public void onResponse(okhttp3.Call call, Response response) throws IOException {
                    Log.e("graphql","sucess");
                }
            });


            ApolloClient apolloClient = ApolloClient.builder()
                    .serverUrl("https://data.heavyset16.hasura-app.io/v1alpha1/")
                    .okHttpClient(client).build();
            ClientQuery clientQuery = ClientQuery.builder().limit(1).build();
            ApolloCall<ClientQuery.Data> clientCall = apolloClient
                    .query(clientQuery);
            clientCall.enqueue(new ApolloCall.Callback<ClientQuery.Data>() {
                @Override
                public void onResponse(@Nonnull com.apollographql.apollo.api.Response<ClientQuery.Data> response) {

                }

                @Override
                public void onFailure(@Nonnull ApolloException e) {
                   Log.e("graphql",e.getMessage());
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
