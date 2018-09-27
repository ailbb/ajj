package com.ailbb.ajj.date;

import org.springframework.scheduling.support.CronSequenceGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Wz on 9/25/2018.
 */
public class $Cron {
    // 0 10/15 * * * ?
    // {秒}  {分}  {时}  {日}  {月}  {周}
    private CronSequenceGenerator cronSequenceGenerator;
    private String patten;

    public $Cron() {}

    public $Cron(String patten) {
        init(patten);
    }

    public $Cron init(String patten){
        this.cronSequenceGenerator = new CronSequenceGenerator(this.patten = patten);
        return this;
    }

    public Date next(Date time) {
        return cronSequenceGenerator.next(time);
    }

    public List<Date> ranges(Date starttime, Date endtime) {
        List<Date> ld = new ArrayList<>();
        Date dt = starttime;

        while (dt.compareTo(endtime) < 0) {
            dt = next(dt);
            ld.add(dt);
        }

        return ld;
    }

    public String getPatten() {
        return patten;
    }

    public $Cron setPatten(String patten) {
        return init(patten);
    }

}
