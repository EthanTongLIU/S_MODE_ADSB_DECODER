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

		// ����ı��ı����Ǿ������˺õ�
		AdsbMes adsbMes = new AdsbMes();

		/*
		 * ͨ����Ϣ��ֵ����
		 */

		// 16���Ʊ���ԭ��
		adsbMes.dataFrame = frame;
		// icao24
		adsbMes.icao24 = adsbMes.dataFrame.substring(2, 8);
		// 2����ME�ֶ�
		adsbMes.datameOnBin = HexBinOct.transformHexStringToBinString(adsbMes.dataFrame.substring(8, 22));
		// ���ĵ�����
		adsbMes.typeCode = Integer.valueOf(adsbMes.datameOnBin.substring(0, 5), 2);

		/*
		 * �������͵�����Ӧ������벻ͬ����Ϣ
		 */

		// -->ʱ��
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateNowStr = sdf.format(d);
		adsbMes.time = dateNowStr;

		// -->�����������뺽��������
		if (adsbMes.typeCode >= 1 && adsbMes.typeCode <= 4) {
			// ����������
			AircraftType.setAircraftType(adsbMes.datameOnBin);
			adsbMes.aircraftType = AircraftType.getAircraftType();

			// ����������
			Callsign.setCallsign(adsbMes.datameOnBin);
			adsbMes.callsign = Callsign.getCallsign().replace("_", ""); // ȥ�������ĩβ��_
		}

		// -->����λ�ã�Ŀǰ��δ������н��������
		if (adsbMes.typeCode >= 5 && adsbMes.typeCode <= 8) {
			System.out.println("������ϢΪ������λ��" + "������");
		}

		// -->����λ��
		if (adsbMes.typeCode >= 9 && adsbMes.typeCode <= 18) {
			// �����ؽ��룩
			PositionRef.setRefLatitude(String.valueOf(UI.getLat0()));
			PositionRef.setRefLongitude(String.valueOf(UI.getLon0()));
			PositionAirborne.setPositionLocally(adsbMes.datameOnBin, PositionRef.getRefLatitude(),
					PositionRef.getRefLongitude());
			PositionAirborne.setAltitude(adsbMes.datameOnBin);
			adsbMes.lat = PositionAirborne.getLatitude();
			adsbMes.lon = PositionAirborne.getLongitude();
			adsbMes.alt = PositionAirborne.getAltitude();

			// ��ȫ����룩
			// ȫ�������ʱ��ʹ��

		}

		// -->����
		if (adsbMes.typeCode == 19) {
			SpeedType.setType(adsbMes.datameOnBin);
			if (SpeedType.getType() == 1) {
				// ������Ϣ

				// ˮƽ�ٶ�
				SpeedGround.setVelocity(adsbMes.datameOnBin);
				adsbMes.groundSpeed = SpeedGround.getVelocity();
				// ˮƽ����
				SpeedGround.setHeading(adsbMes.datameOnBin);
				adsbMes.heading = SpeedGround.getHeading();
				// ������
				SpeedGround.setVerticalRate(adsbMes.datameOnBin);
				adsbMes.verticalRate = SpeedGround.getVerticalRate();

			} else if (SpeedType.getType() == 3) {
				// ������Ϣ

				// ��������
				SpeedAir.setAirSpeedType(adsbMes.datameOnBin);
				adsbMes.airSpeedType = SpeedAir.getAirSpeedType();
				// �����Ƿ�ɻ��
				SpeedAir.setHeadingSource(adsbMes.datameOnBin);
				SpeedAir.getHeadingSource();
				// ˮƽ����
				SpeedAir.setHeading(adsbMes.datameOnBin);
				adsbMes.heading = SpeedAir.getHeading();
				// ˮƽ�ٶ�
				SpeedAir.setVelocity(adsbMes.datameOnBin);
				adsbMes.airSpeed = SpeedAir.getVelocity();
				// ��ֱ����Դ
				SpeedAir.setVerticalRateSource(adsbMes.datameOnBin);
				adsbMes.verticalRateSource = SpeedAir.getVerticalRateSource();
				// ������
				SpeedAir.setVerticalRate(adsbMes.datameOnBin);
				adsbMes.verticalRate = SpeedAir.getVerticalRate();
			} else if (SpeedType.getType() == 2 || SpeedType.getType() == 4) {
				System.out.println("��ʾΪ�����ٷɻ���Ŀǰ�޷����룡");
			}
		}

		// -->����λ��
		if (adsbMes.typeCode >= 20 && adsbMes.typeCode <= 22) {
			System.out.println("������ϢΪ������λ�ã�w/GNSS Height��" + "  " + "������!");
		}

		// Ԥ����Ϣ
		if (adsbMes.typeCode >= 23 && adsbMes.typeCode <= 31) {
			System.out.println("������ϢΪ��Ԥ����Ϣ" + "  " + "������!");
		}

		return adsbMes;
	}

}
