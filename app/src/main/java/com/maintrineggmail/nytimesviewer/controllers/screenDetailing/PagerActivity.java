package com.maintrineggmail.nytimesviewer.controllers.screenDetailing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.maintrineggmail.nytimesviewer.R;
import com.maintrineggmail.nytimesviewer.model.model.Result;
import com.maintrineggmail.nytimesviewer.model.realm.RealmCash;

import io.realm.RealmList;

public class PagerActivity extends AppCompatActivity {

    private static final String EXTRA_POSITION_RV =
            "com.maintrineggmail.nytimesviewer.controllers.position";
    private static final String EXTRA_MODEL = "model";
    private RealmList<Result> mResultRealmList;
    private long id;


    public static Intent newIntent(Context packageContext, long id, int position) {
        Intent intent = new Intent(packageContext, PagerActivity.class);
        intent.putExtra(EXTRA_MODEL, id);
        intent.putExtra(EXTRA_POSITION_RV, position);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        id = getIntent().getLongExtra(EXTRA_MODEL,0);
        mResultRealmList = new RealmCash().readModels(id).getResults();

        int position = getIntent().getIntExtra(EXTRA_POSITION_RV, 0);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                mResultRealmList.get(position);
                return PagerFragment.newInstance(id,position);
            }

            @Override
            public int getCount() {
                return mResultRealmList.size();
            }
        });

        viewPager.setCurrentItem(position);
    }

}


