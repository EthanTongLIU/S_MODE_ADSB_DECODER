package decodeBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableItem;

import aircraftInfos.AdsbMes;
import ui.UI;

public class NetDecodeRunnable implements Runnable {

	// ʵ����ͣ/��������
	public boolean suspended = false;

	// ��¼���յ��ı��ĵ�����
	int numReceived = 0;

	// ��¼�ѽ���ı�������
	int numDecoded = 0;

	// ���ն˿�
	int receivePort = Integer.valueOf(UI.text.getText());

	// ��������Ϣд��TrueTrack
	File file = new File(UI.getTrueTrackFilePath());

	public void run() {
		try {
			// ����socket
			DatagramSocket socket = new DatagramSocket(receivePort);
			// �������ݵ�����
			byte[] arr = new byte[1024];
			// ����packet
			DatagramPacket packet = new DatagramPacket(arr, arr.length);
			// ���յ�һ������
			socket.receive(packet);
			// ��ȡ���ͷ���IP��ַ
			String ip = packet.getAddress().getHostAddress().toString();
			System.out.println("���ͷ�IP��" + ip);
			// ������ͷ�IP�����߳��б���
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					UI.list.setItem(3, "\u2708 ���ͷ�IP: " + ip);
				}
			});

			// �����ļ��������дTrueTrackͷ
			try {
				PrintStream ps = new PrintStream(new FileOutputStream(file));
				ps.println("���� ʱ�� �ɻ���ַ γ�� ���� �߶� ���� ���� ����� ������ ʶ�����");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			// ѭ�����ղ���������
			while (true) {
				// �ж��߳̿���
				if (Thread.currentThread().isInterrupted()) {
					System.out.println("�����߳��ѱ���ֹ��");
					socket.close();
					break;
				}
				synchronized (this) {
					while (suspended) {
						wait();
					}
				}

				arr = new byte[1024];
				packet = new DatagramPacket(arr, arr.length);
				socket.receive(packet);
				// �����յ�������ת��Ϊ�ַ���
				String frame = new String(packet.getData());

				// ����
				if (frame != null) {

					// ** ����Ԥ���� **
					// ����ǽ��մ�"*"�ź�";"�ŵı��ģ�����Ҫȥ��ͷβ
					frame = frame.replace("*", "");
					frame = frame.replace(";", "");
					frame = frame.substring(0, 28).replace(" ", "");
					int judge = PreprocessFrame.isDF17Network(frame);
					// ** ����Ԥ���� **

					if (judge == 1) {

						// System.out.println(frame);
						AdsbMes adsbMes = ProcessFrame.processFrame(frame);
						numDecoded++;

						// ����������
						Display.getDefault().asyncExec(new Runnable() {
							public void run() {

								// �����£�500�����ݱ�����
								if (UI.table.getItemCount() > 500) {
									UI.table.removeAll();
								}

								// �б����£��ѽ���������Ϣ20������
								if ((numDecoded % 20) == 0) {
									UI.list.setItem(11, "\u2708 �ѽ���" + numDecoded + "������");
								}

								// ���� Item ������������
								TableItem tableItem = new TableItem(UI.table, SWT.NONE);
								tableItem.setText(new String[] { String.valueOf(UI.table.getItemCount()),
										adsbMes.time.substring(10), adsbMes.icao24, adsbMes.lat, adsbMes.lon,
										adsbMes.alt, adsbMes.groundSpeed, adsbMes.heading, adsbMes.callsign,
										adsbMes.verticalRate });

								// ������Ŀ��ɫ��ʾ����
								if ((UI.table.getItemCount() & 1) != 0) {
									tableItem.setBackground(new Color(null, 185, 227, 217));
								} else {
									tableItem.setBackground(new Color(null, 174, 221, 129));
								}

							}
						});

						// д�뵽TrueTrack��
						try {
							FileWriter fw = new FileWriter(file, true);
							PrintWriter pw = new PrintWriter(fw);
							pw.println(adsbMes.time + " " + adsbMes.icao24 + " " + adsbMes.lat + " " + adsbMes.lon + " "
									+ adsbMes.alt + " " + adsbMes.groundSpeed + " " + adsbMes.heading + " "
									+ adsbMes.callsign + " " + adsbMes.verticalRate + " null");
							pw.flush();
							fw.flush();
							pw.close();
							fw.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else {
						System.out.println("��������������");
					}

				}

				numReceived++;
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						// �ѽ���������Ϣ��ÿ20������һ��
						if ((numReceived % 20) == 0) {
							UI.list.setItem(9, "\u2708 �ѽ���" + numReceived + "������");
						}
					}
				});
			}

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * ��ͣ
	 */
	public void suspend() {
		suspended = true;
	}

	/**
	 * ����
	 */
	synchronized void resume() {
		suspended = false;
		notify();
	}

}
