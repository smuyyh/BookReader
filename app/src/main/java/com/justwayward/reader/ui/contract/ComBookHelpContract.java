package com.justwayward.reader.ui.contract;

import com.justwayward.reader.bean.HelpList;

import java.util.List;

/**
 * @author lfh.
 * @date 16/9/3.
 */
public interface ComBookHelpContract {

    interface View {
        void showHelpList(List<HelpList.HelpsBean> list, boolean isRefresh);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getHelpList(String sort, String distillate, int start, int limit);
    }

}
