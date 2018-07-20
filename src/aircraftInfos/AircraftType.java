package aircraftInfos;

public class AircraftType {

	// ����������
	private static String aircraftType;

	/**
	 * ����aircraftType��Ա������ֵ
	 * 
	 * @param datameOnBin
	 *            �ò�����56λ2������Ч�����ֶ�
	 */
	public static void setAircraftType(String datameOnBin) {
		String ecOnBin = datameOnBin.substring(5, 8); // ecOnBin �Ǻ���������
		if (Integer.valueOf(ecOnBin, 2) == 0) {
			aircraftType = "Not available!";
		} else {
			aircraftType = String.valueOf(Integer.valueOf(ecOnBin, 2));
		}
	}

	/**
	 * �õ�����������
	 * @return aircraftType
	 */
	public static String getAircraftType() {
		return aircraftType;
	}
}
