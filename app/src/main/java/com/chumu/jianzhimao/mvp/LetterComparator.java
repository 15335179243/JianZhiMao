package com.chumu.jianzhimao.mvp;


import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.chumu.jianzhimao.R;
import com.chumu.jianzhimao.mvp.bean.City;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class LetterComparator implements Comparator<City> {

    @Override
    public int compare(City l, City r) {
        if (l == null || r == null) {
            return 0;
        }

        String lhsSortLetters = l.pys.substring(0, 1).toUpperCase();
        String rhsSortLetters = r.pys.substring(0, 1).toUpperCase();
        if (lhsSortLetters == null || rhsSortLetters == null) {
            return 0;
        }
        return lhsSortLetters.compareTo(rhsSortLetters);
    }
}

