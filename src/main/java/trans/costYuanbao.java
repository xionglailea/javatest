package trans;

/**
 * Created by xiong on 2016/10/12.
 */
public class costYuanbao {
    private String logtime;
    private String logname;
    private int serverid;
    private String os;
    private String platform;
    private String userid;
    private String account;
    private long roleid;
    private String rolename;
    private int lev;
    private int totalcash;
    private int yuanbaotype;
    private int yuanbaopath;
    private int yuanbao;

    public String getLogtime() {
        return logtime;
    }

    public String getLogname() {
        return logname;
    }

    public int getServerid() {
        return serverid;
    }

    public String getOs() {
        return os;
    }

    public String getPlatform() {
        return platform;
    }

    public String getUserid() {
        return userid;
    }

    public String getAccount() {
        return account;
    }

    public long getRoleid() {
        return roleid;
    }

    public String getRolename() {
        return rolename;
    }

    public int getLev() {
        return lev;
    }

    public int getTotalcash() {
        return totalcash;
    }

    public int getYuanbaotype() {
        return yuanbaotype;
    }

    public int getYuanbaopath() {
        return yuanbaopath;
    }

    public int getYuanbao() {
        return yuanbao;
    }

    public void setLogtime(String logtime) {
        this.logtime = logtime;
    }

    public void setLogname(String logname) {
        this.logname = logname;
    }

    public void setServerid(int serverid) {
        this.serverid = serverid;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setRoleid(long roleid) {
        this.roleid = roleid;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public void setLev(int lev) {
        this.lev = lev;
    }

    public void setTotalcash(int totalcash) {
        this.totalcash = totalcash;
    }

    public void setYuanbaotype(int yuanbaotype) {
        this.yuanbaotype = yuanbaotype;
    }

    public void setYuanbaopath(int yuanbaopath) {
        this.yuanbaopath = yuanbaopath;
    }

    public void setYuanbao(int yuanbao) {
        this.yuanbao = yuanbao;
    }

    @Override
    public String toString() {
        return getRoleid() + " " + getRolename() + " " + getYuanbaotype() + " " + getYuanbao();
    }
}
