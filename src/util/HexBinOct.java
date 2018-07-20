package util;

public class HexBinOct {

	/**
	 * һ����16�����ַ���ת����2�����ַ����ķ���
	 * 
	 * @param hexString
	 *            �ò�����16�����ַ�����Ŀǰ�������䳤��
	 * @return ����2�����ַ���
	 */
	public static String transformHexStringToBinString(String hexString) {
		// 16 ����ת�� 2 �����ַ�����
		String[] indexOfHexChar = { 
				"0000", "0001", "0010", "0011", 
				"0100", "0101", "0110", "0111", 
				"1000", "1001", "1010", "1011", 
				"1100", "1101", "1110", "1111" 
		};

		// ���¹涨��һ�������ַ��������ڶ�����ת���ַ���λ��
		String indexOfHexOnMaj = "0123456789ABCDEF";
		String indexOfHexOnMin = "0123456789abcdef";

		// ����� 16 �����ַ����ĳ���
		int len = hexString.length();

		// ������¼��
		int[] indexRecoder = new int[len];

		// ���� 16 �����ַ����е���ĸ�Ǵ�д����Сд�����������ת��
		for (int i = 0; i < len; i++) {
			if (indexOfHexOnMaj.indexOf(hexString.substring(i, i + 1)) >= 0) {
				indexRecoder[i] = indexOfHexOnMaj.indexOf(hexString.substring(i, i + 1));
			} else if (indexOfHexOnMin.indexOf(hexString.substring(i, i + 1)) >= 0) {
				indexRecoder[i] = indexOfHexOnMin.indexOf(hexString.substring(i, i + 1));
			}
		}

		String binString = indexOfHexChar[indexRecoder[0]];
		for (int i = 1; i < len; i++) {
			binString += indexOfHexChar[indexRecoder[i]];
		}

		// System.out.println( binString );

		return binString;
	}

}
