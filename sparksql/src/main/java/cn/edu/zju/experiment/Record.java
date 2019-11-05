package cn.edu.zju.experiment;

/**
 * @author raozihao
 * @date 2019/10/28
 */
public class Record {
    /**
     * time search
     */
    String ts;
    /**
     * user's id
     */
    String uid;
    /**
     * search keyword.
     */
    String keyword;
    /**
     * rank in returned result
     */
    int rank;
    /**
     * click order
     */
    int order;
    /**
     * search url.
     */
    String url;
    /**
     * null in original data
     */
    int year;
    /**
     * null in original data
     */
    int month;
    /**
     * null in original data
     */
    int day;
    /**
     * null in original data
     */
    int hour;

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }
}
