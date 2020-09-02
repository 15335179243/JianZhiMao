package com.chumu.jianzhimao.mvp.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.chumu.jianzhimao.R;
import com.chumu.jianzhimao.mvp.bean.City;
import com.example.common_base.utils.ToastUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.chumu.jianzhimao.mvp.bean.City.MESSAGE_NOTE_CONTENT_TYPE;
import static com.chumu.jianzhimao.mvp.bean.City.MESSAGE_NOTE_TITLE_TYPE;


public class CityAdapter extends BaseMultiItemQuickAdapter<City, BaseViewHolder> {


    public CityAdapter(List<City> data) {
        super(data);
        addItemType(MESSAGE_NOTE_TITLE_TYPE, R.layout.item_city);
        addItemType(MESSAGE_NOTE_CONTENT_TYPE, R.layout.item_pinned_header);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, City item) {
        switch (item.getItemType()) {
            default:
                break;
            case MESSAGE_NOTE_TITLE_TYPE:
                holder.setText(R.id.city_name, item.name);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.toastShortMessage(item.name);
                    }
                });
                break;
            case MESSAGE_NOTE_CONTENT_TYPE:
                String letter = item.pys.substring(0, 1);
                holder.setText(R.id.city_tip, letter);



                break;


        }
    }

    public int getLetterPosition(String letter){
        for (int i = 0 ; i < getData().size(); i++){
            if(getData().get(i).type ==1 && getData().get(i).pys.equals(letter)){
                return i;
            }
        }
        return -1;
    }

}
