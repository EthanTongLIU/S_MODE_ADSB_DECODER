package ui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import decodeBus.FileDecodeRunnable;
import decodeBus.NetDecodeRunnable;
import util.DegreeDigital;
import org.eclipse.wb.swt.SWTResourceManager;

public class UI {

	protected Shell shell;

	// 本地报文文件选择目录
	private static FileDialog fileDialog;

	// 本地报文文件路径
	private static String dataFileName;

	// dataFileName 的 getter
	public static String getDataFileName() {
		return dataFileName;
	}

	// 输出文件路径
	private static String trueTrackFilePath = "D:\\new\\TrueTrack.txt";

	// 设置输出文件路径
	public static void setTrueTrackFilePath(String path) {
		trueTrackFilePath = path;
	}

	// 获得输出文件路径
	public static String getTrueTrackFilePath() {
		return trueTrackFilePath;
	}

	// 图标路径
	private static String logoPath = null;

	// 设置图标路径
	public static void setLogoPath() {
		logoPath = System.getProperty("user.dir") + "\\src\\Img\\icon_1.ico";
	}

	// 显示表格
	public static Table table;
	// 显示列表
	public static List list;
	// 解码模式
	public static String decodeMode;
	// 端口文本
	public static Text text;
	// 端口
	private static String port;

	// 设置端口
	public static void setPort() {
		port = "8888";
	}

	// IP
	private static String IP;

	// 6个文本框，用于填经纬度
	private Text text_1;
	private Text text_2;
	private Text text_3;
	private Text text_4;
	private Text text_5;
	private Text text_6;

	// 接收机位置
	private static double lat0;
	private static double lon0;

	// lat0 的 getter
	public static double getLat0() {
		return lat0;
	}

	// lon0 的 getter
	public static double getLon0() {
		return lon0;
	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			UI window = new UI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell(SWT.MIN | SWT.CLOSE);
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		setLogoPath();
		String path = logoPath;
		shell.setImage(SWTResourceManager.getImage(path));
		shell.setSize(1030, 851);
		shell.setText("ADS-B\u6D88\u606F\u89E3\u7801\u7CFB\u7EDF");

		ToolBar toolBar = new ToolBar(shell, SWT.FLAT | SWT.RIGHT);
		toolBar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		toolBar.setBounds(0, 0, 1012, 28);

		ToolItem toolItem = new ToolItem(toolBar, SWT.NONE);
		toolItem.setText("\u5173\u4E8E");

		ToolItem toolItem_1 = new ToolItem(toolBar, SWT.NONE);
		toolItem_1.setText("\u5E2E\u52A9");

		Composite composite = new Composite(shell, SWT.BORDER);
		composite.setBounds(0, 30, 1012, 62);

		Button button = new Button(composite, SWT.NONE);
		button.setBounds(0, 0, 58, 58);
		button.setText("\u5F00\u59CB");

		Button button_1 = new Button(composite, SWT.NONE);
		button_1.setBounds(64, 0, 58, 58);
		button_1.setText("\u6682\u505C");

		Button button_2 = new Button(composite, SWT.NONE);
		button_2.setBounds(192, 0, 58, 58);
		button_2.setText("\u505C\u6B62");

		Button button_5 = new Button(composite, SWT.NONE);
		button_5.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				File file = new File(trueTrackFilePath);
				try {
					Desktop.getDesktop().open(file);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		button_5.setBounds(320, 0, 58, 58);
		button_5.setText("\u8F93\u51FA");

		Button button_3 = new Button(composite, SWT.NONE);
		button_3.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				table.removeAll();
			}
		});
		button_3.setBounds(256, 0, 58, 58);
		button_3.setText("\u6E05\u7A7A");

		Canvas canvas = new Canvas(composite, SWT.NONE);
		canvas.setBounds(950, 0, 58, 58);

		Button button_8 = new Button(composite, SWT.NONE);
		button_8.setBounds(128, 0, 58, 58);
		button_8.setText("\u7EE7\u7EED");

		final Label lblNewLabel = new Label(composite, SWT.WRAP); // 时间框
		lblNewLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		lblNewLabel.setFont(SWTResourceManager.getFont("微软雅黑", 15, SWT.BOLD));
		lblNewLabel.setBounds(538, 12, 259, 38);
		new Thread() {// 线程操作
			public void run() {
				while (true) {
					try {
						// 对Label进行实时刷新，需要加上这句
						lblNewLabel.getDisplay().asyncExec(new Runnable() {
							public void run() {
								// 设置时间 ，格式化输出时间
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								String s = sdf.format(new Date());
								lblNewLabel.setText(s);// 输出到Label上
							}
						});
						Thread.sleep(1000);// 每隔一秒刷新一次
					} catch (Exception e) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}.start();

		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				setLogoPath();
				Image icon = new Image(null, logoPath);
				e.gc.drawImage(icon, 0, 0, icon.getBounds().width, icon.getBounds().height, 0, 0, 58, 58);
				e.gc.dispose();
				icon.dispose();
			}
		});

		Group group = new Group(shell, SWT.NONE);
		group.setText("\u89E3\u7801\u540E\u7684\u6D88\u606F");
		group.setBounds(10, 98, 703, 696);

		table = new Table(group, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		table.setHeaderVisible(true);
		table.setBounds(10, 25, 683, 661);

		TableColumn tableColumn = new TableColumn(table, SWT.CENTER);
		tableColumn.setMoveable(true);
		tableColumn.setText("\u5E8F\u53F7");
		tableColumn.setWidth(68);

		TableColumn tableColumn_1 = new TableColumn(table, SWT.CENTER);
		tableColumn_1.setToolTipText("");
		tableColumn_1.setWidth(100);
		tableColumn_1.setText("\u65F6\u95F4");

		TableColumn tableColumn_2 = new TableColumn(table, SWT.CENTER);
		tableColumn_2.setWidth(100);
		tableColumn_2.setText("\u98DE\u673A\u5730\u5740");

		TableColumn tableColumn_3 = new TableColumn(table, SWT.CENTER);
		tableColumn_3.setWidth(100);
		tableColumn_3.setText("\u7EAC\u5EA6");

		TableColumn tableColumn_4 = new TableColumn(table, SWT.CENTER);
		tableColumn_4.setWidth(100);
		tableColumn_4.setText("\u7ECF\u5EA6");

		TableColumn tblclmnft = new TableColumn(table, SWT.CENTER);
		tblclmnft.setWidth(100);
		tblclmnft.setText("\u9AD8\u5EA6(ft)");

		TableColumn tblclmnknot = new TableColumn(table, SWT.CENTER);
		tblclmnknot.setWidth(100);
		tblclmnknot.setText("\u5730\u901F(knot)");

		TableColumn tableColumn_7 = new TableColumn(table, SWT.CENTER);
		tableColumn_7.setWidth(100);
		tableColumn_7.setText("\u822A\u5411");

		TableColumn tableColumn_8 = new TableColumn(table, SWT.CENTER);
		tableColumn_8.setWidth(100);
		tableColumn_8.setText("\u822A\u73ED\u53F7");

		TableColumn tblclmnftmin = new TableColumn(table, SWT.CENTER);
		tblclmnftmin.setWidth(111);
		tblclmnftmin.setText("\u722C\u5347\u7387(ft/min)");

		Group group_2 = new Group(shell, SWT.NONE);
		group_2.setText("\u7CFB\u7EDF\u4FE1\u606F");
		group_2.setBounds(719, 414, 283, 380);

		list = new List(group_2, SWT.BORDER);
		list.setBounds(10, 23, 261, 347);

		Group group_3 = new Group(shell, SWT.NONE);
		group_3.setText("\u89E3\u7801\u6A21\u5F0F");
		group_3.setBounds(719, 98, 283, 196);

		Button button_4 = new Button(group_3, SWT.RADIO);
		button_4.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				decodeMode = "本地文件解码";
				list.redraw();
			}
		});
		button_4.setBounds(77, 31, 119, 20);
		button_4.setText("\u672C\u5730\u6587\u4EF6\u89E3\u7801");

		Button button_6 = new Button(group_3, SWT.RADIO);
		button_6.setSelection(true);
		button_6.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				decodeMode = "网络实时解码";
				list.redraw();
			}
		});
		button_6.setBounds(77, 107, 119, 20);
		button_6.setText("\u7F51\u7EDC\u5B9E\u65F6\u89E3\u7801");

		Label label = new Label(group_3, SWT.NONE);
		label.setBounds(54, 143, 76, 20);
		label.setText("\u63A5\u6536\u7AEF\u53E3\uFF1A");

		text = new Text(group_3, SWT.BORDER);
		setPort();
		text.setText(port);
		text.setBounds(140, 140, 94, 26);

		Button button_7 = new Button(group_3, SWT.NONE);
		button_7.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				fileDialog = new FileDialog(shell, SWT.OPEN | SWT.MULTI);
				fileDialog.setFilterPath("C:/");
				fileDialog.setFilterNames(new String[] { "txt文件(*.txt)" });
				fileDialog.setFilterExtensions(new String[] { "*.txt" });
				fileDialog.setText("选择报文文件");
				dataFileName = fileDialog.open();
			}
		});
		button_7.setBounds(54, 64, 180, 30);
		button_7.setText("\u6253\u5F00\u672C\u5730\u539F\u59CB\u62A5\u6587\u6587\u4EF6");

		Group group_1 = new Group(shell, SWT.NONE);
		group_1.setText("\u63A5\u6536\u673A\u4F4D\u7F6E");
		group_1.setBounds(719, 300, 283, 108);

		Label lble = new Label(group_1, SWT.NONE);
		lble.setBounds(30, 25, 38, 20);
		lble.setText("\u7ECF\u5EA6: ");

		Label lblN = new Label(group_1, SWT.NONE);
		lblN.setBounds(30, 69, 38, 20);
		lblN.setText("\u7EAC\u5EA6: ");

		text_1 = new Text(group_1, SWT.BORDER);
		text_1.setText("117");
		text_1.setBounds(70, 22, 47, 26);

		text_2 = new Text(group_1, SWT.BORDER);
		text_2.setText("39");
		text_2.setBounds(70, 66, 47, 26);

		text_3 = new Text(group_1, SWT.BORDER);
		text_3.setText("21");
		text_3.setBounds(126, 22, 47, 26);

		text_4 = new Text(group_1, SWT.BORDER);
		text_4.setText("15.46");
		text_4.setBounds(180, 22, 47, 26);

		text_5 = new Text(group_1, SWT.BORDER);
		text_5.setText("06");
		text_5.setBounds(126, 66, 47, 26);

		text_6 = new Text(group_1, SWT.BORDER);
		text_6.setText("36.11");
		text_6.setBounds(180, 66, 47, 26);

		Label label_3 = new Label(group_1, SWT.NONE);
		label_3.setText("\u00B0");
		label_3.setBounds(118, 25, 6, 20);

		Label label_4 = new Label(group_1, SWT.NONE);
		label_4.setBounds(174, 25, 6, 20);
		label_4.setText("\u2032");

		Label lblE = new Label(group_1, SWT.NONE);
		lblE.setBounds(228, 25, 39, 20);
		lblE.setText("\u2033 E");

		Label label_6 = new Label(group_1, SWT.NONE);
		label_6.setBounds(118, 69, 6, 20);
		label_6.setText("\u00B0");

		Label label_7 = new Label(group_1, SWT.NONE);
		label_7.setBounds(174, 69, 8, 20);
		label_7.setText("\u2032");

		Label lblN_1 = new Label(group_1, SWT.NONE);
		lblN_1.setBounds(228, 69, 39, 20);
		lblN_1.setText("\u2033 N");

		// 为列表2添加显示文字
		list.removeAll();
		port = text.getText();
		try {
			IP = InetAddress.getLocalHost().getHostAddress().toString();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		list.add("+++++++++++++++++++++++++++++++++++++++++++");
		list.add("\u2708 输出文件位置: " + trueTrackFilePath);
		list.add("+++++++++++++++++++++++++++++++++++++++++++");
		list.add("\u2708 发送方IP:");
		list.add("+++++++++++++++++++++++++++++++++++++++++++");
		list.add("\u2708 接收方IP: " + IP);
		list.add("+++++++++++++++++++++++++++++++++++++++++++");
		list.add("\u2708 接收端口: " + port);
		list.add("+++++++++++++++++++++++++++++++++++++++++++");
		list.add("\u2708 已接收0条报文");
		list.add("+++++++++++++++++++++++++++++++++++++++++++");
		list.add("\u2708 已解码0条报文");
		list.add("+++++++++++++++++++++++++++++++++++++++++++");

		// 为“开始”按钮添加响应事件
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {

				// 更新list的port
				port = text.getText();
				list.setItem(7, "\u2708 接收端口: " + text.getText());
				
				// 设置接收机位置
				lon0 = DegreeDigital.ConvertDMSToDigital(text_1.getText(), text_3.getText(), text_4.getText());
				lat0 = DegreeDigital.ConvertDMSToDigital(text_2.getText(), text_5.getText(), text_6.getText());
				System.out.print(lon0 + " " + lat0);

				// 本地解码
				if (button_4.getSelection()) {

					// 创建本地解码线程
					Runnable fileDecodeJob = new FileDecodeRunnable();
					Thread fileDecodeThread = new Thread(fileDecodeJob);
					fileDecodeThread.start();

					// 为“暂停”按钮添加响应事件
					button_1.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							fileDecodeThread.suspend();
						}
					});

					// 为“继续”按钮添加响应事件
					button_8.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							fileDecodeThread.resume();
						}
					});

					// 为“停止”按钮添加响应事件
					button_2.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							fileDecodeThread.interrupt();
						}
					});

				}

				// 网络解码
				if (button_6.getSelection()) {

					// 创建网络解码线程
					Runnable netDecodeJob = new NetDecodeRunnable();
					Thread netDecodeThread = new Thread(netDecodeJob);
					netDecodeThread.start();

					// 为“暂停”按钮添加响应事件
					button_1.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							netDecodeThread.suspend();
						}
					});

					// 为“继续”按钮添加响应事件
					button_8.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							netDecodeThread.resume();
						}
					});

					// 为“停止”按钮添加响应事件
					button_2.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							netDecodeThread.interrupt();
						}
					});

				}

			}
		});

		// 为表格添加双击响应事件，该事件目的是将报文的信息索引传递给原始信息分组，从而显示该条报文对应的原始信息
		table.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDoubleClick(org.eclipse.swt.events.MouseEvent e) {
				// 找到双击位置索引
				int selIndex = table.getSelectionIndex();
				System.out.println(selIndex);
			}
		});

		// 在关闭窗口时关闭所有线程
		shell.addShellListener(new ShellAdapter() {
			public void shellClosed(final ShellEvent e) {
				System.exit(0);
			}
		});

	}

}
