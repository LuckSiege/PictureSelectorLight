package com.luck.picture.lib.config;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StyleRes;

import com.luck.picture.lib.R;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DebugUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * author：luck
 * project：PictureSelector
 * package：com.luck.picture.lib.config
 * email：893855882@qq.com
 * data：2017/5/24
 */

public final class PictureSelectionConfig implements Parcelable {
    public int mimeType;
    public boolean camera;
    public String outputCameraPath;
    @StyleRes
    public int themeStyleId;
    public int selectionMode;
    public int maxSelectNum;
    public int minSelectNum;
    public int videoQuality;
    public int videoSecond;
    public int recordVideoSecond;
    public int compressMaxkB;
    public int compressGrade;
    public int imageSpanCount;
    public int compressMode;
    public int compressWidth;
    public int compressHeight;
    public int overrideWidth;
    public int overrideHeight;
    public float sizeMultiplier;
    public boolean zoomAnim;
    public boolean isCompress;
    public boolean isCamera;
    public boolean isGif;
    public boolean enablePreview;
    public boolean enPreviewVideo;
    public boolean enablePreviewAudio;
    public boolean checkNumMode;
    public boolean openClickSound;
    public boolean previewEggs;

    public List<LocalMedia> selectionMedias;

    private void reset() {
        mimeType = PictureConfig.TYPE_IMAGE;
        camera = false;
        themeStyleId = R.style.picture_default_style;
        selectionMode = PictureConfig.MULTIPLE;
        maxSelectNum = 9;
        minSelectNum = 0;
        videoQuality = 1;
        videoSecond = 0;
        recordVideoSecond = 60;
        compressMaxkB = PictureConfig.MAX_COMPRESS_SIZE;
        compressGrade = Luban.THIRD_GEAR;
        imageSpanCount = 4;
        compressMode = PictureConfig.LUBAN_COMPRESS_MODE;
        compressWidth = 0;
        compressHeight = 0;
        overrideWidth = 0;
        overrideHeight = 0;
        sizeMultiplier = 0.5f;
        isCompress = false;
        enablePreviewAudio = true;
        isCamera = true;
        isGif = false;
        enablePreview = true;
        enPreviewVideo = true;
        checkNumMode = false;
        openClickSound = false;
        previewEggs = false;
        zoomAnim = true;
        outputCameraPath = "";
        selectionMedias = new ArrayList<>();
        DebugUtil.i("*******", "reset PictureSelectionConfig");
    }

    public static PictureSelectionConfig getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public static PictureSelectionConfig getCleanInstance() {
        PictureSelectionConfig selectionSpec = getInstance();
        selectionSpec.reset();
        return selectionSpec;
    }

    private static final class InstanceHolder {
        private static final PictureSelectionConfig INSTANCE = new PictureSelectionConfig();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mimeType);
        dest.writeByte(this.camera ? (byte) 1 : (byte) 0);
        dest.writeString(this.outputCameraPath);
        dest.writeInt(this.themeStyleId);
        dest.writeInt(this.selectionMode);
        dest.writeInt(this.maxSelectNum);
        dest.writeInt(this.minSelectNum);
        dest.writeInt(this.videoQuality);
        dest.writeInt(this.videoSecond);
        dest.writeInt(this.recordVideoSecond);
        dest.writeInt(this.compressMaxkB);
        dest.writeInt(this.compressGrade);
        dest.writeInt(this.imageSpanCount);
        dest.writeInt(this.compressMode);
        dest.writeInt(this.compressWidth);
        dest.writeInt(this.compressHeight);
        dest.writeInt(this.overrideWidth);
        dest.writeInt(this.overrideHeight);
        dest.writeFloat(this.sizeMultiplier);
        dest.writeByte(this.zoomAnim ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isCompress ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isCamera ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isGif ? (byte) 1 : (byte) 0);
        dest.writeByte(this.enablePreview ? (byte) 1 : (byte) 0);
        dest.writeByte(this.enPreviewVideo ? (byte) 1 : (byte) 0);
        dest.writeByte(this.enablePreviewAudio ? (byte) 1 : (byte) 0);
        dest.writeByte(this.checkNumMode ? (byte) 1 : (byte) 0);
        dest.writeByte(this.openClickSound ? (byte) 1 : (byte) 0);
        dest.writeByte(this.previewEggs ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.selectionMedias);
    }

    public PictureSelectionConfig() {
    }

    protected PictureSelectionConfig(Parcel in) {
        this.mimeType = in.readInt();
        this.camera = in.readByte() != 0;
        this.outputCameraPath = in.readString();
        this.themeStyleId = in.readInt();
        this.selectionMode = in.readInt();
        this.maxSelectNum = in.readInt();
        this.minSelectNum = in.readInt();
        this.videoQuality = in.readInt();
        this.videoSecond = in.readInt();
        this.recordVideoSecond = in.readInt();
        this.compressMaxkB = in.readInt();
        this.compressGrade = in.readInt();
        this.imageSpanCount = in.readInt();
        this.compressMode = in.readInt();
        this.compressWidth = in.readInt();
        this.compressHeight = in.readInt();
        this.overrideWidth = in.readInt();
        this.overrideHeight = in.readInt();
        this.sizeMultiplier = in.readFloat();
        this.zoomAnim = in.readByte() != 0;
        this.isCompress = in.readByte() != 0;
        this.isCamera = in.readByte() != 0;
        this.isGif = in.readByte() != 0;
        this.enablePreview = in.readByte() != 0;
        this.enPreviewVideo = in.readByte() != 0;
        this.enablePreviewAudio = in.readByte() != 0;
        this.checkNumMode = in.readByte() != 0;
        this.openClickSound = in.readByte() != 0;
        this.previewEggs = in.readByte() != 0;
        this.selectionMedias = in.createTypedArrayList(LocalMedia.CREATOR);
    }

    public static final Parcelable.Creator<PictureSelectionConfig> CREATOR = new Parcelable.Creator<PictureSelectionConfig>() {
        @Override
        public PictureSelectionConfig createFromParcel(Parcel source) {
            return new PictureSelectionConfig(source);
        }

        @Override
        public PictureSelectionConfig[] newArray(int size) {
            return new PictureSelectionConfig[size];
        }
    };
}
