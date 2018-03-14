package com.maintrineggmail.nytimesviewer.model.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.maintrineggmail.nytimesviewer.R;
import com.maintrineggmail.nytimesviewer.model.model.MediaMetadata;
import com.maintrineggmail.nytimesviewer.model.model.Result;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;

import io.realm.RealmList;

public class DownloadPicture {
    private Bitmap mBitmap;
    private static final String TAG = "DownloadPicture";

    public Bitmap downloadPicture(Result result, Context context) {
        if (result.getMediaList() != null && !result.getMediaList().isEmpty()) {
            RealmList<MediaMetadata> mediaMetadataList = result.getMediaList().get(0).getMediaMetadataList();

            Picasso.with(context).
                    load(sortPhoto(mediaMetadataList)).
                    placeholder(R.drawable.nyt_blue).
                    into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            mBitmap = bitmap;
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });
            if (mBitmap == null) {
                downloadPicture(result, context);
            }
            return mBitmap;
        }
        return null;
    }

    public byte[] downloadPicture(Bitmap bitmap) {
        byte[] bytes;
        if (bitmap != null) {
            try {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                bytes = outputStream.toByteArray();
                return bytes;
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }
        }
        return null;
    }

    public Bitmap downloadPicture(byte[] bytes) {
        if (bytes != null && bytes.length != 0) {
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            return bmp;
        }
        return null;
    }

    private String sortPhoto(RealmList<MediaMetadata> mediaMetadataList) {
        if (mediaMetadataList != null) {
            switch (mediaMetadataList.size()) {
                case 0:
                    break;
                case 1:
                    return mediaMetadataList.get(0).getUrl();
                case 2:
                    if (mediaMetadataList.get(0).getWidth() > mediaMetadataList.get(1).getWidth()) {
                        return mediaMetadataList.get(0).getUrl();
                    } else {
                        return mediaMetadataList.get(1).getUrl();
                    }
                case 3:
                    if (mediaMetadataList.get(0).getWidth() > mediaMetadataList.get(1).getWidth()
                            && mediaMetadataList.get(0).getWidth() > mediaMetadataList.get(2).getWidth()) {
                        return mediaMetadataList.get(0).getUrl();
                    } else if (mediaMetadataList.get(1).getWidth() > mediaMetadataList.get(0).getWidth()
                            && mediaMetadataList.get(1).getWidth() > mediaMetadataList.get(2).getWidth()) {
                        return mediaMetadataList.get(1).getUrl();
                    } else if (mediaMetadataList.get(2).getWidth() > mediaMetadataList.get(0).getWidth()
                            && mediaMetadataList.get(2).getWidth() > mediaMetadataList.get(1).getWidth()) {
                        return mediaMetadataList.get(2).getUrl();
                    }

                default:
                    if (mediaMetadataList.get(0).getWidth() > mediaMetadataList.get(1).getWidth()
                            && mediaMetadataList.get(0).getWidth() > mediaMetadataList.get(2).getWidth()) {
                        return mediaMetadataList.get(0).getUrl();
                    } else if (mediaMetadataList.get(1).getWidth() > mediaMetadataList.get(0).getWidth()
                            && mediaMetadataList.get(1).getWidth() > mediaMetadataList.get(2).getWidth()) {
                        return mediaMetadataList.get(1).getUrl();
                    } else if (mediaMetadataList.get(2).getWidth() > mediaMetadataList.get(0).getWidth()
                            && mediaMetadataList.get(2).getWidth() > mediaMetadataList.get(1).getWidth()) {
                        return mediaMetadataList.get(2).getUrl();
                    }
            }
        }
        return null;
    }
}
