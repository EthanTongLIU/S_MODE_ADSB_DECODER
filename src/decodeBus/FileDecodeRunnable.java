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

	// 实现暂停/继续操作
	public boolean suspended = false; 
	
	// 解码后的信息写入TrueTrack
	File file = new File(UI.getTrueTrackFilePath()); 
	
	// 记录已解码报文的数量
	int numDecoded = 0;

	public void run() {
		
		try {

			// 创建文件输出流并写TrueTrack头
			PrintStream ps = new PrintStream(new FileOutputStream(file));
			ps.println("日期 时间 飞机地址 纬度 经度 高度 地速 航向 航班号 爬升率 识别代码");
			
			// 创建文件输入流
			FileInputStream fis = new FileInputStream(UI.getDataFileName());
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
		
			// 循环读文件并解码数据
			while ((br.readLine()) != null) {
				// 中断线程控制
				if (Thread.currentThread().isInterrupted()) {
					System.out.println("解码线程已被终止！");
					break;
				}
				synchronized (this) {
					while (suspended) {
						wait();
					}
				}

				// 读入一行数据
				String frame = br.readLine();

				// 解码
				if (frame != null) {
					
					// ** 报文预处理 **
					// 如果是接收带"*"号和";"号的报文，则需要去掉头尾
					frame = frame.replace("*", "");
					frame = frame.replace(";", "");
					int judge = PreprocessFrame.isDF17(frame);
					// ** 报文预处理 **
					
					if (judge == 1) {

						// System.out.println(frame);
						AdsbMes adsbMes = ProcessFrame.processFrame(frame);
						numDecoded++;
						
						// 输出到表格中
						Display.getDefault().asyncExec(new Runnable() {
							public void run() {

								// 表格更新：500条数据表格更新
								if(UI.table.getItemCount()>500) {
									UI.table.removeAll();
								}
								
								// 列表框更新：已解码数量信息20条更新
								if( (numDecoded%20)==0 ) {
									UI.list.setItem(11, "\u2708 已解码" + numDecoded + "条报文");
								}
								
								// 创建 Item 并添加至表格中
								TableItem tableItem = new TableItem(UI.table, SWT.NONE);
								tableItem.setText(new String[] { String.valueOf(UI.table.getItemCount()), adsbMes.time.substring(10),
										adsbMes.icao24, adsbMes.lat, adsbMes.lon, adsbMes.alt, adsbMes.groundSpeed,
										adsbMes.heading, adsbMes.callsign, adsbMes.verticalRate });
								
								// 表格相邻着色区分
								if ((UI.table.getItemCount() & 1) != 0) {
									tableItem.setBackground(new Color(null, 185, 227, 217));
								} else {
									tableItem.setBackground(new Color(null, 174, 221, 129));
								}

							}
						});

						// 写入到TrueTrack中
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
						System.out.println("不解码这条报文");
					}

				}

			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}

	}

	/**
	 * 暂停
	 */
	public void suspend() {
		suspended = true;
	}

	/**
	 * 继续
	 */
	synchronized void resume() {
		suspended = false;
		notify();
	}

}
