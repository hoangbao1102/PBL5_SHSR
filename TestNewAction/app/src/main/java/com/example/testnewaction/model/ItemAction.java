package com.example.testnewaction.model;

import java.io.Serializable;

public class ItemAction implements Serializable {

    private String actionid;
    private String actionname;
    private long status, status1, status2, status3, status4;

    public ItemAction() {

    }

    public ItemAction(String actionid, String actionname, long status, long status1, long status2, long status3, long status4) {
        this.actionid = actionid;
        this.actionname = actionname;
        this.status = status;
        this.status1 = status1;
        this.status2 = status2;
        this.status3 = status3;
        this.status4 = status4;
    }

    public String getActionname() {
        return actionname;
    }

    public String getActionid() {
        return actionid;
    }

    public long getStatus() {
        return status;
    }

    public long getStatus1() {
        return status1;
    }

    public long getStatus2() {
        return status2;
    }

    public long getStatus3() {
        return status3;
    }

    public long getStatus4() {
        return status4;
    }

    public void setActionid(String actionid) {
        this.actionid = actionid;
    }

    public void setActionname(String actionname) {
        this.actionname = actionname;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public void setStatus1(long status1) {
        this.status1 = status1;
    }

    public void setStatus2(long status2) {
        this.status2 = status2;
    }

    public void setStatus3(long status3) {
        this.status3 = status3;
    }

    public void setStatus4(long status4) {
        this.status4 = status4;
    }
}
