package decodeBus;

import util.HexBinOct;

public class PreprocessFrame {

	/**
	 * ���������緢����DF17����
	 * 
	 * @return
	 */
	public static int isDF17Network(String frame) {
		int judge = 0;
		int judge1 = 0;
		int judge2 = 0;

		// ����ȥ������֡�Ĳ�����ַ�*��;
		frame = frame.replace(" ", "");

		// ��ȡ����ǰ�����ַ����ж����Ƿ�ΪDF17����
		String DLCAonHex = frame.substring(0, 2);
		String DLCAonBin = HexBinOct.transformHexStringToBinString(DLCAonHex);
		String DLonBin = DLCAonBin.substring(0, 5);
		int DLonDec = Integer.valueOf(DLonBin, 2);
		if (DLonDec == 17) {
			judge1 = 1;
		}

		// ��ȡ���ĵ�λ�����ж��Ƿ���©λ���������
		if (frame.length() == 28) {
			judge2 = 1;
		}

		if (judge1 == 1 && judge2 == 1) {
			judge = 1;
		}

		return judge;
	}

	/**
	 * ���˱��ģ��ж��Ƿ�ΪDF17��ʽ�ı��ĺ�©λ������
	 * 
	 * @param frame
	 *            ����һ������
	 * @return ����0�������Ĳ��ܽ�����һ����������1�������Ŀ��Խ�����һ���Ĵ���
	 */
	public static int isDF17(String frame) {
		int judge = 0;
		int judge1 = 0;
		int judge2 = 0;

		// ��ȡ����ǰ�����ַ����ж����Ƿ�ΪDF17����
		String DLCAonHex = frame.substring(0, 2);
		String DLCAonBin = HexBinOct.transformHexStringToBinString(DLCAonHex);
		String DLonBin = DLCAonBin.substring(0, 5);
		int DLonDec = Integer.valueOf(DLonBin, 2);
		if (DLonDec == 17) {
			judge1 = 1;
		}

		// ��ȡ���ĵ�λ�����ж��Ƿ���©λ���������
		if (frame.length() == 28) {
			judge2 = 1;
		}

		if (judge1 == 1 && judge2 == 1) {
			judge = 1;
		}

		return judge;
	}

}
