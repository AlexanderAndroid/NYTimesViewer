package com.maintrineggmail.nytimesviewer.controllers.screenList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maintrineggmail.nytimesviewer.R;
import com.maintrineggmail.nytimesviewer.controllers.screenDetailing.PagerActivity;
import com.maintrineggmail.nytimesviewer.model.api.Client;
import com.maintrineggmail.nytimesviewer.model.model.NYTimesModel;
import com.maintrineggmail.nytimesviewer.model.model.Result;
import com.maintrineggmail.nytimesviewer.model.realm.RealmCash;
import com.maintrineggmail.nytimesviewer.model.realm.RealmDB;

import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RVFragment extends android.support.v4.app.Fragment implements NYTimesAdapter.
        AdapterClickListenerIF {

    private NYTimesModel mNYTimesModel;
    private NYTimesAdapter mNYTimesAdapter;
    private RealmList<Result> mResults;
    private static final String TAG = "RVFragment";
    private static final String API_KEY = "9a0cf10fad584040a5de5d856b7ae777";
    private static final String ARG_SECTION_NUMBER = "section_number";
    private RecyclerView recyclerView;
    private int position;

    public static RVFragment newInstance(int sectionNumber) {
        RVFragment fragment = new RVFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rv_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.id_fragment_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        position = getArguments().getInt(ARG_SECTION_NUMBER);
        Log.d(TAG, String.valueOf(position));

        if (position < 3) {
            mResults = new RealmList<>();
            mNYTimesAdapter = new NYTimesAdapter(mResults, this);
            recyclerView.setAdapter(mNYTimesAdapter);
            getResponce();
        } else if (position == 3) {
            mNYTimesAdapter = new NYTimesAdapter(new RealmDB().readResuls(), this);
            recyclerView.setAdapter(mNYTimesAdapter);
        }
        return view;
    }

    private void getResponce() {
        String link = null;
        switch (position) {
            case 0:
                link = "mostemailed";
                break;
            case 1:
                link = "mostshared";
                break;
            case 2:
                link = "mostviewed";
                break;
        }
        Call<NYTimesModel> call = Client.getApi().getNYTimesModel(link, API_KEY);
        call.enqueue(new Callback<NYTimesModel>() {
            @Override
            public void onResponse(Call<NYTimesModel> call, Response<NYTimesModel> response) {
                if (response.isSuccessful()) {
                    mNYTimesModel = response.body();
                    mResults.addAll(mNYTimesModel.getResults());
                    recyclerView.getAdapter().notifyDataSetChanged();
                    new RealmCash().writeNYTmodel(mNYTimesModel);
                } else {
                    Log.d(TAG, "response.isSuccessful(false)");
                }
            }

            @Override
            public void onFailure(Call<NYTimesModel> call, Throwable t) {
                Log.w(TAG, t.toString());
            }
        });
    }

    @Override
    public void onClick(int position) {
        if (mNYTimesModel != null) {
            Intent intent = PagerActivity.newIntent(getActivity(), mNYTimesModel.getId(), position);
            startActivity(intent);
        }
    }
}


