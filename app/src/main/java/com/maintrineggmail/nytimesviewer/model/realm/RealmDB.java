package com.maintrineggmail.nytimesviewer.model.realm;


import android.support.annotation.NonNull;

import com.maintrineggmail.nytimesviewer.model.model.Result;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class RealmDB {
    private static RealmConfiguration configDB;
    private Realm mRealm = Realm.getInstance(getConfig());
    private RealmResults<Result> realmResults;

    public RealmConfiguration getConfig() {
        configDB = new RealmConfiguration.Builder()
                .name("database4.realm")
                .build();
        Realm.setDefaultConfiguration(configDB);
        return configDB;
    }

    public void deleteResult(final int position) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realmResults = mRealm.where(Result.class).findAll();
                realmResults.deleteFromRealm(position);
            }
        });
        closeDB();
    }

    public RealmResults<Result> readResuls() {
        return mRealm.where(Result.class).findAll();
    }

    public void writeResult(final Result result) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.insert(result);
            }
        });
        closeDB();
    }

    public void closeDB() {
        mRealm.close();
    }
}
