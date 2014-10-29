package mysword.bean.tools;

public class MetadataBean {

	private String schemaType;
	private String schemaName;
	private String schemaPath;
	public String getSchemaType() {
		return schemaType;
	}
	public void setSchemaType(String schemaType) {
		this.schemaType = schemaType;
	}
	public String getSchemaName() {
		return schemaName;
	}
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	public String getSchemaPath() {
		return schemaPath;
	}
	public void setSchemaPath(String schemaPath) {
		this.schemaPath = schemaPath;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		
		return schemaType + ",," + schemaName + "," + schemaPath + ",";
	}
}
