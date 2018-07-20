package decodeBus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableItem;

import aircraftInfos.AdsbMes;
import ui.UI;

public class FileDecodeRunnable implements Runnable {

	// ʵ����ͣ/��������
	public boolean suspended = false; 
	
	// ��������Ϣд��TrueTrack
	File file = new File(UI.getTrueTrackFilePath()); 
	
	// ��¼�ѽ��뱨�ĵ�����
	int numDecoded = 0;

	public void run() {
		
		try {

			// �����ļ��������дTrueTrackͷ
			PrintStream ps = new PrintStream(new FileOutputStream(file));
			ps.println("���� ʱ�� �ɻ���ַ γ�� ���� �߶� ���� ���� ����� ������ ʶ�����");
			
			// �����ļ�������
			FileInputStream fis = new FileInputStream(UI.getDataFileName());
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
		
			// ѭ�����ļ�����������
			while ((br.readLine()) != null) {
				// �ж��߳̿���
				if (Thread.currentThread().isInterrupted()) {
					System.out.println("�����߳��ѱ���ֹ��");
					break;
				}
				synchronized (this) {
					while (suspended) {
						wait();
					}
				}

				// ����һ������
				String frame = br.readLine();

				// ����
				if (frame != null) {
					
					// ** ����Ԥ���� **
					// ����ǽ��մ�"*"�ź�";"�ŵı��ģ�����Ҫȥ��ͷβ
					frame = frame.replace("*", "");
					frame = frame.replace(";", "");
					int judge = PreprocessFrame.isDF17(frame);
					// ** ����Ԥ���� **
					
					if (judge == 1) {

						// System.out.println(frame);
						AdsbMes adsbMes = ProcessFrame.processFrame(frame);
						numDecoded++;
						
						// ����������
						Display.getDefault().asyncExec(new Runnable() {
							public void run() {

								// �����£�500�����ݱ�����
								if(UI.table.getItemCount()>500) {
									UI.table.removeAll();
								}
								
								// �б����£��ѽ���������Ϣ20������
								if( (numDecoded%20)==0 ) {
									UI.list.setItem(11, "\u2708 �ѽ���" + numDecoded + "������");
								}
								
								// ���� Item ������������
								TableItem tableItem = new TableItem(UI.table, SWT.NONE);
								tableItem.setText(new String[] { String.valueOf(UI.table.getItemCount()), adsbMes.time.substring(10),
										adsbMes.icao24, adsbMes.lat, adsbMes.lon, adsbMes.alt, adsbMes.groundSpeed,
										adsbMes.heading, adsbMes.callsign, adsbMes.verticalRate });
								
								// ���������ɫ����
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
