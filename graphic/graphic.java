package graphic;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import estimators.EomLee;
import estimators.Estimator;
import estimators.LowerBound;
import general.Metrics;

public class graphic extends JFrame {
	
	public int inicialNumberTags;
	public int incrementTagsBy;
	public int maxNumberTags;
	
	public List<Metrics> lbMetrics;
	public List<Metrics> elMetrics;

    public graphic(int choosenEstimators,int incTagsBy, int iniNumberTags, int maxNumTags) {
        
        this.incrementTagsBy = incTagsBy;
		this.inicialNumberTags = iniNumberTags;
		this.maxNumberTags = maxNumTags;
        
        if(choosenEstimators==1) {
			this.lbMetrics = new ArrayList<Metrics>();
		}else if(choosenEstimators==2) {
			this.elMetrics = new ArrayList<Metrics>();
		}else {
			this.lbMetrics = new ArrayList<Metrics>();
			this.elMetrics = new ArrayList<Metrics>();
		}
		
    }
    
 
    private void initUI(XYDataset dataset, String eixoY) {

        JFreeChart chart = createChart(dataset, eixoY);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Line chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private XYDataset createDatasetEmpty() {
    	XYSeries series1 = new XYSeries("LowerBound");
    	XYSeries series2 = new XYSeries("Eomlee");
    	
    	int numberTags = this.inicialNumberTags;
    	
    	for(int i=0;numberTags<=this.maxNumberTags;i++) {
    		
    		series1.add(numberTags, this.lbMetrics.get(i).getNumberEmptySlots());
    		series2.add(numberTags, this.elMetrics.get(i).getNumberEmptySlots());
			
			numberTags+=this.incrementTagsBy;
		}        

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);

        return dataset;
    }
    
    private XYDataset createDatasetCollision() {
    	XYSeries series1 = new XYSeries("LowerBound");
    	XYSeries series2 = new XYSeries("Eomlee");
    	
    	int numberTags = this.inicialNumberTags;
    	
    	for(int i=0;numberTags<=this.maxNumberTags;i++) {
    		
    		series1.add(numberTags, this.lbMetrics.get(i).getNumberCollisionSlots());
    		series2.add(numberTags, this.elMetrics.get(i).getNumberCollisionSlots());
			
			numberTags+=this.incrementTagsBy;
		}        

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);

        return dataset;
    }
    
    private XYDataset createDatasetEfficiency() {
    	XYSeries series1 = new XYSeries("LowerBound");
    	XYSeries series2 = new XYSeries("Eomlee");
    	
    	int numberTags = this.inicialNumberTags;
    	
    	for(int i=0;numberTags<=this.maxNumberTags;i++) {
    		
    		series1.add(numberTags, this.lbMetrics.get(i).getEfficiency());
    		series2.add(numberTags, this.elMetrics.get(i).getEfficiency());
			
			numberTags+=this.incrementTagsBy;
		}        

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);

        return dataset;
    }
    
    private XYDataset createDatasetTotalSlots() {
    	XYSeries series1 = new XYSeries("LowerBound");
    	XYSeries series2 = new XYSeries("Eomlee");
    	
    	int numberTags = this.inicialNumberTags;
    	
    	for(int i=0;numberTags<=this.maxNumberTags;i++) {
    		
    		series1.add(numberTags, this.lbMetrics.get(i).getNumberTotalSlots());
    		series2.add(numberTags, this.elMetrics.get(i).getNumberTotalSlots());
			
			numberTags+=this.incrementTagsBy;
		}        

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);

        return dataset;
    }

    private JFreeChart createChart(final XYDataset dataset, String eixoY) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "SisCom", 
                eixoY, 
                "Slots", 
                dataset, 
                PlotOrientation.VERTICAL,
                true, 
                true, 
                false
        );

        XYPlot plot = chart.getXYPlot();
        
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));        

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

//        plot.setRangeGridlinesVisible(false);
//        plot.setDomainGridlinesVisible(false);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);
        
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("SisCom",
                        new Font("Serif", Font.BOLD, 18)
                )
        );

        return chart;
    }
    
    public void plotGraphic() {
    	initUI(createDatasetTotalSlots(),"Número de Slots");
        SwingUtilities.invokeLater(() -> {
            this.setVisible(true);
        });
        initUI(createDatasetEfficiency(),"Eficiencia");
        SwingUtilities.invokeLater(() -> {
            this.setVisible(true);
        });
        initUI(createDatasetEmpty(),"Número de Slots Vazios");
        SwingUtilities.invokeLater(() -> {
            this.setVisible(true);
        });
        initUI(createDatasetCollision(),"Número de Slots em Colisões");
        SwingUtilities.invokeLater(() -> {
            this.setVisible(true);
        });
        
    }

    public static void main(String[] args) {

         
    }
}