package it.eng.saceriam.ws.dto;

import java.util.Date;

/**
 *
 * @author Fioravanti_F
 */
public class RispostaControlli implements java.io.Serializable {

    private long rLong = -1;
    private long rLongExtended = -1;
    private int rInt = -1;
    private String rString = null;
    private boolean rBoolean = false;
    private Object rObject = null;
    private Date rDate = null;
    private String codErr = null;
    private String dsErr = null;

    public void init() {
        codErr = null;
        dsErr = null;
    }

    public void reset() {
        rLong = -1;
        rLongExtended = -1;
        rInt = -1;
        rBoolean = false;
        rString = null;
        codErr = null;
        dsErr = null;
        rObject = null;
        rDate = null;
    }

    public String getCodErr() {
        return codErr;
    }

    public void setCodErr(String codErr) {
        this.codErr = codErr;
    }

    public String getDsErr() {
        return dsErr;
    }

    public void setDsErr(String dsErr) {
        this.dsErr = dsErr;
    }

    public boolean isrBoolean() {
        return rBoolean;
    }

    public void setrBoolean(boolean rBoolean) {
        this.rBoolean = rBoolean;
    }

    public Date getrDate() {
        return rDate;
    }

    public void setrDate(Date rDateTime) {
        this.rDate = rDateTime;
    }

    public int getrInt() {
        return rInt;
    }

    public void setrInt(int rInt) {
        this.rInt = rInt;
    }

    public long getrLong() {
        return rLong;
    }

    public void setrLong(long rLong) {
        this.rLong = rLong;
    }

    public long getrLongExtended() {
        return rLongExtended;
    }

    public void setrLongExtended(long rLongExtended) {
        this.rLongExtended = rLongExtended;
    }

    public Object getrObject() {
        return rObject;
    }

    public void setrObject(Object rObject) {
        this.rObject = rObject;
    }

    public String getrString() {
        return rString;
    }

    public void setrString(String rString) {
        this.rString = rString;
    }
}