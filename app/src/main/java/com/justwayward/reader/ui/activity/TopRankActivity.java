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
package com.justwayward.reader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ExpandableListView;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseActivity;
import com.justwayward.reader.bean.RankingList;
import com.justwayward.reader.common.OnRvItemClickListener;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerFindComponent;
import com.justwayward.reader.ui.adapter.TopRankAdapter;
import com.justwayward.reader.ui.contract.TopRankContract;
import com.justwayward.reader.ui.presenter.TopRankPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class TopRankActivity extends BaseActivity implements TopRankContract.View {

    @Bind(R.id.elvFeMale)
    ExpandableListView elvFeMale;
    @Bind(R.id.elvMale)
    ExpandableListView elvMale;

    private List<RankingList.MaleBean> maleGroups = new ArrayList<>();
    private List<List<RankingList.MaleBean>> maleChilds = new ArrayList<>();
    private TopRankAdapter maleAdapter;

    private List<RankingList.MaleBean> femaleGroups = new ArrayList<>();
    private List<List<RankingList.MaleBean>> femaleChilds = new ArrayList<>();
    private TopRankAdapter femaleAdapter;

    @Inject
    TopRankPresenter mPresenter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, TopRankActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_top_rank;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerFindComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setTitle("排行榜");
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {
        maleAdapter = new TopRankAdapter(this, maleGroups, maleChilds);
        femaleAdapter = new TopRankAdapter(this, femaleGroups, femaleChilds);
        maleAdapter.setItemClickListener(new ClickListener());
        femaleAdapter.setItemClickListener(new ClickListener());
    }

    @Override
    public void configViews() {
        showDialog();
        elvMale.setAdapter(maleAdapter);
        elvFeMale.setAdapter(femaleAdapter);

        mPresenter.attachView(this);
        mPresenter.getRankList();
    }

    @Override
    public void showRankList(RankingList rankingList) {
        maleGroups.clear();
        femaleGroups.clear();
        updateMale(rankingList);
        updateFemale(rankingList);
    }

    private void updateMale(RankingList rankingList) {
        List<RankingList.MaleBean> list = rankingList.male;
        List<RankingList.MaleBean> collapse = new ArrayList<>();
        for (RankingList.MaleBean bean : list) {
            if (bean.collapse) { // 折叠
                collapse.add(bean);
            } else {
                maleGroups.add(bean);
                maleChilds.add(new ArrayList<RankingList.MaleBean>());
            }
        }
        if (collapse.size() > 0) {
            maleGroups.add(new RankingList.MaleBean("别人家的排行榜"));
            maleChilds.add(collapse);
        }
        maleAdapter.notifyDataSetChanged();
    }

    private void updateFemale(RankingList rankingList) {
        List<RankingList.MaleBean> list = rankingList.female;
        List<RankingList.MaleBean> collapse = new ArrayList<>();
        for (RankingList.MaleBean bean : list) {
            if (bean.collapse) { // 折叠
                collapse.add(bean);
            } else {
                femaleGroups.add(bean);
                femaleChilds.add(new ArrayList<RankingList.MaleBean>());
            }
        }
        if (collapse.size() > 0) {
            femaleGroups.add(new RankingList.MaleBean("别人家的排行榜"));
            femaleChilds.add(collapse);
        }
        femaleAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {
        dismissDialog();
    }

    class ClickListener implements OnRvItemClickListener<RankingList.MaleBean> {

        @Override
        public void onItemClick(View view, int position, RankingList.MaleBean data) {
            if (data.monthRank == null) {
                SubOtherHomeRankActivity.startActivity(mContext, data._id, data.title);
            } else {
                SubRankActivity.startActivity(mContext, data._id, data.monthRank, data.totalRank, data.title);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
