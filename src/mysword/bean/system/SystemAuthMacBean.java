package mysword.bean.system;

/**
 * Created by hyao on 8/22/2014.
 */
public class SystemAuthMacBean {
    private String mac;
    private String username;
    private String description;
    private String lasteditdt;
    private String lasteditby;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLasteditdt() {
        return lasteditdt;
    }

    public void setLasteditdt(String lasteditdt) {
        this.lasteditdt = lasteditdt;
    }

    public String getLasteditby() {
        return lasteditby;
    }

    public void setLasteditby(String lasteditby) {
        this.lasteditby = lasteditby;
    }
}
