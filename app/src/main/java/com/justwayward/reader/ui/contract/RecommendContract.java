package com.justwayward.reader.ui.contract;

import com.justwayward.reader.bean.Recommend;

import java.util.List;

/**
 * @author yuyh.
 * @date 2016/8/3.
 */
public interface RecommendContract {

    interface View {
        void showRecommendList(List<Recommend.RecommendBooks> list);
    }

    interface Presenter {

    }

}
