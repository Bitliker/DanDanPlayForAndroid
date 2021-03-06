package com.xyoye.dandanplay.ui.weight.item;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.xyoye.dandanplay.R;
import com.xyoye.dandanplay.bean.AnimeDetailBean;
import com.xyoye.dandanplay.ui.activities.anime.AnimeListActivity;
import com.xyoye.dandanplay.utils.interf.AdapterItem;

import butterknife.BindView;

/**
 * Created by xyoye on 2019/7/9.
 */

public class AnimeTagItem implements AdapterItem<AnimeDetailBean.BangumiBean.TagsBean> {

    @BindView(R.id.tag_tv)
    TextView tagTv;

    private Context context;

    @Override
    public int getLayoutResId() {
        return R.layout.item_anime_tag;
    }

    @Override
    public void initItemViews(View itemView) {
        context = itemView.getContext();
    }

    @Override
    public void onSetViews() {

    }

    @Override
    public void onUpdateViews(AnimeDetailBean.BangumiBean.TagsBean model, int position) {
        tagTv.setText(model.getName());
        tagTv.setOnClickListener(v -> {
            if (model.getId() != -1){
                AnimeListActivity.launchAnimeListTag(context, model);
            }
        });
    }
}
