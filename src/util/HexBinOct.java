package util;

public class HexBinOct {

	/**
	 * 一个将16进制字符串转化成2进制字符串的方法
	 * 
	 * @param hexString
	 *            该参数是16进制字符串，目前不限制其长度
	 * @return 返回2进制字符串
	 */
	public static String transformHexStringToBinString(String hexString) {
		// 16 进制转换 2 进制字符索引
		String[] indexOfHexChar = { 
				"0000", "0001", "0010", "0011", 
				"0100", "0101", "0110", "0111", 
				"1000", "1001", "1010", "1011", 
				"1100", "1101", "1110", "1111" 
		};

		// 以下规定了一个索引字符串，用于读出被转换字符的位置
		String indexOfHexOnMaj = "0123456789ABCDEF";
		String indexOfHexOnMin = "0123456789abcdef";

		// 传入的 16 进制字符串的长度
		int len = hexString.length();

		// 索引记录器
		int[] indexRecoder = new int[len];

		// 不管 16 进制字符串中的字母是大写还是小写，都可以完成转换
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
