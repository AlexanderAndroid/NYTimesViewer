package com.maintrineggmail.nytimesviewer.controllers.screenDetailing;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maintrineggmail.nytimesviewer.R;
import com.maintrineggmail.nytimesviewer.model.model.NYTimesModel;
import com.maintrineggmail.nytimesviewer.model.model.Result;
import com.maintrineggmail.nytimesviewer.model.picasso.DownloadPicture;
import com.maintrineggmail.nytimesviewer.model.realm.RealmCash;
import com.maintrineggmail.nytimesviewer.model.realm.RealmDB;

public class PagerFragment extends Fragment {

    private static final String ARG_ID_MODEL = "id_model";
    private static final String ARG_POSITION_RV = "position_rv";
    private static final String TAG = "PagerFragment";
    private Result resultCopy;
    private int position;
    private long id;
    private Bitmap mBitmap;


    public static PagerFragment newInstance(long id, int position) {
        Bundle args = new Bundle();
        args.putLong(ARG_ID_MODEL, id);
        args.putInt(ARG_POSITION_RV, position);
        PagerFragment fragment = new PagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION_RV);
        id = getArguments().getLong(ARG_ID_MODEL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pager, container, false);
        TextView tvAbstractPager = v.findViewById(R.id.tv_abstract_pager);
        TextView tvTitlePager = v.findViewById(R.id.tv_title_pager);
        ImageView ivPhotoPager = v.findViewById(R.id.iv_photo_pager);

        RealmCash realmCash = new RealmCash();
        NYTimesModel NYTimesModel = realmCash.readModels(id);
        Result result = NYTimesModel.getResults().get(position);
        resultCopy = realmCash.getCopy(NYTimesModel).getResults().get(position);
        tvTitlePager.setText(result.getTitle());
        tvAbstractPager.setText(result.getAbstract());

        mBitmap = new DownloadPicture().downloadPicture(result, getContext());
        if (mBitmap != null) {
            ivPhotoPager.setImageBitmap(mBitmap);
        } else {
            ivPhotoPager.setImageResource(R.drawable.nyt_blue);
        }
        savePhoto(v);

        return v;
    }

    private void savePhoto(View view) {
        view.findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new downloadPhoto().execute();
            }
        });
    }

    private class downloadPhoto extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            if (!resultCopy.getMediaList().isEmpty()) {
                resultCopy.getMediaList().get(0).setPicture(new DownloadPicture().downloadPicture(mBitmap));
            }
            new RealmDB().writeResult(resultCopy);
            return null;
        }
    }
}
