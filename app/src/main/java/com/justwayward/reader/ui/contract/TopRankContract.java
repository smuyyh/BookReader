package com.justwayward.reader.ui.contract;

import com.justwayward.reader.bean.RankingList;

/**
 * @author yuyh.
 * @date 16/9/1.
 */
public interface TopRankContract {

    interface View {
        void showRankList(RankingList rankingList);

        void complete();
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {

        void getRankList();
    }

}
