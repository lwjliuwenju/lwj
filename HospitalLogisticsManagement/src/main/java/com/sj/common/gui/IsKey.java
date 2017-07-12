package com.sj.common.gui;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import com.sj.common.utils.EncryptUtil;
import com.sj.common.utils.MACAddressUtils;

public class IsKey {
	public static void main(String[] arguments) throws Exception {
		MainGui gui11 = new MainGui();
		System.out.println("MAC ......... " + MACAddressUtils.getMACAddress());
		MACAddressUtils.getAllMac().forEach(e -> System.out.print(e + "\n"));
	}
	public static boolean idKey() {
		if(!new File("key.lsj").exists())
			new MainGui();
		else{
			try {
				Properties prop = new Properties(); 
				InputStream in = new BufferedInputStream (new FileInputStream("key.lsj"));
				prop.load(in);
				String userMD = prop.getProperty("user");
				String passMD = prop.getProperty("pass");
				String macText = prop.getProperty("key");
				in.close();
				List<String> allMac = MACAddressUtils.getAllMac();
				for (String string : allMac) {
					String macMD = EncryptUtil.encrypt(MACAddressUtils.macPaser(string));
					String key = EncryptUtil.encrypt(userMD.substring(0, 8) + macMD.substring(1, 9) + passMD.substring(2, 10)).substring(0, 24);
					if(macText.equals(key))
						return true;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}
		return MainGui.isKey;
	}
}
