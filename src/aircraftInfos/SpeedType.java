package aircraftInfos;

// �ٶ�������
public class SpeedType 
{
	// ���������� 1��2��3��4
	private static int type;
	
	/**
	 * �÷������ڻ�ȡ����������
	 * @param datameOnBin 56λ2������Ч�����ֶ�
	 * @return ���غ��������ͣ�1��2��3��4���е�һ��������
	 */
	public static void setType( String datameOnBin )
	{
		type = Integer.valueOf( datameOnBin.substring( 5 , 8 ) , 2 );
	}
	
	public static int getType()
	{
		return type;
	}
}
