package mysword.bean.knowledgebase;

/**
 * Created by hyao on 7/25/2014.
 */
public class ServerBean {
    private String environment;
    private String title;
    private String address;
    private String hostname;
    private String description;
    private String lasteditby;
    private String lasteditdt;

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLasteditby() {
        return lasteditby;
    }

    public void setLasteditby(String lasteditby) {
        this.lasteditby = lasteditby;
    }

    public String getLasteditdt() {
        return lasteditdt;
    }

    public void setLasteditdt(String lasteditdt) {
        this.lasteditdt = lasteditdt;
    }
}
