package com.oxygen.yallagoom.widget.compactcalendarview.comparators;


import com.oxygen.yallagoom.widget.compactcalendarview.domain.Event;

import java.util.Comparator;

public class EventComparator implements Comparator<Event> {

    @Override
    public int compare(Event lhs, Event rhs) {
        return lhs.getTimeInMillis() < rhs.getTimeInMillis() ? -1 : lhs.getTimeInMillis() == rhs.getTimeInMillis() ? 0 : 1;
    }
}
