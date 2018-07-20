package aircraftInfos;

//飞行器位置
public class PositionAirborne 
{
	// 1.纬度
	private static String latitude;
	// 2.经度
	private static String longitude;
	// 3.高度
	private static String altitude;
	
	
	/**
	 * 工具方法1：取模运算
	 * @param x,y 这两个参数是需要进行取模运算的参数
	 * @return 返回x对y取模的结果
	 */
	public static double getMod( double x , double y )
	{
		return x - y * Math.floor( x / y );
	}
	
	
	/**
	 * 工具方法2：计算纬度的CPR表示
	 * @param datameOnBin 56位2进制有效数据字段
	 * @return 返回纬度的CPR表示
	 */
	public static double getLatCprExp( String datameOnBin )
	{
		return (double)Integer.valueOf( datameOnBin.substring( 22 , 39 ) , 2 ) / 131072;
	}
	
	
	/**
	 * 工具方法3：计算经度的CPR表示
	 * @param datameOnBin 56位2进制有效数据字段
	 * @return 返回经度的CPR表示
	 */
	public static double getLonCprExp( String datameOnBin )
	{
		return (double)Integer.valueOf( datameOnBin.substring( 39 , 56 ) , 2 ) / 131072;
	}
	
	
	/**
	 * 工具方法4：计算特定纬度对应的经度区数量NL
	 * @param lat 经度
	 * @return 返回特定纬度所对应的经度区数量
	 */
	public static double getNumberOfLonZone( double lat )
	{
		return Math.floor(
				2 * Math.PI / 
				Math.acos(
						1 - ( 1 - Math.cos( Math.PI / ( 2.0 * 15.0 ) ) ) /  Math.pow( Math.cos( Math.PI / 180 * lat ) , 2 ) 
						) 
				);
	}
	
	
	/**
	 * 工具方法5：找出报文的编码方式，奇编码/偶编码
	 * @param datameOnBin 56位2进制有效数据字段 
	 * @return 返回报文位置编码方式字符串，ODD表示奇编码，EVEN表示偶编码
	 */
	public static String getEncodingWay( String datameOnBin )
	{
		String encodingWay = null;
		
		if ( datameOnBin.substring( 21 , 22 ).equals( "0" ) )
		{
			encodingWay = "EVEN";
		}
		else if ( datameOnBin.substring( 21 , 22 ).equals( "1" ) )
		{
			encodingWay = "ODD";
		}
		
		return encodingWay;
	}
	
	
	/**
	 * 方法1：解码高度，将被方法2和方法3调用
	 * @param datameOnBin 56位2进制有效数据字段
	 */
	public static void setAltitude( String datameOnBin ) {
		double alt = 0;
		String temp = datameOnBin.substring( 8 , 20 );
		String altOnBin = temp.substring( 0 , 7 ) + temp.substring( 8 , 12 );
		
		if ( temp.substring( 7 , 8 ).equals( "0" ) ) // 是100的倍数 
		{
			alt = (double)Integer.valueOf( altOnBin , 2 ) * 100 - 1000; 
		}
		else if ( temp.substring( 7 , 8 ).equals( "1" ) ) // 是25的倍数
		{
			alt = (double)Integer.valueOf( altOnBin , 2 ) * 25 - 1000;
		}
		
		altitude = String.valueOf( alt );
	}
	
	/**
	 * 方法2：本地解码
	 * @param datameOnBin 56位2进制有效数据字段
	 * @param refLat 参考纬度
	 * @param refLon 参考经度
	 */
	public static void setPositionLocally( String datameOnBin , String refLat , String refLon ) {
		// 纬度区数目
		double numberOfLatZone = 15;
		// 编码方式，0为偶，1为奇
		String encodeType = datameOnBin.substring( 21 , 22 );
		// 经纬度的CPR表示
		double cprLat = getLatCprExp( datameOnBin );
		double cprLon = getLonCprExp( datameOnBin );
		
		// 计算dLat
		double dLat = 0;
		if ( encodeType.equals( "0" ) )
		{
			dLat = 360 / ( numberOfLatZone * 4 );
		}
		else if ( encodeType.equals( "1" ) )
		{
			dLat = 360 / ( numberOfLatZone * 4 - 1 );
		}
		
		// 计算纬度索引j
		double refLatOnDouble = Double.parseDouble( refLat );
		double j = Math.floor( refLatOnDouble / dLat ) + Math.floor( 
				getMod( refLatOnDouble , dLat ) / dLat - cprLat + 0.5 );
		
		// 计算纬度
		double lat = dLat * ( j + cprLat );
		latitude = String.valueOf( lat );
		
		// 计算dLon
		double numberOfLonZone = getNumberOfLonZone( lat );
		double dLon = 0;
		if ( numberOfLonZone > 0 )
		{
			dLon = 360 / numberOfLonZone;
		}
		else if ( numberOfLonZone == 0 )
		{
			dLon = 360;
		}
		
		// 计算经度索引m
		double refLonOnDouble = Double.parseDouble( refLon );
		double m = Math.floor( refLonOnDouble / dLon ) + Math.floor(
				getMod( refLonOnDouble , dLon ) / dLon - cprLon + 0.5 );
		
		// 计算经度
		double lon = dLon * ( m + cprLon );
		longitude = String.valueOf( lon );
	}
	
	/**
	 * 方法3：全球解码
	 * @param datameOnBin1 第一条报文的56位2进制有效数据字段
 	 * @param datameOnBin2 第二条报文的56位2进制有效数据字段
	 * @param time1 第一条报文的接收时间
	 * @param time2 第二条报文的接收时间
	 */
	public static void setPositionGlobally( String datameOnBin1 , String datameOnBin2 , double time1 , double time2 )
	{
		// 判断编码方式并赋值
		String datameOnBinEven = null;
		String datameOnBinOdd = null;
		double timeEven = 0;
		double timeOdd = 1;
		
		if ( getEncodingWay( datameOnBin1 ).equals( "EVEN" ) && getEncodingWay( datameOnBin2 ).equals( "ODD" ) )
		{
			datameOnBinEven = datameOnBin1;
			datameOnBinOdd = datameOnBin2;
			timeEven = time1;
			timeOdd = time2;
		}
		else if ( getEncodingWay( datameOnBin1 ).equals( "ODD" ) && getEncodingWay( datameOnBin2 ).equals( "EVEN" ) )
		{
			datameOnBinEven = datameOnBin2;
			datameOnBinOdd = datameOnBin1;
			timeEven = time2;
			timeOdd = time1;
			
		}
	
		// 求报文的CPR表示
		double latCprEven = getLatCprExp( datameOnBinEven );
		double lonCprEven = getLonCprExp( datameOnBinEven );
		double latCprOdd = getLatCprExp( datameOnBinOdd );
		double lonCprOdd = getLonCprExp( datameOnBinOdd );
		
		// 计算纬度索引j
		double j = Math.floor( 59 * latCprEven - 60 * latCprOdd + 0.5 );
		
		// 计算纬度
		double dLatEven = 360.0 / 60;
		double dLatOdd = 360.0 / 59;
		double latEven = dLatEven * ( getMod( j , 60 ) + latCprEven );
		double latOdd = dLatOdd * ( getMod( j , 59 ) + latCprOdd );
		
		if ( latEven >= 270 )
		{
			latEven = latEven - 360;
		}
		else if ( latOdd >= 270 )
		{
			latOdd = latOdd - 360;
		}
		
		if ( timeEven >= timeOdd  )
		{
			latitude = String.valueOf( latEven );
		}
		else
		{
			latitude = String.valueOf( latOdd );
		}
		
		// 检查纬度区的一致性
		if ( getNumberOfLonZone( latEven ) != getNumberOfLonZone( latOdd ) )
		{
			System.out.println( "奇偶编码经度区数量不一致，全球解码不可行，退出全球解码！" ); // 该处也可以被设计为等待新消息，继续进行全球解码
			return;
		}
		
		// 计算经度
		double lon = 0;
		if ( timeEven >= timeOdd )
		{
			double ni = Math.max( getNumberOfLonZone( latEven ) , 1 );
			double dLon = 360 / ni;
			double m = Math.floor( 
					lonCprEven * ( getNumberOfLonZone( latEven ) - 1 )
					- lonCprOdd * getNumberOfLonZone( latEven ) + 0.5
					);
			lon = dLon * ( getMod( m , ni ) + lonCprEven );
		}
		else {
			double ni = Math.max( getNumberOfLonZone( latOdd ) - 1 , 1 );
			double dLon = 360 / ni;
			double m = Math.floor( 
					lonCprEven * ( getNumberOfLonZone( latOdd ) - 1 )
					- lonCprOdd * getNumberOfLonZone( latOdd ) + 0.5
					);
			lon = dLon * ( getMod( m , ni ) + lonCprOdd );
		}
		
		if ( lon >= 180 ) {
			lon = lon - 360;
		}
		
		longitude = String.valueOf( lon );
		
		// 计算高度，调用方法1
		if ( timeEven >= timeOdd ) {
			setAltitude( datameOnBinEven );
		}
		else {
			setAltitude( datameOnBinOdd );
		}
	}
	
	/**
	 * 该方法得到纬度
	 * @return 纬度
	 */
	public static String getLatitude() {
		return latitude;
	}
	
	/**
	 * 该方法得到经度
	 * @return 经度
	 */
	public static String getLongitude() {
		return longitude;
	}
	
	/**
	 * 该方法获得高度字符串
	 * @return 返回高度字符串
	 */
	public static String getAltitude() {
		return altitude;
	}
}
