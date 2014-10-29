package mysword.bean.project;

/**
 * Created by hyao on 7/20/2014.
 */
public class ProjectStructureBean {
    private String region;
    private String projectTypeId;
    private String[] types;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getProjectTypeId() {
        return projectTypeId;
    }

    public void setProjectTypeId(String projectTypeId) {
        this.projectTypeId = projectTypeId;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }
}
