package com.maintrineggmail.nytimesviewer.model.api;


import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.maintrineggmail.nytimesviewer.BuildConfig;
import com.maintrineggmail.nytimesviewer.model.model.Medium;
import com.maintrineggmail.nytimesviewer.model.model.Result;

import java.lang.reflect.Type;

import io.realm.Realm;
import io.realm.RealmList;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client extends Application {
    private static ApiService sApiService;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.nytimes.com/svc/mostpopular/v2/")
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .client(httpClient)
                .build();
        sApiService = retrofit.create(ApiService.class);
    }

    private static Gson getGson() {
        return new GsonBuilder().registerTypeAdapter(Result.class, new JsonDeserializer<Result>() {
            @Override
            public Result deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                JsonObject object = json.getAsJsonObject();

                Result result = new Result();

                if (object.get("media").isJsonArray() && !object.get("title").isJsonNull() &&
                        !object.get("abstract").isJsonNull() && !object.get("url").isJsonNull()
                        ) {
                    Gson gson = new Gson();
                    return gson.fromJson(json, Result.class);
                } else {
                    if (!object.get("media").isJsonArray()) {
                        RealmList<Medium> list = new RealmList<>();
                        result.setMediaList(list);
                    }

                    if (!object.get("title").isJsonNull()) {
                        result.setTitle(object.get("title").getAsString());
                    } else {
                        result.setTitle("");
                    }

                    if (!object.get("abstract").isJsonNull()) {
                        result.setAbstract(object.get("abstract").getAsString());
                    } else {
                        result.setAbstract("");
                    }

                    if (!object.get("url").isJsonNull()) {
                        result.setUrl(object.get("url").getAsString());
                    } else {
                        result.setUrl("");
                    }

              }
                return result;

            }
        }).create();
    }

    public static ApiService getApi() {
        return sApiService;
    }


}
