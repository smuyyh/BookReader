package com.justwayward.reader.base;

import com.justwayward.reader.R;
import com.justwayward.reader.bean.support.SelectionEvent;
import com.justwayward.reader.utils.ToastUtils;
import com.justwayward.reader.view.SelectionLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @author yuyh.
 * @date 16/9/2.
 */
public abstract class BaseCommuniteActivity extends BaseActivity implements SelectionLayout.OnSelectListener {

    @Bind(R.id.slOverall)
    SelectionLayout slOverall;

    protected List<List<String>> list;

    protected List<List<String>> list1 = new ArrayList<List<String>>() {{
        add(new ArrayList<String>() {{
            add("全部");
            add("精品");
        }});
        add(new ArrayList<String>() {{
            add("默认排序");
            add("最新发布");
            add("最多评论");
        }});
    }};

    protected List<List<String>> list2 = new ArrayList<List<String>>() {{
        add(new ArrayList<String>() {{
            add("全部");
            add("精品");
        }});
        add(new ArrayList<String>() {{
            add("全部类型");
            add("玄幻奇幻");
            add("武侠仙侠");
            add("都市异能");
            add("历史军事");
            add("游戏竞技");
            add("科幻灵异");
            add("穿越架空");
            add("豪门总裁");
            add("现代言情");
            add("古代言情");
            add("幻想言情");
            add("耽美同人");
        }});
        add(new ArrayList<String>() {{
            add("默认排序");
            add("最新发布");
            add("最有用的");
            add("最多评论");
        }});
    }};

    @Constant.Distillate
    private String distillate = Constant.Distillate.ALL;
    @Constant.BookType
    private String type = Constant.BookType.ALL;
    @Constant.SortType
    private String sort = Constant.SortType.DEFAULT;

    @Override
    public void initDatas() {
        list = getTabList();
        if (slOverall != null) {
            slOverall.setData(list.toArray(new List[list.size()]));
            slOverall.setOnSelectListener(this);
        }
    }

    protected abstract List<List<String>> getTabList();

    @Override
    public void onSelect(int index, int position, String title) {
        switch (index) {
            case 0:
                switch (position) {
                    case 0:
                        distillate = "";
                        break;
                    case 1:
                        distillate = "true";
                        break;
                    default:
                        break;
                }
                break;
            case 1:
                if (list.size() == 2) {
                    switch (position) {
                        case 0:
                            sort = Constant.SortType.DEFAULT;
                            break;
                        case 1:
                            sort = Constant.SortType.CREATED;
                            break;
                        case 2:
                            sort = Constant.SortType.COMMENT_COUNT;
                            break;
                        default:
                            break;
                    }
                } else if (list.size() == 3) {
                    switch (position) {
                        case 0:
                            type = Constant.BookType.ALL;
                            break;
                        case 1:
                            type = Constant.BookType.XHQH;
                            break;
                        case 2:
                            type = Constant.BookType.WXXX;
                            break;
                        case 3:
                            type = Constant.BookType.DSYN;
                            break;
                        case 4:
                            type = Constant.BookType.LSJS;
                            break;
                        case 5:
                            type = Constant.BookType.YXJJ;
                            break;
                        case 6:
                            type = Constant.BookType.KHLY;
                            break;
                        case 7:
                            type = Constant.BookType.CYJK;
                            break;
                        case 8:
                            type = Constant.BookType.HMZC;
                            break;
                        case 9:
                            type = Constant.BookType.XDYQ;
                            break;
                        case 10:
                            type = Constant.BookType.GDYQ;
                            break;
                        case 11:
                            type = Constant.BookType.HXYQ;
                            break;
                        case 12:
                            type = Constant.BookType.DMTR;
                            break;
                        default:
                            break;
                    }
                }
                break;
            case 2:
                switch (position) {
                    case 0:
                        sort = Constant.SortType.DEFAULT;
                        break;
                    case 1:
                        sort = Constant.SortType.CREATED;
                        break;
                    case 2:
                        sort = Constant.SortType.COMMENT_COUNT;
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }

        ToastUtils.showSingleToast("index="+index+" position="+position+" data="+title);

        EventBus.getDefault().post(new SelectionEvent(distillate, type, sort));
    }
}
