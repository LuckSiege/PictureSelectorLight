package com.luck.pictureselector;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DebugUtil;
import com.luck.pictureselector.adapter.GridImageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author：luck
 * project：PictureSelector
 * package：com.luck.pictureselector
 * email：893855882@qq.com
 * data：2017/5/30
 */

public class PhotoFragment extends Fragment implements View.OnClickListener,
        RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener {
    private final static String TAG = PhotoFragment.class.getSimpleName();
    private View rootView;
    private List<LocalMedia> selectList = new ArrayList<>();
    private RecyclerView recyclerView;
    private GridImageAdapter adapter;
    private int maxSelectNum = 9;
    private TextView tv_select_num;
    private ImageView left_back, minus, plus;
    private RadioGroup rgb_compress, rgb_style, rgb_photo_mode;
    private CheckBox cb_voice, cb_choose_mode, cb_isCamera, cb_isGif,
            cb_preview_img, cb_preview_video, cb_compress, cb_preview_audio,
            cb_mode;
    private int compressMode = PictureConfig.SYSTEM_COMPRESS_MODE;
    private int themeId;
    private int chooseMode = PictureMimeType.ofAll();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.activity_main, container, false);
        }
        init();
        return rootView;
    }

    private void init() {
        themeId = R.style.picture_default_style;
        minus = (ImageView) rootView.findViewById(R.id.minus);
        plus = (ImageView) rootView.findViewById(R.id.plus);
        tv_select_num = (TextView) rootView.findViewById(R.id.tv_select_num);
        rgb_style = (RadioGroup) rootView.findViewById(R.id.rgb_style);
        rgb_photo_mode = (RadioGroup) rootView.findViewById(R.id.rgb_photo_mode);
        rgb_compress = (RadioGroup) rootView.findViewById(R.id.rgb_compress);
        cb_voice = (CheckBox) rootView.findViewById(R.id.cb_voice);
        cb_choose_mode = (CheckBox) rootView.findViewById(R.id.cb_choose_mode);
        cb_isCamera = (CheckBox) rootView.findViewById(R.id.cb_isCamera);
        cb_isGif = (CheckBox) rootView.findViewById(R.id.cb_isGif);
        cb_preview_img = (CheckBox) rootView.findViewById(R.id.cb_preview_img);
        cb_preview_video = (CheckBox) rootView.findViewById(R.id.cb_preview_video);
        cb_compress = (CheckBox) rootView.findViewById(R.id.cb_compress);
        cb_mode = (CheckBox) rootView.findViewById(R.id.cb_mode);
        cb_preview_audio = (CheckBox) rootView.findViewById(R.id.cb_preview_audio);
        rgb_compress.setOnCheckedChangeListener(this);
        rgb_style.setOnCheckedChangeListener(this);
        rgb_photo_mode.setOnCheckedChangeListener(this);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        left_back = (ImageView) rootView.findViewById(R.id.left_back);
        left_back.setOnClickListener(this);
        minus.setOnClickListener(this);
        plus.setOnClickListener(this);
        cb_compress.setOnCheckedChangeListener(this);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(getActivity(), 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(getActivity(), onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                LocalMedia media = selectList.get(position);
                String pictureType = media.getPictureType();
                int mediaType = PictureMimeType.pictureToVideo(pictureType);
                switch (mediaType) {
                    case 1:
                        // 预览图片
                        PictureSelector.create(PhotoFragment.this).externalPicturePreview(position, selectList);
                        break;
                    case 2:
                        // 预览视频
                        PictureSelector.create(PhotoFragment.this).externalPictureVideo(media.getPath());
                        break;
                    case 3:
                        // 预览音频
                        PictureSelector.create(PhotoFragment.this).externalPictureAudio(media.getPath());
                        break;
                }
            }
        });
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            boolean mode = cb_mode.isChecked();
            if (mode) {
                // 进入相册
                PictureSelector.create(PhotoFragment.this)
                        .openGallery(chooseMode)
                        .theme(themeId)
                        .maxSelectNum(maxSelectNum)
                        .minSelectNum(1)
                        .selectionMode(cb_choose_mode.isChecked() ? PictureConfig.MULTIPLE : PictureConfig.SINGLE)
                        .previewImage(cb_preview_img.isChecked())
                        .previewVideo(cb_preview_video.isChecked())
                        .compressGrade(Luban.THIRD_GEAR)
                        .isCamera(cb_isCamera.isChecked())
                        .compress(cb_compress.isChecked())
                        .compressMode(compressMode)
                        .glideOverride(160, 160)
                        .previewEggs(true)
                        .isGif(cb_isGif.isChecked())
                        .openClickSound(cb_voice.isChecked())
                        .selectionMedia(selectList)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            } else {
                // 单独拍照
                PictureSelector.create(PhotoFragment.this)
                        .openCamera(chooseMode)
                        .theme(themeId)
                        .maxSelectNum(maxSelectNum)
                        .minSelectNum(1)
                        .selectionMode(cb_choose_mode.isChecked() ? PictureConfig.MULTIPLE : PictureConfig.SINGLE)
                        .previewImage(cb_preview_img.isChecked())
                        .previewVideo(cb_preview_video.isChecked())
                        .compressGrade(Luban.THIRD_GEAR)
                        .isCamera(cb_isCamera.isChecked())
                        .compress(cb_compress.isChecked())
                        .compressMode(compressMode)
                        .glideOverride(160, 160)
                        .isGif(cb_isGif.isChecked())
                        .openClickSound(cb_voice.isChecked())
                        .selectionMedia(selectList)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择
                    selectList = PictureSelector.obtainMultipleResult(data);
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    DebugUtil.i(TAG, "onActivityResult:" + selectList.size());
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_back:
                getActivity().finish();
                break;
            case R.id.minus:
                if (maxSelectNum > 1) {
                    maxSelectNum--;
                }
                tv_select_num.setText(maxSelectNum + "");
                adapter.setSelectMax(maxSelectNum);
                break;
            case R.id.plus:
                maxSelectNum++;
                tv_select_num.setText(maxSelectNum + "");
                adapter.setSelectMax(maxSelectNum);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rb_all:
                chooseMode = PictureMimeType.ofAll();
                cb_preview_img.setChecked(true);
                cb_preview_video.setChecked(true);
                cb_isGif.setChecked(false);
                cb_isGif.setVisibility(View.GONE);
                cb_preview_video.setChecked(true);
                cb_preview_img.setChecked(true);
                cb_preview_audio.setVisibility(View.GONE);
                cb_preview_video.setVisibility(View.VISIBLE);
                cb_preview_img.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_image:
                chooseMode = PictureMimeType.ofImage();
                cb_preview_img.setChecked(true);
                cb_preview_video.setChecked(false);
                cb_isGif.setChecked(false);
                cb_isGif.setVisibility(View.VISIBLE);
                cb_preview_video.setChecked(false);
                cb_preview_video.setVisibility(View.GONE);
                cb_preview_img.setChecked(true);
                cb_preview_audio.setVisibility(View.GONE);
                cb_preview_img.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_video:
                chooseMode = PictureMimeType.ofVideo();
                cb_preview_img.setChecked(false);
                cb_preview_video.setChecked(true);
                cb_isGif.setChecked(false);
                cb_isGif.setVisibility(View.GONE);
                cb_preview_video.setChecked(true);
                cb_preview_video.setVisibility(View.VISIBLE);
                cb_preview_img.setVisibility(View.GONE);
                cb_preview_audio.setVisibility(View.GONE);
                cb_preview_img.setChecked(false);
                break;
            case R.id.rb_audio:
                chooseMode = PictureMimeType.ofAudio();
                cb_preview_audio.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_compress_system:
                compressMode = PictureConfig.SYSTEM_COMPRESS_MODE;
                break;
            case R.id.rb_compress_luban:
                compressMode = PictureConfig.LUBAN_COMPRESS_MODE;
                break;
            case R.id.rb_default_style:
                themeId = R.style.picture_default_style;
                break;
            case R.id.rb_white_style:
                themeId = R.style.picture_white_style;
                break;
            case R.id.rb_num_style:
                themeId = R.style.picture_QQ_style;
                break;
            case R.id.rb_sina_style:
                themeId = R.style.picture_Sina_style;
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_compress:
                rgb_compress.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                break;
        }
    }
}
