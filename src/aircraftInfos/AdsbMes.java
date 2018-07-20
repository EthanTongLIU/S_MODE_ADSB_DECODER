package aircraftInfos;

public class AdsbMes {

	/*
	 * 非输出中间信息
	 */
	
	//16  进制报文原码
	public String dataFrame = "null";
	//2 进制下行格式
	public String downlinkOnBin = "null";
	//10 进制下行格式
	public String downlinkOnDec = "null";
	//2 进制 CA 字段
	public String caOnBin = "null";
	//2 进制 ME 字段
	public String datameOnBin = "null";
	//10 进制 TC 字段
	public int typeCode;
	//2 进制 PI 字段
	public String piOnBin = "null";
	
	/*
	 * 输出解码后信息
	 */
	
	//1.报文接收时间
	public String time = "null";
	//2.飞机地址（16 进制 ICAO24 字段）
	public String icao24 = "null";
	//3.纬度
	public String lat= "null";
	//4.经度
	public String lon = "null";
	//5.高度
	public String alt = "null";
	//6.地速
	public String groundSpeed = "null";
	//7.航向
	public String heading = "null";
	//8.航班号
	public String callsign = "null";
	//9.爬升率
	public String verticalRate = "null";
	
	/*
	 * 非输出解码后信息
	 */
	
	//1.航空器种类
	public String aircraftType = "null";
	//2.空速类型
	public String airSpeedType = "null";
	//3.空速
	public String airSpeed = "null";
	//4.垂直速率源
	public String verticalRateSource = "null";
	
}
