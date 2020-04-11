package com.hmx.test;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * SOCKETͨѶ����:��ѯ�˻���Ϣ
 */
public class SocketRequest {
	/**
	 * ����������
	 * 
	 * @return
	 */
	private String getRequestStr() {
		// �����ѯ����������
		XmlPacket xmlPkt = new XmlPacket("GetAccInfo", "USRA01");
		Map mpAccInfo = new Properties();
		mpAccInfo.put("BBKNBR", "57");
		mpAccInfo.put("ACCNBR", "571905400610301");
		xmlPkt.putProperty("SDKACINFX", mpAccInfo);
		return xmlPkt.toXmlString();
	}

	/**
	 * ����ǰ�û������������ģ���÷��ر���
	 * 
	 * @param data
	 * @return
	 */
	private String sendRequest(String data) {
		// ����ǰ�û���Ip + port
		String hostname = "localhost";
		int port = 8080;
		String result = null;
		try {
			InetAddress addr = InetAddress.getByName(hostname);
			Socket socket = new Socket(addr, port);

			// ����2����ͨѶ��ʱʱ��
			socket.setSoTimeout(120 * 1000);

			DataOutputStream wr = new DataOutputStream(socket.getOutputStream());

			// ͨѶͷΪ8λ���ȣ��Ҳ��ո��Ȳ���8λ�ո���ȡǰ8λ��Ϊ����ͷ
			String strLen = String.valueOf(data.getBytes().length) + "        ";
			wr.write((strLen.substring(0, 8) + data).getBytes());
			wr.flush();
			DataInputStream rd = new DataInputStream(socket.getInputStream());
			// ���շ��ر��ĵĳ���
			byte rcvLen[] = new byte[8];
			rd.read(rcvLen);
			String sLen = new String(rcvLen);
			int iSum = 0;
			try {
				iSum = Integer.valueOf(sLen.trim());
			} catch (NumberFormatException e) {
				System.out.println("����ͷ��ʽ����" + sLen);
			}
			if (iSum > 0) {
				System.out.println("��Ӧ���ĳ���:" + iSum);
				// ���շ��ر��ĵ�����	
				int nRecv = 0, nOffset = 0;
				byte[] rcvData = new byte[iSum];// data
				while (iSum > 0) {
					nRecv = rd.read(rcvData, nOffset, iSum);
					if (nRecv < 0)
						break;
					nOffset += nRecv;
					iSum -= nRecv;
				}
				result = new String(rcvData);
				System.out.println("��Ӧ��������:" + result);
			}
			wr.close();
			rd.close();
		} catch (SocketTimeoutException e) {
			System.out.println("ͨѶ��ʱ��" + e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return result;
	}

	/**
	 * �����صĽ��
	 * 
	 * @param result
	 */
	private void processResult(String result) {
		if (result != null && result.length() > 0) {
			XmlPacket pktRsp = XmlPacket.valueOf(result);
			if (pktRsp != null) {
				if (pktRsp.isError()) {
					System.out.println("ȡ�˻���Ϣʧ�ܣ�" + pktRsp.getERRMSG());
				} else {
					Map propAcc = pktRsp.getProperty("NTQACINFZ", 0);
					System.out.println("�˻�" + propAcc.get("ACCNBR") + "��������"
							+ propAcc.get("ONLBLV"));
				}
			} else {
				System.out.println("��Ӧ���Ľ���ʧ��");
			}
		}
	}

	public static void main(String[] args) {
		try {
			SocketRequest request = new SocketRequest();

			// ����������
			String data = request.getRequestStr();

			// ����ǰ�û������������ģ���÷��ر���
			String result = request.sendRequest(data);

			// �����صĽ��
			request.processResult(result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}