package com.sj.common.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class MACAddressUtils {
	// ��ȡMAC��ַ�ķ���
	public static String getMACAddress() throws Exception {
		InetAddress ia = InetAddress.getLocalHost();// ��ȡ����IP����
		// �������ӿڶ��󣨼������������õ�mac��ַ��mac��ַ������һ��byte�����С�
		byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();

		// ��������ǰ�mac��ַƴװ��String
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < mac.length; i++) {
			if (i != 0) {
				sb.append("-");
			}
			// mac[i] & 0xFF ��Ϊ�˰�byteת��Ϊ������
			String s = Integer.toHexString(mac[i] & 0xFF);
			sb.append(s.length() == 1 ? 0 + s : s);
		}

		// ���ַ�������Сд��ĸ��Ϊ��д��Ϊ�����mac��ַ������
		return sb.toString().toUpperCase();
	}

	// ��ȡ����������MAC��ַ
	public static List<String> getAllMac() {
		List<String> list = new ArrayList<String>();
		try {
			Enumeration<NetworkInterface> e = NetworkInterface
					.getNetworkInterfaces();// ������������ӿڵ�һ��ö��ʵ��
			while (e.hasMoreElements()) {
				NetworkInterface network = e.nextElement();// ��õ�ǰ����ӿ�
				if (network != null) {
					if (network.getHardwareAddress() != null) {
						// ���MAC��ַ
						// �����һ��byte���飬ÿ����һ��byte��������Ҫͨ��parseByte����ת���ɳ�����ʮ�����Ʊ�ʾ
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
					System.out.println("��ȡMAC��ַ�����쳣");
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return list;
	}

	// ��ʽ��������
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
		//��������ǰ�mac��ַƴװ��String  
        StringBuilder sb = new StringBuilder();
        String[] macs = macStr.split(":");
        for (int i = 0; i < macs.length; i++) {  
            if (i != 0) {  
                sb.append("-");  
            }  
            String s = macs[i];
            sb.append(s.length() == 1 ? 0 + s : s);  
        }  
        //���ַ�������Сд��ĸ��Ϊ��д��Ϊ�����mac��ַ������  
        return sb.toString().trim().toUpperCase();
	}
}
