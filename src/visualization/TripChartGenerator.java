package visualization;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import exceptions.InvalidAccommodationDataException;
import travel.Trip;

public class TripChartGenerator {

    private static void applyChartStyling(JFreeChart chart) {
        Font titleFont = new Font("Bookman Old Style", Font.BOLD, 36);
        Font axisLabelFont = new Font("Bookman Old Style", Font.BOLD, 27);
        Font tickLabelFont = new Font("Bookman Old Style", Font.PLAIN, 18);
        Font legendFont = new Font("Bookman Old Style", Font.BOLD, 23);
        Font pieLabelFont = new Font("Bookman Old Style", Font.ITALIC, 23);

        chart.getTitle().setFont(titleFont);
        if (chart.getLegend() != null) {
            chart.getLegend().setItemFont(legendFont);
        }

        if (chart.getPlot() instanceof CategoryPlot) {
            CategoryPlot plot = (CategoryPlot) chart.getPlot();
            CategoryAxis domainAxis = plot.getDomainAxis();
            ValueAxis rangeAxis = plot.getRangeAxis();
            domainAxis.setLabelFont(axisLabelFont);
            domainAxis.setTickLabelFont(tickLabelFont);
            rangeAxis.setLabelFont(axisLabelFont);
            rangeAxis.setTickLabelFont(tickLabelFont);
        }

        if (chart.getPlot() instanceof PiePlot) {
            @SuppressWarnings("unchecked")
            PiePlot<String> plot = (PiePlot<String>) chart.getPlot();
            plot.setLabelFont(pieLabelFont);
            plot.setBackgroundPaint(Color.WHITE);
            plot.setOutlineVisible(false);
            plot.setSectionOutlinesVisible(false);
            plot.setShadowPaint(null);
            plot.setLabelBackgroundPaint(new Color(245, 245, 245, 180));
        }
    }

    public static void generateCostBarChart(Trip[] trips, int count) throws IOException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < count; i++) {
            try {
                double cost = trips[i].calculateTotalCost(trips[i].getDuration());
                dataset.addValue(cost, "Total Cost", trips[i].getTripId());
            } catch (InvalidAccommodationDataException e) {
                System.err.println("Skipping trip " + trips[i].getTripId() + " due to invalid data: " + e.getMessage());
            }
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Trip Costs",
                "Trip ID",
                "Cost ($)",
                dataset
        );
        applyChartStyling(chart);
        ChartUtils.saveChartAsPNG(new File("output/trip_cost_bar_chart.png"), chart, 800, 600);
    }

    public static void generateDestinationPieChart(Trip[] trips, int count) throws IOException {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        for (int i = 0; i < count; i++) {
            String destination = trips[i].getDestination();
            if (dataset.getIndex(destination) != -1) {
                double value = dataset.getValue(destination).doubleValue();
                dataset.setValue(destination, value + 1);
            } else {
                dataset.setValue(destination, 1);
            }
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Trips per Destination",
                dataset,
                true,
                true,
                false
        );
        applyChartStyling(chart);
        ChartUtils.saveChartAsPNG(new File("output/trips_per_destination_pie.png"), chart, 800, 600);
    }

    public static void generateDurationLineChart(Trip[] trips, int count) throws IOException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < count; i++) {
            dataset.addValue(trips[i].getDuration(), "Duration (days)", trips[i].getTripId());
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Trip Duration",
                "Trip ID",
                "Duration (days)",
                dataset
        );
        applyChartStyling(chart);

        CategoryPlot plot = chart.getCategoryPlot();
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesStroke(0, new BasicStroke(3.0f));
        renderer.setSeriesPaint(0, Color.MAGENTA);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesShapesFilled(0, true);

        ChartUtils.saveChartAsPNG(new File("output/trip_duration_line_chart.png"), chart, 800, 600);
    }
}