package aircraftInfos;

// 参考位置：纬度+经度
public class PositionRef 
{
	// 参考纬度
	private static String latitude;
	// 参考经度
	private static String longitude;
	
	/**
	 * 该方法用于设置参考纬度的值
	 * @param refLatitude 该参数为参考纬度
	 */
	public static void setRefLatitude( String refLatitude )
	{
		latitude = refLatitude;
	}
	
	/**
	 * 该方法用于得到参考纬度值
	 * @return 返回参考纬度值
	 */
	public static String getRefLatitude()
	{
		return latitude;
	}
	
	
	/**
	 * 该方法用于设置参考经度的值
	 * @param refLongitude 该参数为参考经度的值
	 */
	public static void setRefLongitude( String refLongitude )
	{
		longitude = refLongitude;
	}
	
	/**
	 * 该方法用于获得参考经度的值
	 * @return 返回参考经度的值
	 */
	public static String getRefLongitude()
	{
		return longitude;
	}
}
