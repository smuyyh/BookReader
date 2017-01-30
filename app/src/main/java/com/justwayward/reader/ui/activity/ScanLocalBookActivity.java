/**
 * Copyright 2016 JustWayward Team
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.justwayward.reader.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseActivity;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.Recommend;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.manager.CollectionsManager;
import com.justwayward.reader.manager.EventManager;
import com.justwayward.reader.ui.easyadapter.RecommendAdapter;
import com.justwayward.reader.utils.AppUtils;
import com.justwayward.reader.utils.FileUtils;
import com.justwayward.reader.view.recyclerview.EasyRecyclerView;
import com.justwayward.reader.view.recyclerview.adapter.RecyclerArrayAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 扫描本地书籍
 *
 * @author yuyh.
 * @date 2016/10/9.
 */
public class ScanLocalBookActivity extends BaseActivity implements RecyclerArrayAdapter.OnItemClickListener {

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, ScanLocalBookActivity.class));
    }

    @Bind(R.id.recyclerview)
    EasyRecyclerView mRecyclerView;

    private RecommendAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_scan_local_book;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setTitle("扫描本地书籍");
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {
    }

    @Override
    public void configViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemDecoration(ContextCompat.getColor(this, R.color.common_divider_narrow), 1, 0, 0);

        mAdapter = new RecommendAdapter(this);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapterWithProgress(mAdapter);

        queryFiles();
    }

    private void queryFiles() {
        String[] projection = new String[]{MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.SIZE
        };

        // cache
        String bookpath = FileUtils.createRootPath(AppUtils.getAppContext());

        // 查询后缀名为txt与pdf，并且不位于项目缓存中的文档
        Cursor cursor = getContentResolver().query(
                Uri.parse("content://media/external/file"),
                projection,
                MediaStore.Files.FileColumns.DATA + " not like ? and ("
                        + MediaStore.Files.FileColumns.DATA + " like ? or "
                        + MediaStore.Files.FileColumns.DATA + " like ? or "
                        + MediaStore.Files.FileColumns.DATA + " like ? or "
                        + MediaStore.Files.FileColumns.DATA + " like ? )",
                new String[]{"%" + bookpath + "%",
                        "%" + Constant.SUFFIX_TXT,
                        "%" + Constant.SUFFIX_PDF,
                        "%" + Constant.SUFFIX_EPUB,
                        "%" + Constant.SUFFIX_CHM}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int idindex = cursor.getColumnIndex(MediaStore.Files.FileColumns._ID);
            int dataindex = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
            int sizeindex = cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE);
            List<Recommend.RecommendBooks> list = new ArrayList<>();


            do {
                String path = cursor.getString(dataindex);

                int dot = path.lastIndexOf("/");
                String name = path.substring(dot + 1);
                if (name.lastIndexOf(".") > 0)
                    name = name.substring(0, name.lastIndexOf("."));

                Recommend.RecommendBooks books = new Recommend.RecommendBooks();
                books._id = name;
                books.path = path;
                books.title = name;
                books.isFromSD = true;
                books.lastChapter = FileUtils.formatFileSizeToString(cursor.getLong(sizeindex));

                list.add(books);
            } while (cursor.moveToNext());

            cursor.close();

            mAdapter.addAll(list);
        } else {
            mAdapter.clear();
        }
    }

    @Override
    public void onItemClick(final int position) {
        final Recommend.RecommendBooks books = mAdapter.getItem(position);

        if (books.path.endsWith(Constant.SUFFIX_TXT)) {
            // TXT
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage(String.format(getString(
                            R.string.book_detail_is_joined_the_book_shelf), books.title))
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 拷贝到缓存目录
                            FileUtils.fileChannelCopy(new File(books.path),
                                    new File(FileUtils.getChapterPath(books._id, 1)));
                            // 加入书架
                            if (CollectionsManager.getInstance().add(books)) {
                                mRecyclerView.showTipViewAndDelayClose(String.format(getString(
                                        R.string.book_detail_has_joined_the_book_shelf), books.title));
                                // 通知
                                EventManager.refreshCollectionList();
                            } else {
                                mRecyclerView.showTipViewAndDelayClose("书籍已存在");
                            }
                            dialog.dismiss();
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        } else if (books.path.endsWith(Constant.SUFFIX_PDF)) {
            // PDF
            ReadPDFActivity.start(this, books.path);
        } else if (books.path.endsWith(Constant.SUFFIX_EPUB)) {
            // EPub
            ReadEPubActivity.start(this, books.path);
        } else if (books.path.endsWith(Constant.SUFFIX_CHM)) {
            // CHM
            ReadCHMActivity.start(this, books.path);
        }
    }
}
