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
        ttsConfig.addParam(TtsConfig.SessionConfig.PARAM_KEY_CAP_KEY, "tts.cloud.xiaokun"); // 发音人
        ttsConfig.addParam(TtsConfig.BasicConfig.PARAM_KEY_AUDIO_FORMAT, "pcm16k16bit"); // 音频格式
        return ttsConfig;
    }
}
