package aircraftInfos;

public class AdsbMes {

	/*
	 * ������м���Ϣ
	 */
	
	//16  ���Ʊ���ԭ��
	public String dataFrame = "null";
	//2 �������и�ʽ
	public String downlinkOnBin = "null";
	//10 �������и�ʽ
	public String downlinkOnDec = "null";
	//2 ���� CA �ֶ�
	public String caOnBin = "null";
	//2 ���� ME �ֶ�
	public String datameOnBin = "null";
	//10 ���� TC �ֶ�
	public int typeCode;
	//2 ���� PI �ֶ�
	public String piOnBin = "null";
	
	/*
	 * ����������Ϣ
	 */
	
	//1.���Ľ���ʱ��
	public String time = "null";
	//2.�ɻ���ַ��16 ���� ICAO24 �ֶΣ�
	public String icao24 = "null";
	//3.γ��
	public String lat= "null";
	//4.����
	public String lon = "null";
	//5.�߶�
	public String alt = "null";
	//6.����
	public String groundSpeed = "null";
	//7.����
	public String heading = "null";
	//8.�����
	public String callsign = "null";
	//9.������
	public String verticalRate = "null";
	
	/*
	 * ������������Ϣ
	 */
	
	//1.����������
	public String aircraftType = "null";
	//2.��������
	public String airSpeedType = "null";
	//3.����
	public String airSpeed = "null";
	//4.��ֱ����Դ
	public String verticalRateSource = "null";
	
}
