package com.zy_admin.common;

import java.util.List;

public class Page<T> {
    private List<T> content;   //数据集合
    private long page;  //页码号
    private long total;  //总数据量
    private long pages; //总页码数据

    public Page() {
    }

    public Page(List<T> content, long page, long total, long pages) {
        this.content = content;
        this.page = page;
        this.total = total;
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "Page{" +
                "content=" + content +
                ", page=" + page +
                ", total=" + total +
                ", pages=" + pages +
                '}';
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }
}
