package com.justwayward.reader.utils;

import com.justwayward.reader.common.TTSEventProcess;
import com.sinovoice.hcicloudsdk.android.tts.player.TTSPlayer;
import com.sinovoice.hcicloudsdk.common.tts.TtsConfig;
import com.sinovoice.hcicloudsdk.common.tts.TtsInitParam;

/**
 * @author yuyh.
 * @date 2016/8/16.
 */
public class TTSPlayerUtils {

    public static TTSPlayer getTTSPlayer(){
        TTSPlayer mTtsPlayer = new TTSPlayer();
        TtsInitParam ttsInitParam = new TtsInitParam();
        ttsInitParam.addParam(TtsInitParam.PARAM_KEY_FILE_FLAG, "none");
        mTtsPlayer.init(ttsInitParam.getStringConfig(), new TTSEventProcess());
        return mTtsPlayer;
    }

    public static TtsConfig getTtsConfig(){
        TtsConfig ttsConfig = new TtsConfig();
        ttsConfig.addParam(TtsConfig.SessionConfig.PARAM_KEY_CAP_KEY, "tts.cloud.wangjing"); // 发音人
        ttsConfig.addParam(TtsConfig.BasicConfig.PARAM_KEY_AUDIO_FORMAT, "pcm16k16bit"); // 音频格式
        return ttsConfig;
    }
}
