package mysword.bean.system;

import java.util.ArrayList;

/**
 * Created by hyao on 7/6/2014.
 */
public class SystemAuthBean {
    private ArrayList<SystemAuthMacBean> mac = new ArrayList<SystemAuthMacBean>();
    private String hostname;
    private String username;
    private String sims;
    private String sims_password;
    private String region;
    private String email;
    private String extension;
    private String department;
    private String theme;
    private String userIndex;
    private boolean sysadmin;
    private boolean knowledge_sharing_admin;
    private boolean knowledge_sharing_user;
    private boolean developer;
    private boolean support;
    private String lasteditby;
    private String lasteditdt;

    public ArrayList<SystemAuthMacBean> getMac() {
        return mac;
    }

    public void setMac(ArrayList<SystemAuthMacBean> mac) {
        this.mac = mac;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSims() {
        return sims;
    }

    public void setSims(String sims) {
        this.sims = sims;
    }

    public String getSims_password() {
        return sims_password;
    }

    public void setSims_password(String sims_password) {
        this.sims_password = sims_password;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getUserIndex() {
        return userIndex;
    }

    public void setUserIndex(String userIndex) {
        this.userIndex = userIndex;
    }

    public boolean isSysadmin() {
        return sysadmin;
    }

    public void setSysadmin(boolean sysadmin) {
        this.sysadmin = sysadmin;
    }

    public boolean isKnowledge_sharing_admin() {
        return knowledge_sharing_admin;
    }

    public void setKnowledge_sharing_admin(boolean knowledge_sharing_admin) {
        this.knowledge_sharing_admin = knowledge_sharing_admin;
    }

    public boolean isKnowledge_sharing_user() {
        return knowledge_sharing_user;
    }

    public void setKnowledge_sharing_user(boolean knowledge_sharing_user) {
        this.knowledge_sharing_user = knowledge_sharing_user;
    }

    public boolean isDeveloper() {
        return developer;
    }

    public void setDeveloper(boolean developer) {
        this.developer = developer;
    }

    public boolean isSupport() {
        return support;
    }

    public void setSupport(boolean support) {
        this.support = support;
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
