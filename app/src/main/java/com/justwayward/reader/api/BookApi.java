package com.justwayward.reader.api;

import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.AutoComplete;
import com.justwayward.reader.bean.BookDetail;
import com.justwayward.reader.bean.BookSource;
import com.justwayward.reader.bean.BookToc;
import com.justwayward.reader.bean.BooksByCats;
import com.justwayward.reader.bean.BooksByTag;
import com.justwayward.reader.bean.CategoryList;
import com.justwayward.reader.bean.CategoryListLv2;
import com.justwayward.reader.bean.ChapterRead;
import com.justwayward.reader.bean.HotReview;
import com.justwayward.reader.bean.HotWord;
import com.justwayward.reader.bean.Recommend;
import com.justwayward.reader.bean.RecommendBookList;
import com.justwayward.reader.bean.SearchDetail;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author yuyh.
 * @date 2016/8/3.
 */
public class BookApi {

    public static BookApi instance;

    private BookApiService service;

    public BookApi(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.API_BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 添加Rx适配器
                .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
                .client(okHttpClient)
                .build();
        service = retrofit.create(BookApiService.class);
    }

    public static BookApi getInstance(OkHttpClient okHttpClient) {
        if (instance == null)
            instance = new BookApi(okHttpClient);
        return instance;
    }

    public Observable<Recommend> getRecommend(String gender) {
        return service.getRecomend(gender);
    }

    public Observable<HotWord> getHotWord() {
        return service.getHotWord();
    }

    public Observable<AutoComplete> getAutoComplete(String query) {
        return service.autoComplete(query);
    }

    public Observable<SearchDetail> getSearchResult(String query) {
        return service.searchBooks(query);
    }

    public Observable<BookDetail> getBookDetail(String bookId) {
        return service.getBookDetail(bookId);
    }

    public Observable<HotReview> getHotReview(String book) {
        return service.getHotReview(book);
    }

    public Observable<RecommendBookList> getRecommendBookList(String bookId, String limit) {
        return service.getRecommendBookList(bookId, limit);
    }

    public Observable<BooksByTag> getBooksByTag(String tags, String start, String limit) {
        return service.getBooksByTag(tags, start, limit);
    }

    public Observable<BookToc> getBookToc(String bookId, String view) {
        return service.getBookToc(bookId, view);
    }

    public synchronized Observable<ChapterRead> getChapterRead(String url) {
        return service.getChapterRead(url);
    }

    public synchronized Observable<List<BookSource>> getBookSource(String view,String book) {
        return service.getBookSource(view,book);
    }

    public synchronized Observable<CategoryList> getCategoryList() {
        return service.getCategoryList();
    }

    public Observable<CategoryListLv2> getCategoryListLv2(){
        return service.getCategoryListLv2();
    }

    public Observable<BooksByCats> getBooksByCats(String gender, String type, String major, String minor, int start, @Query("limit") int limit){
        return service.getBooksByCats(gender, type, major, minor, start, limit);
    }

}
