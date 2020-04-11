package com.hmx.test;

import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.Properties;

/**
 * 
 * HTTPͨѶ����: ֱ��֧��
 * 
 */
public class HttpRequest {
	/**
	 * ����������
	 * 
	 * @return
	 */
	private String getRequestStr() {
		// ����֧����������
		XmlPacket xmlPkt = new XmlPacket("Payment", "USRA01");
		Map mpPodInfo = new Properties();
		mpPodInfo.put("BUSCOD", "N02031");
		xmlPkt.putProperty("SDKPAYRQX", mpPodInfo);
		Map mpPayInfo = new Properties();
		mpPayInfo.put("YURREF", "201009270002");
		mpPayInfo.put("DBTACC", "571905400910411");
		mpPayInfo.put("DBTBBK", "57");
		mpPayInfo.put("DBTBNK", "�������к��ݷ���Ӫҵ��");
		mpPayInfo.put("DBTNAM", "NEXT TEST");
		mpPayInfo.put("DBTREL", "0000007715");
		mpPayInfo.put("TRSAMT", "1.01");
		mpPayInfo.put("CCYNBR", "10");
		mpPayInfo.put("STLCHN", "N");
		mpPayInfo.put("NUSAGE", "���ñ�����");
		mpPayInfo.put("CRTACC", "571905400810812");
		mpPayInfo.put("CRTNAM", "�����տ");
		mpPayInfo.put("CRTBNK", "��������");
		mpPayInfo.put("CTYCOD", "ZJHZ");
		mpPayInfo.put("CRTSQN", "ժҪ��Ϣ:[1.01]");
		xmlPkt.putProperty("SDKPAYDTX", mpPayInfo);
		return xmlPkt.toXmlString();
	}

	/**
	 * ����ǰ�û������������ģ���÷��ر���
	 * 
	 * @param data
	 * @return
	 * @throws MalformedURLException
	 */
	private String sendRequest(String data) {
		String result = "";
		try {
			URL url;
			url = new URL("http://localhost:8091");

			HttpURLConnection conn;
			conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			OutputStream os;
			os = conn.getOutputStream();
			os.write(data.toString().getBytes("gbk"));
			os.close();

			BufferedReader br = new BufferedReader(new InputStreamReader(conn
					.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				result += line;
			}

			System.out.println(result);
			br.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
				String sRetCod = pktRsp.getRETCOD();
				if (sRetCod.equals("0")) {
					Map propPayResult = pktRsp.getProperty("NTQPAYRQZ", 0);
					String sREQSTS = (String) propPayResult.get("REQSTS");
					String sRTNFLG = (String) propPayResult.get("RTNFLG");
					if (sREQSTS.equals("FIN") && sRTNFLG.equals("F")) {
						System.out.println("֧��ʧ�ܣ�"
								+ propPayResult.get("ERRTXT"));
					} else {
						System.out.println("֧���ѱ���������֧��״̬��" + sREQSTS + "��");
					}
				} else if (sRetCod.equals("-9")) {
					System.out.println("֧��δ֪�쳣�����ѯ֧�����ȷ��֧��״̬��������Ϣ��"
							+ pktRsp.getERRMSG());
				} else {
					System.out.println("֧��ʧ�ܣ�" + pktRsp.getERRMSG());
				}
			} else {
				System.out.println("��Ӧ���Ľ���ʧ��");
			}
		}
	}

	public static void main(String[] args) {
		try {
			HttpRequest request = new HttpRequest();

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