package mysword.utils;

import com.jcraft.jsch.*;

public class JSFTPFactory {

	public static ChannelSftp getSftp(String hostname, int port, String username, String password) throws JSchException {
		JSch jsch=new JSch();
		jsch.setConfig("PreferredAuthentications", "password");
		Session session=jsch.getSession(username, hostname, port);
		session.setPassword(password);
		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
	    session.connect(3000);
	    Channel channel=session.openChannel("sftp");
	    channel.connect();
	    return (ChannelSftp)channel;
	}
	
	public static void disconnet(Channel channel) throws JSchException {
		Session session = channel.getSession();
		channel.disconnect();
		session.disconnect();
	}
}
