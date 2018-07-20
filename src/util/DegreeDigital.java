package util;

public class DegreeDigital {

	/**
	 * 度分秒经纬度(必须含有'°')和数字经纬度转换
	 * 
	 * @param degrees
	 *            度分秒经纬度
	 * @return 数字经纬度
	 */
	public static double ConvertDegreesToDigital(String degrees) {
		final double num = 60;
		double digitalDegree = 0.0;
		int d = degrees.indexOf('°'); // 度的符号对应的 Unicode 代码为：00B0[1]（六十进制），显示为°。
		if (d < 0) {
			return digitalDegree;
		}
		String degree = degrees.substring(0, d);
		digitalDegree += Double.parseDouble(degree);

		int m = degrees.indexOf('\''); // 分的符号对应的 Unicode 代码为：2032[1]（六十进制），显示为′。
		if (m < 0) {
			return digitalDegree;
		}
		String minute = degrees.substring(d + 1, m);
		digitalDegree += ((Double.parseDouble(minute)) / num);

		int s = degrees.indexOf('\"'); // 秒的符号对应的 Unicode 代码为：2033[1]（六十进制），显示为″。
		if (s < 0) {
			return digitalDegree;
		}
		String second = degrees.substring(m + 1, s);
		digitalDegree += (Double.parseDouble(second) / (num * num));

		return digitalDegree;
	}

	/**
	 * 度分秒经纬度转换为数字经纬度
	 * 
	 * @param D
	 *            度
	 * @param M
	 *            分
	 * @param S
	 *            秒
	 * @return 数字经纬度
	 */
	public static double ConvertDMSToDigital(String D, String M, String S) {
		double digitalDegree = 0.0;
		digitalDegree = Double.parseDouble(D) + Double.parseDouble(D) / 60.0 + Double.parseDouble(D) / 3600.0;
		return digitalDegree;
	}

}
