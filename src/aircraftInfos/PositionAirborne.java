package aircraftInfos;

//������λ��
public class PositionAirborne 
{
	// 1.γ��
	private static String latitude;
	// 2.����
	private static String longitude;
	// 3.�߶�
	private static String altitude;
	
	
	/**
	 * ���߷���1��ȡģ����
	 * @param x,y ��������������Ҫ����ȡģ����Ĳ���
	 * @return ����x��yȡģ�Ľ��
	 */
	public static double getMod( double x , double y )
	{
		return x - y * Math.floor( x / y );
	}
	
	
	/**
	 * ���߷���2������γ�ȵ�CPR��ʾ
	 * @param datameOnBin 56λ2������Ч�����ֶ�
	 * @return ����γ�ȵ�CPR��ʾ
	 */
	public static double getLatCprExp( String datameOnBin )
	{
		return (double)Integer.valueOf( datameOnBin.substring( 22 , 39 ) , 2 ) / 131072;
	}
	
	
	/**
	 * ���߷���3�����㾭�ȵ�CPR��ʾ
	 * @param datameOnBin 56λ2������Ч�����ֶ�
	 * @return ���ؾ��ȵ�CPR��ʾ
	 */
	public static double getLonCprExp( String datameOnBin )
	{
		return (double)Integer.valueOf( datameOnBin.substring( 39 , 56 ) , 2 ) / 131072;
	}
	
	
	/**
	 * ���߷���4�������ض�γ�ȶ�Ӧ�ľ���������NL
	 * @param lat ����
	 * @return �����ض�γ������Ӧ�ľ���������
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
	 * ���߷���5���ҳ����ĵı��뷽ʽ�������/ż����
	 * @param datameOnBin 56λ2������Ч�����ֶ� 
	 * @return ���ر���λ�ñ��뷽ʽ�ַ�����ODD��ʾ����룬EVEN��ʾż����
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
	 * ����1������߶ȣ���������2�ͷ���3����
	 * @param datameOnBin 56λ2������Ч�����ֶ�
	 */
	public static void setAltitude( String datameOnBin ) {
		double alt = 0;
		String temp = datameOnBin.substring( 8 , 20 );
		String altOnBin = temp.substring( 0 , 7 ) + temp.substring( 8 , 12 );
		
		if ( temp.substring( 7 , 8 ).equals( "0" ) ) // ��100�ı��� 
		{
			alt = (double)Integer.valueOf( altOnBin , 2 ) * 100 - 1000; 
		}
		else if ( temp.substring( 7 , 8 ).equals( "1" ) ) // ��25�ı���
		{
			alt = (double)Integer.valueOf( altOnBin , 2 ) * 25 - 1000;
		}
		
		altitude = String.valueOf( alt );
	}
	
	/**
	 * ����2�����ؽ���
	 * @param datameOnBin 56λ2������Ч�����ֶ�
	 * @param refLat �ο�γ��
	 * @param refLon �ο�����
	 */
	public static void setPositionLocally( String datameOnBin , String refLat , String refLon ) {
		// γ������Ŀ
		double numberOfLatZone = 15;
		// ���뷽ʽ��0Ϊż��1Ϊ��
		String encodeType = datameOnBin.substring( 21 , 22 );
		// ��γ�ȵ�CPR��ʾ
		double cprLat = getLatCprExp( datameOnBin );
		double cprLon = getLonCprExp( datameOnBin );
		
		// ����dLat
		double dLat = 0;
		if ( encodeType.equals( "0" ) )
		{
			dLat = 360 / ( numberOfLatZone * 4 );
		}
		else if ( encodeType.equals( "1" ) )
		{
			dLat = 360 / ( numberOfLatZone * 4 - 1 );
		}
		
		// ����γ������j
		double refLatOnDouble = Double.parseDouble( refLat );
		double j = Math.floor( refLatOnDouble / dLat ) + Math.floor( 
				getMod( refLatOnDouble , dLat ) / dLat - cprLat + 0.5 );
		
		// ����γ��
		double lat = dLat * ( j + cprLat );
		latitude = String.valueOf( lat );
		
		// ����dLon
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
		
		// ���㾭������m
		double refLonOnDouble = Double.parseDouble( refLon );
		double m = Math.floor( refLonOnDouble / dLon ) + Math.floor(
				getMod( refLonOnDouble , dLon ) / dLon - cprLon + 0.5 );
		
		// ���㾭��
		double lon = dLon * ( m + cprLon );
		longitude = String.valueOf( lon );
	}
	
	/**
	 * ����3��ȫ�����
	 * @param datameOnBin1 ��һ�����ĵ�56λ2������Ч�����ֶ�
 	 * @param datameOnBin2 �ڶ������ĵ�56λ2������Ч�����ֶ�
	 * @param time1 ��һ�����ĵĽ���ʱ��
	 * @param time2 �ڶ������ĵĽ���ʱ��
	 */
	public static void setPositionGlobally( String datameOnBin1 , String datameOnBin2 , double time1 , double time2 )
	{
		// �жϱ��뷽ʽ����ֵ
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
	
		// ���ĵ�CPR��ʾ
		double latCprEven = getLatCprExp( datameOnBinEven );
		double lonCprEven = getLonCprExp( datameOnBinEven );
		double latCprOdd = getLatCprExp( datameOnBinOdd );
		double lonCprOdd = getLonCprExp( datameOnBinOdd );
		
		// ����γ������j
		double j = Math.floor( 59 * latCprEven - 60 * latCprOdd + 0.5 );
		
		// ����γ��
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
		
		// ���γ������һ����
		if ( getNumberOfLonZone( latEven ) != getNumberOfLonZone( latOdd ) )
		{
			System.out.println( "��ż���뾭����������һ�£�ȫ����벻���У��˳�ȫ����룡" ); // �ô�Ҳ���Ա����Ϊ�ȴ�����Ϣ����������ȫ�����
			return;
		}
		
		// ���㾭��
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
		
		// ����߶ȣ����÷���1
		if ( timeEven >= timeOdd ) {
			setAltitude( datameOnBinEven );
		}
		else {
			setAltitude( datameOnBinOdd );
		}
	}
	
	/**
	 * �÷����õ�γ��
	 * @return γ��
	 */
	public static String getLatitude() {
		return latitude;
	}
	
	/**
	 * �÷����õ�����
	 * @return ����
	 */
	public static String getLongitude() {
		return longitude;
	}
	
	/**
	 * �÷�����ø߶��ַ���
	 * @return ���ظ߶��ַ���
	 */
	public static String getAltitude() {
		return altitude;
	}
}
