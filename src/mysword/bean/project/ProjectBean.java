package mysword.bean.project;

/**
 * Created by halberd on 2014/6/22 0022.
 */
public class ProjectBean {
    private String itemId="";
    private String lockId="";
    private String week="";
    private String region="";
    private String item_Type ="";
    private String scriptId="";
    private String GSD="";
    private String projectName="";
    private String coordinator="";
    private String customer="";
    private String category="";
    private String priority="";
    private String liveOnTest="";
    private String liveOnProd="";
    private String docLink="";
    private String developer="";
    private int maps;
    private float estimateEffort;
    private float realEffort;
    private float pendingEffort;
    private float restEffort;
    private float underEstimatedEffort;
    private String item_Comment ="";
    private String lastEditDT="";
    private String lastEditBy="";

    public int getMaps() {
        return maps;
    }

    public void setMaps(int maps) {
        this.maps = maps;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getLockId() {
        return lockId;
    }

    public void setLockId(String lockId) {
        this.lockId = lockId;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getItem_Type() {
        return item_Type;
    }

    public void setItem_Type(String item_Type) {
        this.item_Type = item_Type;
    }

    public String getScriptId() {
        return scriptId;
    }

    public void setScriptId(String scriptId) {
        this.scriptId = scriptId;
    }

    public String getGSD() {
        return GSD;
    }

    public void setGSD(String GSD) {
        this.GSD = GSD;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(String coordinator) {
        this.coordinator = coordinator;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getLiveOnTest() {
        return liveOnTest;
    }

    public void setLiveOnTest(String liveOnTest) {
        this.liveOnTest = liveOnTest;
    }

    public String getLiveOnProd() {
        return liveOnProd;
    }

    public void setLiveOnProd(String liveOnProd) {
        this.liveOnProd = liveOnProd;
    }

    public String getDocLink() {
        return docLink;
    }

    public void setDocLink(String docLink) {
        this.docLink = docLink;
    }

    public float getEstimateEffort() {
        return estimateEffort;
    }

    public void setEstimateEffort(float estimateEffort) {
        this.estimateEffort = estimateEffort;
    }

    public float getRealEffort() {
        return realEffort;
    }

    public void setRealEffort(float realEffort) {
        this.realEffort = realEffort;
    }

    public float getPendingEffort() {
        return pendingEffort;
    }

    public void setPendingEffort(float pendingEffort) {
        this.pendingEffort = pendingEffort;
    }

    public float getRestEffort() {
        return restEffort;
    }

    public void setRestEffort(float restEffort) {
        this.restEffort = restEffort;
    }

    public float getUnderEstimatedEffort() {
        return underEstimatedEffort;
    }

    public void setUnderEstimatedEffort(float underEstimatedEffort) {
        this.underEstimatedEffort = underEstimatedEffort;
    }

    public String getItem_Comment() {
        return item_Comment;
    }

    public void setItem_Comment(String item_Comment) {
        this.item_Comment = item_Comment;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getLastEditDT() {
        return lastEditDT;
    }

    public void setLastEditDT(String lastEditDT) {
        this.lastEditDT = lastEditDT;
    }

    public String getLastEditBy() {
        return lastEditBy;
    }

    public void setLastEditBy(String lastEditBy) {
        this.lastEditBy = lastEditBy;
    }
}
