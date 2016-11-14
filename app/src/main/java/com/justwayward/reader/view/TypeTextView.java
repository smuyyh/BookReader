/**
 * Copyright 2016 JustWayward Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.justwayward.reader.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 打字效果TextView
 *
 * @author yuyh.
 * @date 16/4/10.
 */
public class TypeTextView extends TextView {
    private Context mContext = null;
    private MediaPlayer mMediaPlayer = null;
    private String mShowTextString = null;
    private Timer mTypeTimer = null;
    private OnTypeViewListener mOnTypeViewListener = null;
    private static final int TYPE_TIME_DELAY = 80;
    private int mTypeTimeDelay = TYPE_TIME_DELAY; // 打字间隔

    public TypeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initTypeTextView(context);
    }

    public TypeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTypeTextView(context);
    }

    public TypeTextView(Context context) {
        super(context);
        initTypeTextView(context);
    }

    public void setOnTypeViewListener(OnTypeViewListener onTypeViewListener) {
        mOnTypeViewListener = onTypeViewListener;
    }

    public void start(final String textString) {
        start(textString, TYPE_TIME_DELAY);
    }

    public void start(final String textString, final int typeTimeDelay) {
        if (TextUtils.isEmpty(textString) || typeTimeDelay < 0) {
            return;
        }
        post(new Runnable() {
            @Override
            public void run() {
                mShowTextString = textString;
                mTypeTimeDelay = typeTimeDelay;
                setText("");
                startTypeTimer();
                if (null != mOnTypeViewListener) {
                    mOnTypeViewListener.onTypeStart();
                }
            }
        });
    }

    public void stop() {
        stopTypeTimer();
        stopAudio();
    }

    private void initTypeTextView(Context context) {
        mContext = context;
    }

    private void startTypeTimer() {
        stopTypeTimer();
        mTypeTimer = new Timer();
        mTypeTimer.schedule(new TypeTimerTask(), mTypeTimeDelay);
    }

    private void stopTypeTimer() {
        if (null != mTypeTimer) {
            mTypeTimer.cancel();
            mTypeTimer = null;
        }
    }

    private void startAudioPlayer() {
        stopAudio();
        //TODO playAudio(R.raw.type_in);
    }

    private void playAudio(int audioResId) {
        try {
            stopAudio();
            mMediaPlayer = MediaPlayer.create(mContext, audioResId);
            mMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopAudio() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    class TypeTimerTask extends TimerTask {
        @Override
        public void run() {
            post(new Runnable() {
                @Override
                public void run() {
                    if (getText().toString().length() < mShowTextString.length()) {
                        setText(mShowTextString.substring(0, getText().toString().length() + 1));
                        startAudioPlayer();
                        startTypeTimer();
                    } else {
                        stopTypeTimer();
                        if (null != mOnTypeViewListener) {
                            mOnTypeViewListener.onTypeOver();
                        }
                    }
                }
            });
        }
    }

    public interface OnTypeViewListener {
        void onTypeStart();

        void onTypeOver();
    }
}