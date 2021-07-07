package com.ailbb.ajj.entity;

import java.util.Map;

/*
 * Created by Wz on 8/23/2018.
 */
public class $Condition {
    private String starttime;
    private String endtime;
    private int page = 1;
    private int pagesize = 50;

    public String getStarttime() {
        return starttime;
    }

    public $Condition setStarttime(String starttime) {
        this.starttime = starttime;
        return this;
    }

    public String getEndtime() {
        return endtime;
    }

    public $Condition setEndtime(String endtime) {
        this.endtime = endtime;
        return this;
    }

    public int getPage() {
        return page;
    }

    public $Condition setPage(int page) {
        this.page = page;
        return this;
    }

    public int getPagesize() {
        return pagesize;
    }

    public $Condition setPagesize(int pagesize) {
        this.pagesize = pagesize;
        return this;
    }

}
