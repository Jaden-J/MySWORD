package mysword.bean.system;

public class SystemConfBean {

	private String config_name;
	private String config_value;
    private String config_type;
	private String config_desc;
    private boolean config_req;
	private String lasteditby;
	private String lasteditdt;

    public String getConfig_name() {
        return config_name;
    }

    public void setConfig_name(String config_name) {
        this.config_name = config_name;
    }

    public String getConfig_value() {
        return config_value;
    }

    public void setConfig_value(String config_value) {
        this.config_value = config_value;
    }

    public String getConfig_type() {
        return config_type;
    }

    public void setConfig_type(String config_type) {
        this.config_type = config_type;
    }

    public String getConfig_desc() {
        return config_desc;
    }

    public void setConfig_desc(String config_desc) {
        this.config_desc = config_desc;
    }

    public boolean isConfig_req() {
        return config_req;
    }

    public void setConfig_req(boolean config_req) {
        this.config_req = config_req;
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
