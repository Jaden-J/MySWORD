package mysword.utils;

import com.jcraft.jsch.*;
import org.apache.commons.collections.map.ListOrderedMap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

public class J2SSH1 {
	
	@SuppressWarnings("static-access")
	public static boolean command(String command, StringBuffer logs, String hostname, int port, String username, String password) throws JSchException, InterruptedException, IOException {
		Channel channel = null;
		Session session = null;
		try {
			JSch jsch = new JSch();
			jsch.setConfig("PreferredAuthentications", "password");
			session = jsch.getSession(username, hostname, port);
			session.setPassword(password);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect(6000);

			channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);
			OutputStream outStream = channel.getOutputStream();
			InputStream inputStream = channel.getInputStream();
			((ChannelExec) channel).setAgentForwarding(true);
			channel.connect(6000);
			for (int i = 0; i < 5; i++) {
				Thread.sleep(1000);
				if (inputStream.available() > 0)
					break;
				i++;
			}
			if (inputStream.available() > 0) {
				byte[] data = new byte[inputStream.available()];
				inputStream.read(data);
				logs.append(new String(data));
			}
			outStream.close();
			inputStream.close();
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
            return true;
		} catch (JSchException e) {
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
            logs.append(e.toString() + "\n");
            throw e;
        } catch (InterruptedException e) {
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
            logs.append(e.toString() + "\n");
            throw e;
        } catch (IOException e) {
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
            logs.append(e.toString() + "\n");
            throw e;
        }
//		} finally {
//            if(channel != null) {
//                result = channel.getExitStatus()==0?true:false;
//                channel.disconnect();
//            }
//            if(session != null) {
//                session.disconnect();
//            }
//		}
//		return result;
	}

	@SuppressWarnings("static-access")
	public static HashMap<String, Object> exeCommand(String command,
			String hostname, int port, String username, String password)
			throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		StringBuffer logString = new StringBuffer();
		StringBuffer errorString = new StringBuffer();
		Channel channel = null;
		Session session = null;
		try {
			JSch jsch = new JSch();
			jsch.setConfig("PreferredAuthentications", "password");
			session = jsch.getSession(username, hostname, port);
			session.setPassword(password);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect(3000);
			logString.append("Login successfully!\n");

			channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);
			OutputStream outStream = channel.getOutputStream();
			InputStream inputStream = channel.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			((ChannelExec) channel).setErrStream(baos);
			((ChannelExec) channel).setAgentForwarding(true);
			channel.connect(3000);
			logString.append("Run command successfully!\n");

			for (int i = 0; i < 5; i++) {
				Thread.sleep(1000);
				if (inputStream.available() > 0)
					break;
				i++;
			}

			if (inputStream.available() > 0) {
				logString.append("-------Result Begin-------\n");
				byte[] data = new byte[inputStream.available()];
				int nLen = inputStream.read(data);
				
				if (nLen < 0) {
					throw new Exception("network error.");
				}

				resultMap.put("result", new String(data));
				logString.append(new String(data)
						+ "-------Result End-------\n");
			}
			errorString.append(new String(baos.toByteArray()) + "\n");

			baos.close();
			outStream.close();
			inputStream.close();
		} catch (Exception e) {
			errorString.append(e.toString() + "\n");
			e.printStackTrace();
		} finally {
            if(channel != null && !channel.isClosed()) {
                channel.disconnect();
            }
            if(session != null && session.isConnected()) {
                session.disconnect();
            }
			logString.append("Close connection successfully!\n");
		}
		resultMap.put("logs", logString);
		resultMap.put("errors", errorString);
		return resultMap;
	}

	@SuppressWarnings("static-access")
	public static String shell(String shells[], String hostname, int port, String username, String password)
			throws Exception {
		StringBuffer logString = new StringBuffer();
		Channel channel = null;
		Session session = null;
		try {
			JSch jsch = new JSch();
			JSch.setConfig("PreferredAuthentications", "password");
			session = jsch.getSession(username, hostname, port);
			session.setPassword(password);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect(3000);

			channel = session.openChannel("shell");
			OutputStream outStream = channel.getOutputStream();
			((ChannelShell) channel).setAgentForwarding(true);
			channel.connect(3000);

			for (int i = 0; i < shells.length; i++) {
				if(!shells[i].endsWith("\n")) {
					shells[i] = shells[i] + "\n";
				}
				outStream.write(shells[i].getBytes());
				outStream.flush();
				
				InputStream inputStream = channel.getInputStream();
				
				for (int j = 0; j < 5; j++) {
					Thread.sleep(1000);
					if (inputStream.available() > 0)
						break;
					j++;
				}
				
				StringBuffer shell_result = new StringBuffer("");
				if (inputStream.available() > 0) {
					byte[] data = new byte[2048];
					int length = inputStream.available();
					while (2048 < length) {
						inputStream.read(data);
						shell_result.append(new String(data));
						length -= 2048;
					}
					if (length > 0) {
						data = new byte[length];
						inputStream.read(data);
						shell_result.append(new String(data));
					}
				}
				
				logString.append(shell_result.toString());
				inputStream.close();
			}
			
			outStream.close();
		} catch (Exception e) {
			logString.append(e.toString() + "\n");
			e.printStackTrace();
		} finally {
            if(channel != null && !channel.isClosed()) {
                channel.disconnect();
            }
            if(session != null && session.isConnected()) {
                session.disconnect();
            }
		}
		return logString.toString();
	}
	
	@SuppressWarnings("static-access")
	public static HashMap<String, Object> exeShell(String shells[],
			String hostname, int port, String username, String password)
			throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		ListOrderedMap lom = new ListOrderedMap();
		StringBuffer errorString = new StringBuffer();
		Channel channel = null;
		Session session = null;
		try {
			JSch jsch = new JSch();
			jsch.setConfig("PreferredAuthentications", "password");
			session = jsch.getSession(username, hostname, port);
			session.setPassword(password);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect(3000);
			lom.put("Start Connection", "Login successfully!\n");

			channel = session.openChannel("shell");
			OutputStream outStream = channel.getOutputStream();
			((ChannelShell) channel).setAgentForwarding(true);
			channel.connect(3000);

			lom.put("Open Shell", "-------Result Begin-------\n");
			for (int i = 0; i < shells.length; i++) {
				outStream.write(shells[i].getBytes());
				outStream.flush();
				
				InputStream inputStream = channel.getInputStream();
				
				for (int j = 0; j < 5; j++) {
					Thread.sleep(1000);
					if (inputStream.available() > 0)
						break;
					j++;
				}
				
				StringBuffer shell_result = new StringBuffer("");
				if (inputStream.available() > 0) {
					byte[] data = new byte[2048];
					int length = inputStream.available();
					while (2048 < length) {
						inputStream.read(data);
						shell_result.append(new String(data));
						length -= 2048;
					}
					if (length > 0) {
						data = new byte[length];
						inputStream.read(data);
						shell_result.append(new String(data));
					}
				}
				
				lom.put(shells[i], shell_result.toString());
				inputStream.close();
			}
			lom.put("Close Shell", "\n-------Result End-------\n");
			
			outStream.close();
		} catch (Exception e) {
			errorString.append(e.toString() + "\n");
			e.printStackTrace();
		} finally {
            if(channel != null && !channel.isClosed()) {
                channel.disconnect();
            }
            if(session != null && session.isConnected()) {
                session.disconnect();
            }
			lom.put("Close Connection", "Close connection successfully!\n");
		}
		resultMap.put("logs", lom);
		resultMap.put("errors", errorString);
		return resultMap;
	}
	
	@SuppressWarnings("static-access")
	public static HashMap<String, Object> comsysShell(String type, String agreementId, String hostname, String username, String password, String simsName, int comsys) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		ListOrderedMap lom = new ListOrderedMap();
		StringBuffer errorString = new StringBuffer("");
		String[] shells = null;
		if("run".equals(type)) {
			shells = new String[] { "sudo su - xib\n", password + "\n", "run " + agreementId + "\n", comsys + "\n" };
		} else if("reload".equals(type)) {
			shells = new String[] { "sudo su - xib\n", password + "\n", "reload " + agreementId + "\n", simsName + "\n", comsys + "\n" };
		} else {
			return new HashMap<String, Object>();
		}
		Channel channel = null;
		Session session = null;
		try {
			JSch jsch = new JSch();
			jsch.setConfig("PreferredAuthentications", "password");
			session = jsch.getSession(username, hostname, 22);
			session.setPassword(password);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect(6000);
			lom.put("Start Connection", "Login successfully!\n");

			channel = session.openChannel("shell");
			OutputStream outStream = channel.getOutputStream();
			((ChannelShell) channel).setAgentForwarding(true);
			channel.connect(6000);

			lom.put("Open Shell", "-------Result Begin-------\n");
			String temp_result = "";
			for (int i = 0; i < shells.length; i++) {
				outStream.write(shells[i].getBytes());
				outStream.flush();
				
				InputStream inputStream = channel.getInputStream();
				
				for (int j = 0; j < 5; j++) {
					Thread.sleep(1000);
					if (inputStream.available() > 0)
						break;
					j++;
				}
				temp_result = inputStreamToString(inputStream);
				lom.put(shells[i], temp_result);
				inputStream.close();
			}
			
			if(!temp_result.endsWith("$ ")){
				shells = new String[]{"y\n", "y\n", ":q\n", "n\n"};
				for (int i = 0; i < shells.length; i++) {
					outStream.write(shells[i].getBytes());
					outStream.flush();
					
					InputStream inputStream = channel.getInputStream();
					
					for (int j = 0; j < 5; j++) {
						Thread.sleep(1000);
						if (inputStream.available() > 0)
							break;
						j++;
					}
					lom.put(shells[i], inputStreamToString(inputStream));
					inputStream.close();
				}
			}
			lom.put("Close Shell", "\n-------Result End-------\n");
			outStream.close();
		} catch (Exception e) {
			errorString.append(e.toString() + "\n");
			e.printStackTrace();
		} finally {
            if(channel != null) {
                channel.disconnect();
            }
            if(session != null) {
                session.disconnect();
            }
			lom.put("Close Connection", "Close connection successfully!\n");
		}
		resultMap.put("logs", lom);
		resultMap.put("errors", errorString);
		return resultMap;
	}
	
	public static String inputStreamToString(InputStream inputStream) throws Exception {
		StringBuffer strBuffer = new StringBuffer("");
		if (inputStream.available() > 0) {
			byte[] data = new byte[2048];
			int length = inputStream.available();
			while (2048 < length) {
				inputStream.read(data);
				strBuffer.append(new String(data));
				length -= 2048;
			}
			if (length > 0) {
				data = new byte[length];
				inputStream.read(data);
				strBuffer.append(new String(data));
			}
		}
		return strBuffer.toString();
	}
}
