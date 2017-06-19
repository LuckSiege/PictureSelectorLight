package com.luck.picture.lib.config;

import android.support.annotation.StyleRes;

import com.luck.picture.lib.R;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DebugUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * author：luck
 * project：PictureSelector
 * package：com.luck.picture.lib.config
 * email：893855882@qq.com
 * data：2017/5/24
 */

public final class PictureSelectionConfig implements Serializable {
    public int mimeType;
    public boolean camera;
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
    public boolean isCompress;
    public boolean isCamera;
    public boolean isGif;
    public boolean enablePreview;
    public boolean enPreviewVideo;
    public boolean enablePreviewAudio;
    public boolean checkNumMode;
    public boolean openClickSound;
    public boolean previewEggs;

    public List<LocalMedia> selectionMedias = new ArrayList<>();

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
        isCompress = false;
        enablePreviewAudio = true;
        isCamera = true;
        isGif = false;
        enablePreview = true;
        enPreviewVideo = true;
        checkNumMode = false;
        openClickSound = false;
        previewEggs = false;
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
}
