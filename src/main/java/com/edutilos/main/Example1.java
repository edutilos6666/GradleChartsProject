package com.edutilos.main; 

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.StringTokenizer;

import javax.swing.BoxLayout;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.general.SeriesException;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.xy.XYZDataset;
import org.jfree.ui.ApplicationFrame;

public class Example1 {
   public static void main(String[] args) {
	   createPieChartFromDatabase();  
  }
   
   private static Random rand = new Random(); 
   private static double generateRandomDouble() { 
	   return rand.nextDouble()* 1000;
   }
   
   
   private static int generateRandomInt() {
	   return rand.nextInt(1000); 
   }
   
   
   
   
   
   private static void createPieChartFromDatabase() {
	   BrowsersDAO dao = new BrowsersDAOMysqlImpl(); 
	   ((BrowsersDAOMysqlImpl)dao).dropTable();
	   ((BrowsersDAOMysqlImpl)dao).createTable();
	   dao.save(new Browser("Chrome", 100.0));
	   dao.save(new Browser("Firefox", 200.0));
	   dao.save(new Browser("Edge", 300.0));
	   dao.save(new Browser("Mozilla", 400.0));
	   dao.save(new Browser("Safari", 500.0));
	   
	   List<Browser> all = dao.findAll(); 
	   DefaultPieDataset dataset = new DefaultPieDataset(); 
	   for(Browser b: all) {
		   dataset.setValue(b.getHeader(), b.getValue());
	   }
	   
	   createPieChartFromDataSet("Browsers Mysql", dataset);
   }
   
   private static void createPieChartFromFile(String fileName)  {
	   try {
		   List<String> headers = new ArrayList<>(); 
		   List<Double> values = new ArrayList<>(); 
		   
		   File file = new File(fileName); 
		   FileInputStream fis = new FileInputStream(file); 
		 BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
		 String line = reader.readLine(); 
		 while(line != null) {
			 System.out.println(line);
			 StringTokenizer tokenizer = new StringTokenizer(line, ","); 
			 headers.add(tokenizer.nextToken()); 
			 values.add(Double.parseDouble(tokenizer.nextToken())); 
			 line = reader.readLine(); 
		 }
		 
		 DefaultPieDataset dataset = new DefaultPieDataset(); 
		 for(int i=0; i< headers.size(); ++i) {
			 dataset.setValue(headers.get(i), values.get(i));
		 }
		 
		 createPieChartFromDataSet("Browsers From File", dataset);
	   } catch(Exception ex) {
		   ex.printStackTrace();
	   } finally {
		   
	   }
   }
   
   private static void createPieChartFromDataSet(String title, DefaultPieDataset dataset) {
	   ApplicationFrame frame = new ApplicationFrame("TimeSeriesChart Example"); 
	   frame.setDefaultCloseOperation(ApplicationFrame.EXIT_ON_CLOSE);
	   frame.setSize(new Dimension(500, 500));
	   
	   JFreeChart chart = ChartFactory.createPieChart(title, dataset, true, true,true); 
        ChartPanel panel = new ChartPanel(chart); 
        frame.setContentPane(panel);
        frame.setVisible(true);
   }
   
   private static  void createTimeSeriesChart()  {
	   ApplicationFrame frame = new ApplicationFrame("TimeSeriesChart Example"); 
	   frame.setDefaultCloseOperation(ApplicationFrame.EXIT_ON_CLOSE);
	   frame.setSize(new Dimension(500, 500));

	   
	   TimeSeries series = new TimeSeries("Random Data");
	   Second second = new Second();
	   double value  = 0 ;
	   Random rand = new Random(); 
	   for(int i=0; i< 4000; ++i) {
		   try {
			   value = value + rand.nextDouble() - 0.5 ;
			   series.add(second , value); 
			   second = (Second)second.next(); 
		   } catch(SeriesException ex) {
			   ex.printStackTrace();
		   }
	   }
	   
	  TimeSeriesCollection coll = new TimeSeriesCollection(); 
	  coll.addSeries(series);
	  /*
	   *     public static JFreeChart createTimeSeriesChart(String title,
            String timeAxisLabel, String valueAxisLabel, XYDataset dataset,
            boolean legend, boolean tooltips, boolean urls)
	   */
	  JFreeChart chart = ChartFactory.createTimeSeriesChart("TimeSeries Example", "Time", "RandomValue", coll, 
			  true, true, true); 
	  ChartPanel panel = new ChartPanel(chart); 
	  frame.setContentPane(panel);
	   frame.setVisible(true);
   }
   
   private static void createBubbleChart() {
	   ApplicationFrame frame = new ApplicationFrame("LineChart Example"); 
	   frame.setDefaultCloseOperation(ApplicationFrame.EXIT_ON_CLOSE);
	   frame.setSize(new Dimension(500, 500));
	   
	   
	   DefaultXYZDataset dataset = new DefaultXYZDataset(); 
	    
	 
	    
	   
	   double d1[] = new double[5], 
			   d2[] = new double [5], 
			   d3[] = new double[5]; 
	   
	   for(int i=0; i< 5; ++i) {
		   d1[i] = generateRandomDouble();
		   d2[i] = generateRandomDouble(); 
		   d3[i] = generateRandomDouble()%500; 
	   }
	   
	   double dataChrome[][] = {d1, d2, d3}; 
	
	   for(int i=0; i< 5; ++i) {
		   d1[i] = generateRandomDouble();
		   d2[i] = generateRandomDouble(); 
		   d3[i] = generateRandomDouble()%500; 
	   }
	
	   double dataFirefox[][] = {d1 , d2 , d3}; 
	   
	   for(int i=0; i< 5; ++i) {
		   d1[i] = generateRandomDouble();
		   d2[i] = generateRandomDouble(); 
		   d3[i] = generateRandomDouble()%500; 
	   }
	
	   double dataEdge[][] = {d1, d2, d3}; 
	   
	   dataset.addSeries("Chrome", dataChrome);
	   dataset.addSeries("Firefox", dataFirefox);
	   dataset.addSeries("Edge", dataEdge);
	   
	   /*
	    *   public static JFreeChart createBubbleChart(String title, String xAxisLabel,
            String yAxisLabel, XYZDataset dataset, PlotOrientation orientation,
            boolean legend, boolean tooltips, boolean urls)
	    */
	   JFreeChart chart = ChartFactory.createBubbleChart("Browsers Bubble", "Browser", "Values", dataset,
			   PlotOrientation.VERTICAL, true, true, true); 
	   ChartPanel panel = new ChartPanel(chart); 
	   frame.setContentPane(panel); 
	   frame.setVisible(true);
   }
   
   
   private static void createXYChart() {
	   ApplicationFrame frame = new ApplicationFrame("LineChart Example"); 
	   frame.setDefaultCloseOperation(ApplicationFrame.EXIT_ON_CLOSE);
	   frame.setSize(new Dimension(500, 500));
	   
	   XYSeries seriesChrome = new XYSeries("Chrome Users"); 
	   seriesChrome.add(generateRandomDouble(), generateRandomDouble());
	   seriesChrome.add(generateRandomDouble(), generateRandomDouble());
	   seriesChrome.add(generateRandomDouble(), generateRandomDouble());
	   seriesChrome.add(generateRandomDouble(), generateRandomDouble());
	   
	   
	   XYSeries seriesFirefox = new XYSeries("Firefox Users"); 
	   seriesChrome.add(generateRandomDouble(), generateRandomDouble());
	   seriesChrome.add(generateRandomDouble(), generateRandomDouble());
	   seriesChrome.add(generateRandomDouble(), generateRandomDouble());
	   seriesChrome.add(generateRandomDouble(), generateRandomDouble());
	   
	   
	   XYSeries seriesEdge  = new XYSeries("Edge Users"); 
	   seriesChrome.add(generateRandomDouble(), generateRandomDouble());
	   seriesChrome.add(generateRandomDouble(), generateRandomDouble());
	   seriesChrome.add(generateRandomDouble(), generateRandomDouble());
	   seriesChrome.add(generateRandomDouble(), generateRandomDouble());
	   
	   
	   XYSeriesCollection coll = new XYSeriesCollection(); 
	   coll.addSeries(seriesChrome);
	   coll.addSeries(seriesFirefox);
	   coll.addSeries(seriesEdge);
	   
	   /*
	    *     public static JFreeChart createXYAreaChart(String title, String xAxisLabel,
            String yAxisLabel, XYDataset dataset, PlotOrientation orientation,
            boolean legend, boolean tooltips, boolean urls)
	    */
	   JFreeChart chart = ChartFactory.createXYLineChart("XYAreaChart", "Salary", "Car Price", coll, 
			   PlotOrientation.VERTICAL, true, true, true); 
	   
	   XYPlot plot = chart.getXYPlot();
	   ChartPanel panel = new ChartPanel(chart); 
	   XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(); 
	   renderer.setSeriesPaint(0, Color.BLUE);
	   renderer.setSeriesPaint(1,  Color.YELLOW);
	   renderer.setSeriesPaint(2, Color.GREEN);
	   renderer.setSeriesStroke(0, new BasicStroke(2.0f));
	   renderer.setSeriesStroke(1,  new BasicStroke(3.0f));
	   renderer.setSeriesStroke(2, new BasicStroke(4.0f));
	   plot.setRenderer(renderer);
	   
	   
	   frame.setContentPane(panel);
	   frame.setVisible(true);
   }
   
   private static void createLineChart()  {
	   ApplicationFrame frame = new ApplicationFrame("LineChart Example"); 
	   frame.setDefaultCloseOperation(ApplicationFrame.EXIT_ON_CLOSE);
	   frame.setSize(new Dimension(500, 500));
	   
	   //line chart for Chrome 
	   List<String> years = Arrays.asList("1970", "1980", "1990", "2000", "2010"); 
	   String browser = "Chrome"; 
	   DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 
	   for(String year: years) {
		   dataset.addValue(generateRandomDouble(), browser, year);
	   }
	   
	   /*
	    *     public static JFreeChart createLineChart(String title,
            String categoryAxisLabel, String valueAxisLabel,
            CategoryDataset dataset, PlotOrientation orientation,
            boolean legend, boolean tooltips, boolean urls)
	    */
	   JFreeChart chartChrome = ChartFactory.createLineChart("Chrome", "Chrome", "Years", dataset, 
			   PlotOrientation.VERTICAL, true, true, true); 
	   ChartPanel panelChrome = new ChartPanel(chartChrome); 
	   BoxLayout mainLayout = new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS); 
	   frame.setLayout(mainLayout);
	   frame.add(panelChrome);
	    
	    
	    //Firefox
	   browser = "Firefox"; 
	    dataset = new DefaultCategoryDataset(); 
		   for(String year: years) {
			   dataset.addValue(generateRandomDouble(), browser, year);
		   }
		   
		   /*
		    *     public static JFreeChart createLineChart(String title,
	            String categoryAxisLabel, String valueAxisLabel,
	            CategoryDataset dataset, PlotOrientation orientation,
	            boolean legend, boolean tooltips, boolean urls)
		    */
		   JFreeChart chartFirefox = ChartFactory.createLineChart("Firefox", "Firefox", "Years", dataset, 
				   PlotOrientation.VERTICAL, true, true, true); 
		   ChartPanel panelFirefox = new ChartPanel(chartFirefox); 
	    frame.add(panelFirefox); 
		   
	    frame.setVisible(true);
   }
   
   
   private static void createBarChart() {
	   ApplicationFrame frame = new ApplicationFrame("BarChart Example");
	   frame.setDefaultCloseOperation(ApplicationFrame.EXIT_ON_CLOSE); 
	   frame.setSize(new Dimension(500, 500));
	   DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 
	   List<String> categories = Arrays.asList("Chrome", "Firefox", "Edge"); 
	   List<String> params = Arrays.asList("Popularity", "Budget", "Userbase", "Speed"); 
	   for(String category:categories) {
		   dataset.addValue(generateRandomDouble(), category, params.get(0));
		   dataset.addValue(generateRandomDouble(), category, params.get(1));
		   dataset.addValue(generateRandomInt(), category, params.get(2));
		   dataset.addValue(generateRandomDouble(), category, params.get(3));
	   }
	   
	   
	   /*
	    *   public static JFreeChart createBarChart(String title,
            String categoryAxisLabel, String valueAxisLabel,
            CategoryDataset dataset, PlotOrientation orientation,
            boolean legend, boolean tooltips, boolean urls)
	    */
	   JFreeChart chart = ChartFactory.createStackedBarChart3D("Browsers War", "Category", "Params", dataset, 
			   PlotOrientation.VERTICAL, true, true, true); 
	   
	   ChartPanel panel = new ChartPanel(chart); 
	   frame.setContentPane(panel);
	   frame.setVisible(true);
   }
   
   private static void createPieChart()  {
	   ApplicationFrame frame = new ApplicationFrame("PieChart Example"); 
	   frame.setDefaultCloseOperation(ApplicationFrame.EXIT_ON_CLOSE);
	   frame.setSize(new Dimension(500, 500));
	   
	   DefaultPieDataset dataSet = new DefaultPieDataset(); 
	   List<String> browsers = Arrays.asList("Chrome", "Firefox", "IE", "Edge", "Opera", "Safari"); 
	   browsers.forEach(b-> {
		   dataSet.setValue(b, generateRandomDouble());
	   });
	   
	   /*
	    *    public static JFreeChart createPieChart3D(String title, PieDataset dataset,
            boolean legend, boolean tooltips, Locale locale)
	    */
	   JFreeChart chart = ChartFactory.createPieChart("Browser", dataSet, true, true, Locale.getDefault()); 
	   frame.setContentPane(new ChartPanel(chart));
	   frame.setVisible(true);
	   
   }
}
