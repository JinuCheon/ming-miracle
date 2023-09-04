package org.example.domain;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;

public class BreadCrumbs {

    private final Deque<Page> pages = new ArrayDeque<>();

    public void addPrevious(final Page page) {
        pages.addFirst(page);
    }

    public Collection<Page> getPages() {
        return Collections.unmodifiableCollection(pages);
    }
}
