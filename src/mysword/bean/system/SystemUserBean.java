package mysword.bean.system;

import java.util.ArrayList;

public class SystemUserBean {

	private String username;
	private String password;
	private String sims;
	private String img;
	private String department;
	private String email;
	private String server_Number;
	private String server_Username;
	private String server_Password;
	private ArrayList<SystemRoleBean> roles = new ArrayList<SystemRoleBean>();
	private String lasteditby;
	private String lasteditdt;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSims() {
		return sims;
	}
	public void setSims(String sims) {
		this.sims = sims;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getServer_Number() {
		return server_Number;
	}
	public void setServer_Number(String server_Number) {
		this.server_Number = server_Number;
	}
	public String getServer_Username() {
		return server_Username;
	}
	public void setServer_Username(String server_Username) {
		this.server_Username = server_Username;
	}
	public String getServer_Password() {
		return server_Password;
	}
	public void setServer_Password(String server_Password) {
		this.server_Password = server_Password;
	}
	public ArrayList<SystemRoleBean> getRoles() {
		return roles;
	}
	public void setRoles(ArrayList<SystemRoleBean> roles) {
		this.roles = roles;
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
