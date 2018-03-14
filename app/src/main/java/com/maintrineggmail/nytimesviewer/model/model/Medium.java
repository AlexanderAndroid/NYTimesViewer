
package com.maintrineggmail.nytimesviewer.model.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class Medium implements RealmModel {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("subtype")
    @Expose
    private String subtype;
    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("media-metadata")
    @Expose
    private RealmList<MediaMetadata> mediaMetadataList;

    private byte[] picture;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Medium withType(String type) {
        this.type = type;
        return this;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public Medium withSubtype(String subtype) {
        this.subtype = subtype;
        return this;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Medium withCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public Medium withCopyright(String copyright) {
        this.copyright = copyright;
        return this;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public RealmList<MediaMetadata> getMediaMetadataList() {
        return mediaMetadataList;
    }

    public void setMediaMetadataList(RealmList<MediaMetadata> mediaMetadataList) {
        this.mediaMetadataList = mediaMetadataList;
    }

}
