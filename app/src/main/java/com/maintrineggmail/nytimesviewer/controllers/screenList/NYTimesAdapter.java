package com.maintrineggmail.nytimesviewer.controllers.screenList;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.maintrineggmail.nytimesviewer.R;
import com.maintrineggmail.nytimesviewer.model.model.Result;
import com.maintrineggmail.nytimesviewer.model.picasso.DownloadPicture;
import com.maintrineggmail.nytimesviewer.model.realm.RealmDB;

import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;

public class NYTimesAdapter extends RecyclerView.Adapter<NYTimesAdapter.ViewHolder> implements RealmChangeListener {
    private static final String TAG = "NYTimesAdapter";
    private final AdapterClickListenerIF mAdapterClickListenerIF;
    private RealmResults<Result> mResultRealmList;
    private RealmList<Result> mResultList;

    public NYTimesAdapter(RealmList<Result> results, AdapterClickListenerIF adapterClickListenerIF) {
        mResultList = results;
        if (mResultList != null && mResultList.size() != 0) {
            mResultList.addChangeListener(this);
        }
        mAdapterClickListenerIF = adapterClickListenerIF;
    }

    public NYTimesAdapter(RealmResults<Result> resultRealmList, AdapterClickListenerIF adapterClickListenerIF) {
        mResultRealmList = resultRealmList;
        mResultRealmList.addChangeListener(this);
        mAdapterClickListenerIF = adapterClickListenerIF;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (mResultList != null) {
            holder.btnRemove.setVisibility(View.GONE);
            Result resultCash = mResultList.get(position);
            holder.tvColSecByLine.setText(String.format("%s %s %s", resultCash.getColumn(), resultCash.getSection(), resultCash.getByline()));
            holder.tvTitle.setText(resultCash.getTitle());
            Bitmap bitmap = new DownloadPicture().downloadPicture(resultCash, holder.itemView.getContext());
            if (bitmap != null) {
                holder.mImageView.setImageBitmap(bitmap);
            } else {
                holder.mImageView.setImageResource(R.drawable.nyt_blue);
            }

        } else if (mResultRealmList != null) {
            holder.btnRemove.setVisibility(View.VISIBLE);
            Result resultDB = mResultRealmList.get(position);
            holder.tvColSecByLine.setText(String.format("%s %s %s", resultDB.getTitle(), resultDB.getSection(), resultDB.getByline()));
            holder.tvTitle.setText(resultDB.getTitle());
            holder.tvAbstract.setText(String.format("%s%s", resultDB.getAbstract(), resultDB.getPublishedDate()));
            if (!resultDB.getMediaList().isEmpty()) {
                Bitmap  bitmap = new DownloadPicture().downloadPicture(resultDB.getMediaList().get(0).getPicture());
                holder.mImageView.setImageBitmap(bitmap);
            } else {
                holder.mImageView.setImageResource(R.drawable.nyt_blue);
            }
        }
        delete(holder, position);

    }

    private void delete(ViewHolder holder, final int position) {
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RealmDB().deleteResult(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mResultList != null) {
            return mResultList.size();
        } else if (mResultRealmList != null) {
            return mResultRealmList.size();
        }
        return 0;
    }

    @Override
    public void onChange(Object o) {
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        TextView tvTitle, tvAbstract, tvColSecByLine;
        ImageView mImageView;
        Button btnRemove;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_rv_item, parent, false));
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAbstract = itemView.findViewById(R.id.tv_abstract);
            tvColSecByLine = itemView.findViewById(R.id.tv_Col_Sec_ByLine);
            mImageView = itemView.findViewById(R.id.iv_RV);
            btnRemove = itemView.findViewById(R.id.btn_remove);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mAdapterClickListenerIF.onClick(getLayoutPosition());
        }
    }

    public interface AdapterClickListenerIF {

        void onClick(int position);
    }
}
