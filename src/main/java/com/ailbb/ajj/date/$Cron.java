package com.ailbb.ajj.date;

import com.ailbb.ajj.$;
import org.springframework.scheduling.support.CronSequenceGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
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

    public $Cron init(String patten, boolean isUnix){
        List<String> cron = new ArrayList<>();
        String[] crons = patten.split("\\s+");
        for(int i=0; i<crons.length; i++) {
            String v = crons[i].replace("0/0", "0");

            cron.add(getCronValue(v, i, isUnix));
        }

        if(cron.size() > 6) cron = cron.subList(0,6);

        this.cronSequenceGenerator = new CronSequenceGenerator(this.patten = $.string.join(cron, " "));
        return this;
    }

    public $Cron init(String patten){
        return init(patten, true);
    }

    public String getCronValue(String v, int i) {
        // 如果是周，
        return getCronValue(v, i, false);
    }

    public String getCronValue(String v, int i, boolean isUnix) {
        // 如果是周，
        if(isUnix && i == 5 && $.test("\\d", v)) v = ($.parseInt(v)-1) + "";

        return v;
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
