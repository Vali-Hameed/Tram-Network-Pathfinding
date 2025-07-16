import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BasicStroke;
public class createGraph extends JPanel {
    private LinkedHashMap<String, String> stations_WithLine;
    public createGraph(LinkedHashMap<String,String> stations){
        this.stations_WithLine=stations;
        setPreferredSize(new Dimension(stations.size()*100, 200));
        // stores the linked hashmap wiht stations and line colour and sets the prferred size to the size of the line graphic
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        int count=1;
        // initialises the graphics object and count for the alternating station names
        int panelHeight = getHeight();
        //stores the height of the panel
        List<String> stationList = new ArrayList<>(stations_WithLine.keySet());
        // stores all of the keys as a list
        int y = panelHeight / 2;
        // Calculate the starting y-position in the centre
        int x = 50;
        // sets x coordinate of first station
        String prevStation=null;
        // sets prev station to null as only draws line of their is a stationb before it
        for (int i = 0; i < stationList.size(); i++) {
            // iterate over stations
            String station = stationList.get(i);
            // stores the station name at i
            int stationWidth =100; 
            // sets fixed width for the stations
            if (prevStation != null) {
                g2D.setStroke(new BasicStroke(4)); 
                // Line thickness
                g2D.drawLine(x - stationWidth+7, y, x, y); 
                // If it's not the first station, draw the line from the previous station to the current one
            }
            g2D.setColor(Color.BLACK);
            g2D.fillOval(x - 5, y - 5, 10, 10);  
            // Draw a small circle to represent the station
            if (count%2==0) {
                g2D.drawString(station, x-(g2D.getFontMetrics().stringWidth(station)/2), y +25);
                
            }else{
                g2D.drawString(station, x-(g2D.getFontMetrics().stringWidth(station)/2), y - 15);
            }
            count++;
            // draws the station name above or below alternating 
            if (i < stationList.size() - 1) {
                // Get the color of the next station
                String nextStation = stationList.get(i + 1);
                String nextLineColour = stations_WithLine.get(nextStation);
                Color lineColor = getColorFromLine(nextLineColour);
                // Set the color for the line
                g2D.setColor(lineColor);
                g2D.setStroke(new BasicStroke(4));
            }
            // Update the x position for the next station
            x += stationWidth;
            prevStation=station; 
            // stores the current station as prev for next cycle
        }
    }
    private Color getColorFromLine(String line) {
        return switch (line.toLowerCase()) {
            case "green" -> Color.GREEN;
            case "lightblue" -> new Color(126,221,255);
            case "yellow" -> Color.YELLOW;
            case "red" -> Color.RED;
            case "purple"->new Color(102, 29, 220);
            case "pink" -> Color.PINK;
            case "dark blue" -> new Color(2, 62, 121);
            default -> Color.GRAY;
        };
        // swicth case for getting the colour of the line
    }
}
