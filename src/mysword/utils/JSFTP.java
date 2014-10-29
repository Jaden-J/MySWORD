package mysword.utils;

import com.jcraft.jsch.*;

import java.util.ArrayList;
import java.util.Vector;

public class JSFTP {

	@SuppressWarnings("static-access")
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
	
	public static void disconnet(ChannelSftp channel) throws JSchException {
		Session session = channel.getSession();
		channel.disconnect();
		session.disconnect();
	}

    public static ArrayList<String> list(String hostname, int port, String username, String password, String path) throws JSchException, SftpException {
        ArrayList<String> nameList = new ArrayList<String>();
        ChannelSftp sftp = null;
        try {
            sftp = getSftp(hostname, 22, username, password);
            Vector fileList = sftp.ls(path);
            System.out.println(fileList.size());
            for (Object obj : fileList) {
                ChannelSftp.LsEntry file = (ChannelSftp.LsEntry) obj;
                if(".".equals(file.getFilename()) || "..".equals(file.getFilename())) {
                    continue;
                }
                nameList.add(file.getFilename());
            }
            JSFTP.disconnet(sftp);
            return nameList;
        } catch (JSchException e) {
            e.printStackTrace();
            try {
                JSFTP.disconnet(sftp);
            } catch(Exception e1) {
                e1.printStackTrace();
            }
            throw e;
        } catch (SftpException e) {
            e.printStackTrace();
            try {
                JSFTP.disconnet(sftp);
            } catch(Exception e1) {
                e1.printStackTrace();
            }
            throw e;
        }
    }

    public static boolean existing(ChannelSftp channel, String path) {
		try {
			channel.ls(path);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean existingPath(ChannelSftp channel, String path) {
		try {
			channel.ls(path);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
