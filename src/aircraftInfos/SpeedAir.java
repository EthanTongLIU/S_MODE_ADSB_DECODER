package aircraftInfos;

public class SpeedAir 
{
	// 1.空速类型
	private static String airSpeedType;
	// 2.航向是否可获得
	private static String headingSource;
	// 3.水平航向
	private static String heading;
	// 4.水平速度
	private static String velocity;
	// 5.垂直速率源
	public static String verticalRateSource;
	// 6.垂直速率方向
	public static String signOfVerticalRate;
	// 7.垂直速率大小
	public static String verticalRate;
	
	/**
	 * 该方法设置空速类型
	 * @param datameOnBin 56位2进制有效数据字段
	 */
	public static void setAirSpeedType( String datameOnBin )
	{
		if ( datameOnBin.substring( 24 , 25 ).equals( "0" ) )
		{
			airSpeedType = "指示空速（IAS）"; // 指示空速
		}
		else if ( datameOnBin.substring( 24 , 25 ).equals( "1" ) )
		{
			airSpeedType = "真实空速（TAS）"; // 真实空速
		}
	}
	
	/**
	 * 该方法得到空速类型
	 * @return 空速类型
	 */
	public static String getAirSpeedType()
	{
		return airSpeedType;
	}

	
	/**
	 * 该方法设置航向是否可获得
	 * @param datameOnBin 56位2进制有效数据字段
	 */
	public static void setHeadingSource( String datameOnBin )
	{
		if ( datameOnBin.substring( 13 , 14 ).equals( "0" ) )
		{
			headingSource = "Not available";
		}
		else if ( datameOnBin.substring( 13 , 14 ).equals( "1" ) )
		{
			headingSource = "Available";
		}
	}
	
	/**
	 * 该方法获得航向是否可获得
	 * @return 返回航向是否可获得，字符串"Available"或者"Not available"
	 */
	public static String getHeadingSource()
	{
		return headingSource;
	}

	
	/**
	 * 该方法设置水平航向
	 * @param datameOnBin 56位2进制有效数据字段
	 */
	public static void setHeading( String datameOnBin )
	{
		if ( datameOnBin.substring( 13 , 14 ).equals( "1" ) )
		{
			heading = Integer.valueOf( datameOnBin.substring( 14 , 24 ) , 2 ) / 1024.0 * 360 + "";
		}
	}
	
	/**
	 * 该方法获得水平航向
	 * @return 返回水平航向
	 */
	public static String getHeading()
	{
		return heading;
	}
	
	
	/**
	 * 该方法设置水平速度
	 * @param datameOnBin 56位2进制有效数据字段
	 */
	public static void setVelocity( String datameOnBin )
	{
		velocity = String.valueOf( Integer.valueOf( datameOnBin.substring( 25 , 35 ) , 2 ) );
	}
	
	/**
	 * 该方法得到水平速度
	 * @return 返回水平速度
	 */
	public static String getVelocity()
	{
		return velocity;
	}
	

	
	/**
	 * 该方法设置垂直速率源
	 * @param datameOnBin 56位2进制有效数据字段
	 */
	public static void setVerticalRateSource( String datameOnBin )
	{
		if ( datameOnBin.substring( 35 , 36 ).equals( "0" ) )
		{
			verticalRateSource = "气压高度变化率";
		}
		else if ( datameOnBin.substring( 35 , 36 ).equals( "1" ) )
		{
			verticalRateSource = "几何高度变化率";
		}
	}
	
	/**
	 * 该方法获得垂直速率源
	 * @return 返回垂直速率源
	 */
	public static String getVerticalRateSource()
	{
		return verticalRateSource;
	}

	
	/**
	 * 该方法设置垂直速率源
	 * @param datameOnBin 56位2进制有效数据字段
	 */
	public static void setSignOfVerticalRate( String datameOnBin )
	{
		if ( datameOnBin.substring( 36 , 37 ).equals( "0" ) )
		{
			signOfVerticalRate = "上升"; // 代表上升
		}
		else
		{
			signOfVerticalRate = "下降"; // 代表下降
		}
	}
	
	/**
	 * 该方法得到垂直速率方向
	 * @return 返回垂直速率方向
	 */
	public static String getSignOfVerticalRate()
	{
		return signOfVerticalRate;
	}
	
	
	/**
	 * 该方法设置垂直速率大小
	 * @param datameOnBin 56位2进制有效数据字段
	 */
	public static void setVerticalRate( String datameOnBin )
	{
		// 先设置垂直速率方向
		setSignOfVerticalRate(datameOnBin);
		if(getSignOfVerticalRate().equals("下降")) {
			verticalRate = String.valueOf(
					- ( (double)Integer.valueOf( datameOnBin.substring( 37 , 46 ) , 2 ) - 1 ) * 64
					);
		}
		else {
			verticalRate = String.valueOf(
					( (double)Integer.valueOf( datameOnBin.substring( 37 , 46 ) , 2 ) - 1 ) * 64
					);
		}
	}
	
	/**
	 * 该方法得到垂直速率大小
	 * @return 返回垂直速率大小
	 */
	public static String getVerticalRate()
	{
		return verticalRate;
	}
}
