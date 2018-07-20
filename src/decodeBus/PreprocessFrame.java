package decodeBus;

import util.HexBinOct;

public class PreprocessFrame {

	/**
	 * 处理经由网络发来的DF17报文
	 * 
	 * @return
	 */
	public static int isDF17Network(String frame) {
		int judge = 0;
		int judge1 = 0;
		int judge2 = 0;

		// 首先去掉报文帧的不相干字符*和;
		frame = frame.replace(" ", "");

		// 读取报文前两个字符，判断其是否为DF17类型
		String DLCAonHex = frame.substring(0, 2);
		String DLCAonBin = HexBinOct.transformHexStringToBinString(DLCAonHex);
		String DLonBin = DLCAonBin.substring(0, 5);
		int DLonDec = Integer.valueOf(DLonBin, 2);
		if (DLonDec == 17) {
			judge1 = 1;
		}

		// 读取报文的位数，判断是否有漏位的情况发生
		if (frame.length() == 28) {
			judge2 = 1;
		}

		if (judge1 == 1 && judge2 == 1) {
			judge = 1;
		}

		return judge;
	}

	/**
	 * 过滤报文，判断是否为DF17格式的报文和漏位现象发生
	 * 
	 * @param frame
	 *            传入一条报文
	 * @return 返回0，代表报文不能进行下一步处理；返回1，代表报文可以进行下一步的处理
	 */
	public static int isDF17(String frame) {
		int judge = 0;
		int judge1 = 0;
		int judge2 = 0;

		// 读取报文前两个字符，判断其是否为DF17类型
		String DLCAonHex = frame.substring(0, 2);
		String DLCAonBin = HexBinOct.transformHexStringToBinString(DLCAonHex);
		String DLonBin = DLCAonBin.substring(0, 5);
		int DLonDec = Integer.valueOf(DLonBin, 2);
		if (DLonDec == 17) {
			judge1 = 1;
		}

		// 读取报文的位数，判断是否有漏位的情况发生
		if (frame.length() == 28) {
			judge2 = 1;
		}

		if (judge1 == 1 && judge2 == 1) {
			judge = 1;
		}

		return judge;
	}

}
