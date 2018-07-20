package aircraftInfos;

// 速度子类型
public class SpeedType 
{
	// 航速子类型 1、2、3、4
	private static int type;
	
	/**
	 * 该方法用于获取航速子类型
	 * @param datameOnBin 56位2进制有效数据字段
	 * @return 返回航速子类型（1、2、3、4其中的一个整数）
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
