package aircraftInfos;

// �ο�λ�ã�γ��+����
public class PositionRef 
{
	// �ο�γ��
	private static String latitude;
	// �ο�����
	private static String longitude;
	
	/**
	 * �÷����������òο�γ�ȵ�ֵ
	 * @param refLatitude �ò���Ϊ�ο�γ��
	 */
	public static void setRefLatitude( String refLatitude )
	{
		latitude = refLatitude;
	}
	
	/**
	 * �÷������ڵõ��ο�γ��ֵ
	 * @return ���زο�γ��ֵ
	 */
	public static String getRefLatitude()
	{
		return latitude;
	}
	
	
	/**
	 * �÷����������òο����ȵ�ֵ
	 * @param refLongitude �ò���Ϊ�ο����ȵ�ֵ
	 */
	public static void setRefLongitude( String refLongitude )
	{
		longitude = refLongitude;
	}
	
	/**
	 * �÷������ڻ�òο����ȵ�ֵ
	 * @return ���زο����ȵ�ֵ
	 */
	public static String getRefLongitude()
	{
		return longitude;
	}
}
