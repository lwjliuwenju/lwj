package com.sj.common.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class MACAddressUtils {
	// 获取MAC地址的方法
	public static String getMACAddress() throws Exception {
		InetAddress ia = InetAddress.getLocalHost();// 获取本地IP对象
		// 获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
		byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();

		// 下面代码是把mac地址拼装成String
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < mac.length; i++) {
			if (i != 0) {
				sb.append("-");
			}
			// mac[i] & 0xFF 是为了把byte转化为正整数
			String s = Integer.toHexString(mac[i] & 0xFF);
			sb.append(s.length() == 1 ? 0 + s : s);
		}

		// 把字符串所有小写字母改为大写成为正规的mac地址并返回
		return sb.toString().toUpperCase();
	}

	// 获取所有网卡的MAC地址
	public static List<String> getAllMac() {
		List<String> list = new ArrayList<String>();
		try {
			Enumeration<NetworkInterface> e = NetworkInterface
					.getNetworkInterfaces();// 返回所有网络接口的一个枚举实例
			while (e.hasMoreElements()) {
				NetworkInterface network = e.nextElement();// 获得当前网络接口
				if (network != null) {
					if (network.getHardwareAddress() != null) {
						// 获得MAC地址
						// 结果是一个byte数组，每项是一个byte，我们需要通过parseByte方法转换成常见的十六进制表示
						byte[] addres = network.getHardwareAddress();
						StringBuffer sb = new StringBuffer();
						if (addres != null && addres.length > 1) {
							sb.append(parseByte(addres[0])).append(":")
									.append(parseByte(addres[1])).append(":")
									.append(parseByte(addres[2])).append(":")
									.append(parseByte(addres[3])).append(":")
									.append(parseByte(addres[4])).append(":")
									.append(parseByte(addres[5]));
							list.add(sb.toString());
						}
					}
				} else {
					System.out.println("获取MAC地址发生异常");
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 格式化二进制
	private static String parseByte(byte b) {
		int intValue = 0;
		if (b >= 0) {
			intValue = b;
		} else {
			intValue = 256 + b;
		}
		return Integer.toHexString(intValue);
	}

	public static String macPaser(String macStr) {
		//下面代码是把mac地址拼装成String  
        StringBuilder sb = new StringBuilder();
        String[] macs = macStr.split(":");
        for (int i = 0; i < macs.length; i++) {  
            if (i != 0) {  
                sb.append("-");  
            }  
            String s = macs[i];
            sb.append(s.length() == 1 ? 0 + s : s);  
        }  
        //把字符串所有小写字母改为大写成为正规的mac地址并返回  
        return sb.toString().trim().toUpperCase();
	}
}
