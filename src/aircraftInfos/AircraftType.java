package aircraftInfos;

public class AircraftType {

	// 航空器种类
	private static String aircraftType;

	/**
	 * 设置aircraftType成员变量的值
	 * 
	 * @param datameOnBin
	 *            该参数是56位2进制有效数据字段
	 */
	public static void setAircraftType(String datameOnBin) {
		String ecOnBin = datameOnBin.substring(5, 8); // ecOnBin 是航空器种类
		if (Integer.valueOf(ecOnBin, 2) == 0) {
			aircraftType = "Not available!";
		} else {
			aircraftType = String.valueOf(Integer.valueOf(ecOnBin, 2));
		}
	}

	/**
	 * 得到飞行器种类
	 * @return aircraftType
	 */
	public static String getAircraftType() {
		return aircraftType;
	}
}
