package aircraftInfos;

public class Callsign {
	// ����������
	private static String callsign;

	/**
	 * ����callsign��Ա������ֵ
	 * 
	 * @param datameOnBin
	 *            �ò�����56λ2������Ч�����ֶ�
	 */
	public static void setCallsign(String datameOnBin) {
		String index = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ#####_###############0123456789######";

		int[] indexOfCallsign = new int[8];
		for (int i = 0; i < 8; i++) {
			indexOfCallsign[i] = Integer.valueOf(datameOnBin.substring(8 + 6 * i, 8 + 6 * i + 6), 2);
		}

		callsign = index.substring(indexOfCallsign[0], indexOfCallsign[0] + 1);
		for (int i = 1; i < 8; i++) {
			callsign += index.substring(indexOfCallsign[i], indexOfCallsign[i] + 1);
		}
	}

	/**
	 * ���ʳ�Ա����callsign
	 * 
	 * @return
	 */
	public static String getCallsign() {
		return callsign;
	}

}
