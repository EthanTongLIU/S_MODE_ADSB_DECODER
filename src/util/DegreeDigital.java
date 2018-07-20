package util;

public class DegreeDigital {

	/**
	 * �ȷ��뾭γ��(���뺬��'��')�����־�γ��ת��
	 * 
	 * @param degrees
	 *            �ȷ��뾭γ��
	 * @return ���־�γ��
	 */
	public static double ConvertDegreesToDigital(String degrees) {
		final double num = 60;
		double digitalDegree = 0.0;
		int d = degrees.indexOf('��'); // �ȵķ��Ŷ�Ӧ�� Unicode ����Ϊ��00B0[1]����ʮ���ƣ�����ʾΪ�㡣
		if (d < 0) {
			return digitalDegree;
		}
		String degree = degrees.substring(0, d);
		digitalDegree += Double.parseDouble(degree);

		int m = degrees.indexOf('\''); // �ֵķ��Ŷ�Ӧ�� Unicode ����Ϊ��2032[1]����ʮ���ƣ�����ʾΪ�䡣
		if (m < 0) {
			return digitalDegree;
		}
		String minute = degrees.substring(d + 1, m);
		digitalDegree += ((Double.parseDouble(minute)) / num);

		int s = degrees.indexOf('\"'); // ��ķ��Ŷ�Ӧ�� Unicode ����Ϊ��2033[1]����ʮ���ƣ�����ʾΪ�塣
		if (s < 0) {
			return digitalDegree;
		}
		String second = degrees.substring(m + 1, s);
		digitalDegree += (Double.parseDouble(second) / (num * num));

		return digitalDegree;
	}

	/**
	 * �ȷ��뾭γ��ת��Ϊ���־�γ��
	 * 
	 * @param D
	 *            ��
	 * @param M
	 *            ��
	 * @param S
	 *            ��
	 * @return ���־�γ��
	 */
	public static double ConvertDMSToDigital(String D, String M, String S) {
		double digitalDegree = 0.0;
		digitalDegree = Double.parseDouble(D) + Double.parseDouble(D) / 60.0 + Double.parseDouble(D) / 3600.0;
		return digitalDegree;
	}

}
