package com.maintrineggmail.nytimesviewer.model.realm;


import com.maintrineggmail.nytimesviewer.model.model.NYTimesModel;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmCash {
    private static RealmConfiguration configCash;
    private Realm mRealm = Realm.getInstance(getConfig());

    public RealmConfiguration getConfig() {
        configCash = new RealmConfiguration.Builder()
                .name("cash3.realm")
                .build();
        Realm.setDefaultConfiguration(configCash);
        return configCash;
    }

    public void writeNYTmodel(final NYTimesModel model) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(model);
            }
        });
        closeDB();
    }

    public NYTimesModel readModels(final long id) {
        return mRealm.where(NYTimesModel.class).equalTo("id", id).findFirst();
    }

    public NYTimesModel getCopy(NYTimesModel origin) {
        return mRealm.copyFromRealm(origin);
    }

    public void cleanCash() {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });
        closeDB();
    }

    public void closeDB() {
        mRealm.close();
    }
}
