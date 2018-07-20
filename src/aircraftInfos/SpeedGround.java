package aircraftInfos;

// �����ٶ�
public class SpeedGround 
{
	// 1.��->���ٶ�
	private static double westToEastVelocity;
	// 2.��->���ٶ�
	private static double southToNorthVelocity;
	// 3.ˮƽ�ٶ�
	private static String velocity;
	// 4.ˮƽ����
	private static String heading;
	// 5.��ֱ����Դ
	private static String verticalRateSource;
	// 6.��ֱ���ʷ���
	private static String signOfVerticalRate;
	// 7.��ֱ���ʴ�С
	private static String verticalRate;
	
	/**
	 * �÷���������->���ٶ�
	 * @param datameOnBin 56λ2������Ч�����ֶ�
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
	 * �÷����õ���->���ٶ�
	 * @return ������->���ٶ�
	 */
	public static double getWestToEastVelocity()
	{
		return westToEastVelocity;
	}
	
	/**
	 * �÷���������->���ٶ�
	 * @param datameOnBin 56λ2������Ч�����ֶ�
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
	 * �÷����õ���->���ٶ�
	 * @return ������->���ٶ�
	 */
	public static double getSouthToNorthVelocity()
	{
		return southToNorthVelocity;
	}
	
	/**
	 * �÷�������ˮƽ�ٶ�
	 * @param datameOnBin 56λ2������Ч�����ֶ�
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
	 * �÷����õ�ˮƽ�ٶ�
	 * @return ����ˮƽ�ٶ�
	 */
	public static String getVelocity()
	{
		return velocity;
	}
	
	/**
	 * �÷�������ˮƽ����
	 * @param datameOnBin 56λ2������Ч�����ֶ�
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
	 * �÷����õ�ˮƽ����
	 * @return ����ˮƽ����
	 */
	public static String getHeading()
	{
		return heading;
	}

	
	/**
	 * �÷������ô�ֱ����Դ
	 * @param datameOnBin 56λ2������Ч�����ֶ�
	 */
	public static void setVerticalRateSource( String datameOnBin )
	{
		if ( datameOnBin.substring( 35 , 36 ).equals( "0" ) )
		{
			verticalRateSource = "��ѹ�߶ȱ仯��";
		}
		else if ( datameOnBin.substring( 35 , 36 ).equals( "1" ) )
		{
			verticalRateSource = "���θ߶ȱ仯��";
		}
	}
	
	/**
	 * �÷����õ���ֱ����Դ
	 * @return ���ش�ֱ����Դ
	 */
	public static String getVerticalRateSource()
	{
		return verticalRateSource;
	}
	
	
	/**
	 * �÷������ô�ֱ���ʷ���
	 * @param datameOnBin 56λ2������Ч�����ֶ�
	 */
	public static void setSignOfVerticalRate( String datameOnBin )
	{
		if ( datameOnBin.substring( 36 , 37 ).equals( "0" ) )
		{
			signOfVerticalRate = "����"; // ��������
		}
		else
		{
			signOfVerticalRate = "�½�"; // �����½�
		}
	}
	
	/**
	 * �÷����õ���ֱ���ʷ���
	 * @return ���ش�ֱ���ʷ���
	 */
	public static String getSignOfVerticalRate()
	{
		return signOfVerticalRate;
	}
	
	
	/**
	 * �÷����õ���ֱ���ʴ�С
	 * @param datameOnBin 56λ2������Ч�����ֶ�
	 */
	public static void setVerticalRate( String datameOnBin )
	{
		// �����ô�ֱ���ʷ���
		setSignOfVerticalRate(datameOnBin);
		if(getSignOfVerticalRate().equals("�½�")) {
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
	 * �÷����õ���ֱ���ʴ�С
	 * @return ���ش�ֱ���ʴ�С
	 */
	public static String getVerticalRate()
	{
		return verticalRate;
	}
	
}
