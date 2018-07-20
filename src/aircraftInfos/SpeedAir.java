package aircraftInfos;

public class SpeedAir 
{
	// 1.��������
	private static String airSpeedType;
	// 2.�����Ƿ�ɻ��
	private static String headingSource;
	// 3.ˮƽ����
	private static String heading;
	// 4.ˮƽ�ٶ�
	private static String velocity;
	// 5.��ֱ����Դ
	public static String verticalRateSource;
	// 6.��ֱ���ʷ���
	public static String signOfVerticalRate;
	// 7.��ֱ���ʴ�С
	public static String verticalRate;
	
	/**
	 * �÷������ÿ�������
	 * @param datameOnBin 56λ2������Ч�����ֶ�
	 */
	public static void setAirSpeedType( String datameOnBin )
	{
		if ( datameOnBin.substring( 24 , 25 ).equals( "0" ) )
		{
			airSpeedType = "ָʾ���٣�IAS��"; // ָʾ����
		}
		else if ( datameOnBin.substring( 24 , 25 ).equals( "1" ) )
		{
			airSpeedType = "��ʵ���٣�TAS��"; // ��ʵ����
		}
	}
	
	/**
	 * �÷����õ���������
	 * @return ��������
	 */
	public static String getAirSpeedType()
	{
		return airSpeedType;
	}

	
	/**
	 * �÷������ú����Ƿ�ɻ��
	 * @param datameOnBin 56λ2������Ч�����ֶ�
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
	 * �÷�����ú����Ƿ�ɻ��
	 * @return ���غ����Ƿ�ɻ�ã��ַ���"Available"����"Not available"
	 */
	public static String getHeadingSource()
	{
		return headingSource;
	}

	
	/**
	 * �÷�������ˮƽ����
	 * @param datameOnBin 56λ2������Ч�����ֶ�
	 */
	public static void setHeading( String datameOnBin )
	{
		if ( datameOnBin.substring( 13 , 14 ).equals( "1" ) )
		{
			heading = Integer.valueOf( datameOnBin.substring( 14 , 24 ) , 2 ) / 1024.0 * 360 + "";
		}
	}
	
	/**
	 * �÷������ˮƽ����
	 * @return ����ˮƽ����
	 */
	public static String getHeading()
	{
		return heading;
	}
	
	
	/**
	 * �÷�������ˮƽ�ٶ�
	 * @param datameOnBin 56λ2������Ч�����ֶ�
	 */
	public static void setVelocity( String datameOnBin )
	{
		velocity = String.valueOf( Integer.valueOf( datameOnBin.substring( 25 , 35 ) , 2 ) );
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
	 * �÷�����ô�ֱ����Դ
	 * @return ���ش�ֱ����Դ
	 */
	public static String getVerticalRateSource()
	{
		return verticalRateSource;
	}

	
	/**
	 * �÷������ô�ֱ����Դ
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
	 * �÷������ô�ֱ���ʴ�С
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
