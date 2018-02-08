package com.maintrineggmail.nytimesviewer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MostEmailedFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        mRecyclerView = (RecyclerView) view
                .findViewById(R.id.id_fragment_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    public void updateUI() {
        ArticlesDB articlesDB = ArticlesDB.get(getActivity());
        List<Articles> articles = articlesDB.getArticles();

        if (mAdapter == null) {
            mAdapter = new MyAdapter(articles);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class CrimeHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Articles mArticle;
        private TextView mTextView;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_most_emailed, parent, false));
            itemView.setOnClickListener(this);
            mTextView = (TextView) itemView.findViewById(R.id.tv_most_emailed);
        }

        public void bind(Articles article) {
            mArticle = article;
            mTextView.setText(mArticle.getArticle());
        }

        @Override
        public void onClick(View view) {
            Toast toast = Toast.makeText(getContext(),"Click",Toast.LENGTH_SHORT);
            toast.show();
//            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
//            startActivity(intent);
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Articles> mArticles;

        public MyAdapter(List<Articles> articles) {
            mArticles = articles;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CrimeHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Articles article = mArticles.get(position);
            holder.bind(article);
        }

        @Override
        public int getItemCount() {
            return mArticles.size();
        }
    }
}

