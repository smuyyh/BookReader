package com.justwayward.reader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseActivity;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.BookListDetail;
import com.justwayward.reader.common.OnRvItemClickListener;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.component.DaggerSubjectBookListDetailActivityComponent;
import com.justwayward.reader.ui.adapter.SubjectBookListDetailBooksAdapter;
import com.justwayward.reader.ui.contract.SubjectBookListDetailContract;
import com.justwayward.reader.ui.presenter.SubjectBookListDetailPresenter;
import com.justwayward.reader.utils.GlideCircleTransform;
import com.justwayward.reader.utils.LogUtils;
import com.justwayward.reader.utils.ScreenUtils;
import com.justwayward.reader.utils.ToastUtils;
import com.justwayward.reader.view.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class SubjectBookListDetailActivity extends BaseActivity implements SubjectBookListDetailContract.View,
        OnRvItemClickListener<BookListDetail.BookListBean.BooksBean> {

    @Bind(R.id.llBookDetail)
    LinearLayout llBookDetail;

    @Bind(R.id.tvBookListTitle)
    TextView tvBookListTitle;
    @Bind(R.id.tvBookListDesc)
    TextView tvBookListDesc;
    @Bind(R.id.ivAuthorAvatar)
    ImageView ivAuthorAvatar;
    @Bind(R.id.tvBookListAuthor)
    TextView tvBookListAuthor;
    @Bind(R.id.btnShare)
    TextView btnShare;
    @Bind(R.id.rvBooks)
    RecyclerView rvBooks;

    private SubjectBookListDetailBooksAdapter mSubjectBookListDetailBooksAdapter;
    private List<BookListDetail.BookListBean.BooksBean> mBooks = new ArrayList<>();

    private List<BookListDetail.BookListBean.BooksBean> mAllBooks = new ArrayList<>();

    private LinearLayoutManager linearLayoutManager;

    private int start = 0;
    private int limit = 20;

    @Inject
    SubjectBookListDetailPresenter mPresenter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SubjectBookListDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_subject_book_list_detail;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSubjectBookListDetailActivityComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setTitle(R.string.subject_book_list_detail);
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        showDialog();

        rvBooks.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        rvBooks.setLayoutManager(linearLayoutManager);
        rvBooks.addItemDecoration(new SpaceItemDecoration(ScreenUtils.dpToPxInt(5)));
        rvBooks.setItemAnimator(new DefaultItemAnimator());
        mSubjectBookListDetailBooksAdapter = new SubjectBookListDetailBooksAdapter(this, mBooks, this);
        rvBooks.setAdapter(mSubjectBookListDetailBooksAdapter);

        mPresenter.attachView(this);
        mPresenter.getBookListDetail(getIntent().getStringExtra("bookListId"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_subject, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showBookListDetail(BookListDetail data) {
        tvBookListTitle.setText(data.getBookList().getTitle());
        tvBookListDesc.setText(data.getBookList().getDesc());

        tvBookListAuthor.setText(data.getBookList().getAuthor().getNickname());


        Glide.with(mContext).load(Constant.IMG_BASE_URL + data.getBookList().getAuthor().getAvatar())
                .placeholder(R.drawable.avatar_default).transform(new GlideCircleTransform(mContext))
                .into(ivAuthorAvatar);

        List<BookListDetail.BookListBean.BooksBean> list = data.getBookList().getBooks();
        mAllBooks.clear();
        mAllBooks.addAll(list);
        mBooks.clear();
        loadNextPage();
        rvBooks.addOnScrollListener(new ScrollListener());
    }

    private void loadNextPage() {
        if(start < mAllBooks.size()) {
            if (mAllBooks.size() - start > limit) {
                mBooks.addAll(mAllBooks.subList(start, start + limit));
            } else {
                mBooks.addAll(mAllBooks.subList(start, mAllBooks.size()));
            }
            start += limit;

            mSubjectBookListDetailBooksAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void complete() {
        dismissDialog();
    }

    @Override
    public void onItemClick(View view, int position, BookListDetail.BookListBean.BooksBean data) {
        BookDetailActivity.startActivity(this, data.getBook().get_id());
    }

    private class ScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();

            LogUtils.e(lastVisibleItemPosition + " .......");

            if (lastVisibleItemPosition + 1 == mBooks.size()) { // 滑到倒数第二项就加载更多
                ToastUtils.showSingleToast("------");
                loadNextPage();
            }
        }
    }

}
