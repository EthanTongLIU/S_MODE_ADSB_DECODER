package decodeBus;

import java.text.SimpleDateFormat;
import java.util.Date;

import aircraftInfos.AdsbMes;
import aircraftInfos.SpeedAir;
import aircraftInfos.PositionAirborne;
import aircraftInfos.AircraftType;
import aircraftInfos.Callsign;
import aircraftInfos.SpeedGround;
import aircraftInfos.PositionRef;
import aircraftInfos.SpeedType;
import ui.UI;
import util.HexBinOct;

public class ProcessFrame {

	public static AdsbMes processFrame(String frame) {

		// 传入的报文必须是经过过滤好的
		AdsbMes adsbMes = new AdsbMes();

		/*
		 * 通用消息赋值操作
		 */

		// 16进制报文原文
		adsbMes.dataFrame = frame;
		// icao24
		adsbMes.icao24 = adsbMes.dataFrame.substring(2, 8);
		// 2进制ME字段
		adsbMes.datameOnBin = HexBinOct.transformHexStringToBinString(adsbMes.dataFrame.substring(8, 22));
		// 报文的类型
		adsbMes.typeCode = Integer.valueOf(adsbMes.datameOnBin.substring(0, 5), 2);

		/*
		 * 根据类型调用相应的类解码不同的信息
		 */

		// -->时间
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateNowStr = sdf.format(d);
		adsbMes.time = dateNowStr;

		// -->航空器种类与航空器呼号
		if (adsbMes.typeCode >= 1 && adsbMes.typeCode <= 4) {
			// 航空器种类
			AircraftType.setAircraftType(adsbMes.datameOnBin);
			adsbMes.aircraftType = AircraftType.getAircraftType();

			// 航空器呼号
			Callsign.setCallsign(adsbMes.datameOnBin);
			adsbMes.callsign = Callsign.getCallsign().replace("_", ""); // 去掉航班号末尾的_
		}

		// -->地面位置（目前还未对其进行解码操作）
		if (adsbMes.typeCode >= 5 && adsbMes.typeCode <= 8) {
			System.out.println("报文信息为：地面位置" + "不解码");
		}

		// -->空中位置
		if (adsbMes.typeCode >= 9 && adsbMes.typeCode <= 18) {
			// （本地解码）
			PositionRef.setRefLatitude(String.valueOf(UI.getLat0()));
			PositionRef.setRefLongitude(String.valueOf(UI.getLon0()));
			PositionAirborne.setPositionLocally(adsbMes.datameOnBin, PositionRef.getRefLatitude(),
					PositionRef.getRefLongitude());
			PositionAirborne.setAltitude(adsbMes.datameOnBin);
			adsbMes.lat = PositionAirborne.getLatitude();
			adsbMes.lon = PositionAirborne.getLongitude();
			adsbMes.alt = PositionAirborne.getAltitude();

			// （全球解码）
			// 全球解码暂时不使用

		}

		// -->航速
		if (adsbMes.typeCode == 19) {
			SpeedType.setType(adsbMes.datameOnBin);
			if (SpeedType.getType() == 1) {
				// 地速信息

				// 水平速度
				SpeedGround.setVelocity(adsbMes.datameOnBin);
				adsbMes.groundSpeed = SpeedGround.getVelocity();
				// 水平航向
				SpeedGround.setHeading(adsbMes.datameOnBin);
				adsbMes.heading = SpeedGround.getHeading();
				// 爬升率
				SpeedGround.setVerticalRate(adsbMes.datameOnBin);
				adsbMes.verticalRate = SpeedGround.getVerticalRate();

			} else if (SpeedType.getType() == 3) {
				// 空速信息

				// 空速类型
				SpeedAir.setAirSpeedType(adsbMes.datameOnBin);
				adsbMes.airSpeedType = SpeedAir.getAirSpeedType();
				// 航向是否可获得
				SpeedAir.setHeadingSource(adsbMes.datameOnBin);
				SpeedAir.getHeadingSource();
				// 水平航向
				SpeedAir.setHeading(adsbMes.datameOnBin);
				adsbMes.heading = SpeedAir.getHeading();
				// 水平速度
				SpeedAir.setVelocity(adsbMes.datameOnBin);
				adsbMes.airSpeed = SpeedAir.getVelocity();
				// 垂直速率源
				SpeedAir.setVerticalRateSource(adsbMes.datameOnBin);
				adsbMes.verticalRateSource = SpeedAir.getVerticalRateSource();
				// 爬升率
				SpeedAir.setVerticalRate(adsbMes.datameOnBin);
				adsbMes.verticalRate = SpeedAir.getVerticalRate();
			} else if (SpeedType.getType() == 2 || SpeedType.getType() == 4) {
				System.out.println("显示为超音速飞机，目前无法解码！");
			}
		}

		// -->空中位置
		if (adsbMes.typeCode >= 20 && adsbMes.typeCode <= 22) {
			System.out.println("报文信息为：空中位置（w/GNSS Height）" + "  " + "不解码!");
		}

		// 预留信息
		if (adsbMes.typeCode >= 23 && adsbMes.typeCode <= 31) {
			System.out.println("报文信息为：预留信息" + "  " + "不解码!");
		}

		return adsbMes;
	}

}
