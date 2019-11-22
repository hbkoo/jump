package jump;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class Main {

	public static void main(String[] args) {
		
//		Date time = new Date();
//		System.out.println(time.toString());
//		System.out.println(time.getDate());
//		System.out.println(time.getDay());
//		System.out.println(time.getMonth());
//		time.setMonth(time.getMonth()-1);
//		System.out.println(time.getMonth());
//		System.out.println(time.toString());
//		
//		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		System.out.println(format.format(time));
		
		test();
		
		
//
//		Unit unit = Unit.getInstance();
//
//		Runtime runtime = Runtime.getRuntime();
//
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//
//				while (true) {
//					try {
//						System.out.println();
//						unit.screenANDsave(runtime);
//						long time = unit.getTime();
//						unit.jump(time, runtime);
//						System.out.println("time=" + time);
//						System.out.println();
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}).start();

	}
	
	public static void test() {

		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("A", new Double(20));
		dataset.setValue("B", new Double(20));
		dataset.setValue("C", new Double(40));
		dataset.setValue("D", new Double(10));
		dataset.setValue("E", new Double(10));
		dataset.setValue("F", new Double(10));
		dataset.setValue("G", new Double(10));
		dataset.setValue("H", new Double(10));
		dataset.setValue("I", new Double(10));
		dataset.setValue("J", new Double(10));
		dataset.setValue("K", new Double(10));

		JFreeChart chart = ChartFactory.createPieChart("Mobile Sales", // chart
				dataset, // data
				true, // include legend
				true, false);
		setChart(chart);
		PiePlot pieplot = (PiePlot) chart.getPlot();
		pieplot.setSectionPaint("A", Color.decode("#749f83"));
		pieplot.setSectionPaint("B", Color.decode("#2f4554"));
		pieplot.setSectionPaint("C", Color.decode("#61a0a8"));
		pieplot.setSectionPaint("D", Color.decode("#d48265"));
		pieplot.setSectionPaint("E", Color.decode("#91c7ae"));
		pieplot.setSectionPaint("F", Color.decode("#c23531"));
		pieplot.setSectionPaint("G", Color.decode("#ca8622"));
		pieplot.setSectionPaint("H", Color.decode("#bda29a"));
		pieplot.setSectionPaint("I", Color.decode("#6e7074"));
		pieplot.setSectionPaint("J", Color.decode("#546570"));
		pieplot.setSectionPaint("K", Color.decode("#c4ccd3"));
		
//		FileOutputStream fos_jpg = null;
//		try {
//			fos_jpg = new FileOutputStream("D:/test/result.jpg");
//			ChartUtilities.writeChartAsJPEG(fos_jpg, 1.0f, chart, 335,260,null);
//			fos_jpg.close();
//		} catch (FileNotFoundException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
		
		ChartFrame cf = new ChartFrame("柱状图", chart);
		// cf.pack();
//		// // 设置图片大小
		cf.setSize(500, 300);
		// // 设置图形可见
		cf.setVisible(true);
		
		
	}
	
	
	public static void setChart(JFreeChart chart) {
		chart.setTextAntiAlias(true);
		
		PiePlot pieplot = (PiePlot) chart.getPlot();
		// 设置图表背景颜色
		pieplot.setBackgroundPaint(ChartColor.WHITE);
		pieplot.setLabelBackgroundPaint(null);// 标签背景颜色 
		pieplot.setLabelOutlinePaint(null);// 标签边框颜色 
		pieplot.setLabelShadowPaint(null);// 标签阴影颜色 
		pieplot.setOutlinePaint(null); // 设置绘图面板外边的填充颜色 
		pieplot.setShadowPaint(null); // 设置绘图面板阴影的填充颜色		
		pieplot.setSectionOutlinesVisible(false);        
		pieplot.setNoDataMessage("没有可供使用的数据！");  
	}

}


