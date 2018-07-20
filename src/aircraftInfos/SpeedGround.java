package aircraftInfos;

// 地面速度
public class SpeedGround 
{
	// 1.西->东速度
	private static double westToEastVelocity;
	// 2.南->北速度
	private static double southToNorthVelocity;
	// 3.水平速度
	private static String velocity;
	// 4.水平航向
	private static String heading;
	// 5.垂直速率源
	private static String verticalRateSource;
	// 6.垂直速率方向
	private static String signOfVerticalRate;
	// 7.垂直速率大小
	private static String verticalRate;
	
	/**
	 * 该方法设置西->东速度
	 * @param datameOnBin 56位2进制有效数据字段
	 */
	public static void setWestToEastVelocity( String datameOnBin )
	{
		String signOfEastToWestVelocity = datameOnBin.substring( 13 , 14 );
		double eastToWestVelocity_1 = (double)Integer.valueOf( datameOnBin.substring( 14 , 24 ) , 2 );
		
		if ( signOfEastToWestVelocity.equals( "1" ) )
		{
			westToEastVelocity = -1 * ( eastToWestVelocity_1 - 1 );
		}
		else if ( signOfEastToWestVelocity.equals( "0" ) )
		{
			westToEastVelocity = eastToWestVelocity_1 - 1;
		}
	}
	
	/**
	 * 该方法得到西->东速度
	 * @return 返回西->东速度
	 */
	public static double getWestToEastVelocity()
	{
		return westToEastVelocity;
	}
	
	/**
	 * 该方法设置南->北速度
	 * @param datameOnBin 56位2进制有效数据字段
	 */
	public static void setSouthToNorthVelocity( String datameOnBin )
	{
		String signOfNorthToSouthVelocity = datameOnBin.substring( 24 , 25 );
		double northToSouthVelocity_1 = (double)Integer.valueOf( datameOnBin.substring( 25 , 35 ) , 2 );

		if ( signOfNorthToSouthVelocity.equals( "1" ) )
		{
			southToNorthVelocity = -1 * ( northToSouthVelocity_1 - 1 );
		}
		else if ( signOfNorthToSouthVelocity.equals( "0" ) )
		{
			southToNorthVelocity = northToSouthVelocity_1 - 1;
		}
	}
	
	/**
	 * 该方法得到南->北速度
	 * @return 返回南->北速度
	 */
	public static double getSouthToNorthVelocity()
	{
		return southToNorthVelocity;
	}
	
	/**
	 * 该方法设置水平速度
	 * @param datameOnBin 56位2进制有效数据字段
	 */
	public static void setVelocity( String datameOnBin )
	{
		setWestToEastVelocity( datameOnBin );
		// westToEastVelocity = getWestToEastVelocity();
		setSouthToNorthVelocity( datameOnBin );
		// southToNorthVelocity = getSouthToNorthVelocity();
		
		velocity = String.valueOf(
				Math.sqrt( Math.pow( westToEastVelocity , 2 ) + Math.pow( southToNorthVelocity , 2 ) )
				);
		
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
	 * 该方法设置水平航向
	 * @param datameOnBin 56位2进制有效数据字段
	 */
	public static void setHeading( String datameOnBin )
	{
		setWestToEastVelocity( datameOnBin );
		// westToEastVelocity = getWestToEastVelocity();
		setSouthToNorthVelocity( datameOnBin );
		// southToNorthVelocity = getSouthToNorthVelocity();
		
		double heading_1 = Math.atan2( westToEastVelocity , southToNorthVelocity ) * 360 / ( 2 * Math.PI );
		if ( heading_1 < 0 )
		{
			heading_1 += 360;
		}
		
		heading = String.valueOf(heading_1);
	}
	
	/**
	 * 该方法得到水平航向
	 * @return 返回水平航向
	 */
	public static String getHeading()
	{
		return heading;
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
	 * 该方法得到垂直速率源
	 * @return 返回垂直速率源
	 */
	public static String getVerticalRateSource()
	{
		return verticalRateSource;
	}
	
	
	/**
	 * 该方法设置垂直速率方向
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
	 * 该方法得到垂直速率大小
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
