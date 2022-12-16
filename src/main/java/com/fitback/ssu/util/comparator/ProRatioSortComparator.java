package com.fitback.ssu.util.comparator;

import com.fitback.ssu.domain.user.info.ProInfo;

import java.util.Comparator;

public class ProRatioSortComparator implements Comparator<ProInfo> {

    @Override
    public int compare(ProInfo p1, ProInfo p2) {
        if (p1.getSatisfactionRatio() < p2.getSatisfactionRatio()) {
            return 1;
        } else if (p1.getSatisfactionRatio() > p2.getSatisfactionRatio()) {
            return -1;
        }
        return 0;
    }
}
