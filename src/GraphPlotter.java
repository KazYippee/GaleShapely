import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

// YIPPEE!!! this is the graph plotter for Task C :3
// it reads timing data and draws a cute line graph 
public class GraphPlotter extends JPanel {
    private int[] ns;
    private double[] matcherTimes;
    private double[] verifierTimes;
    
    public GraphPlotter(int[] ns, double[] matcherTimes, double[] verifierTimes) {
        this.ns = ns;
        this.matcherTimes = matcherTimes;
        this.verifierTimes = verifierTimes;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int w = getWidth();
        int h = getHeight();
        int pad = 50;
        int nPoints = ns.length;
        
        // find max time for scaling :3
        double maxTime = 0;
        for (double t : matcherTimes) maxTime = Math.max(maxTime, t);
        for (double t : verifierTimes) maxTime = Math.max(maxTime, t);
        
        // axes :3
        g2.drawLine(pad, h - pad, w - pad, h - pad); // x
        g2.drawLine(pad, h - pad, pad, pad); // y
        g2.drawString("n", w/2, h-10);
        g2.drawString("time (ms)", 5, h/2);
        
        // plot verifier line first so matcher is on top :3
        g2.setColor(new Color(0, 128, 255)); 
        for (int i = 0; i < nPoints-1; i++) {
            int x1 = pad + (int)((w-2*pad) * i/(nPoints-1));
            int y1 = h - pad - (int)((h-2*pad) * verifierTimes[i]/maxTime);
            int x2 = pad + (int)((w-2*pad) * (i+1)/(nPoints-1));
            int y2 = h - pad - (int)((h-2*pad) * verifierTimes[i+1]/maxTime);
            g2.drawLine(x1, y1, x2, y2);
            g2.fillOval(x1-3, y1-3, 6, 6);
        }
        g2.fillOval(pad + (w-2*pad)*(nPoints-1)/(nPoints-1)-3, h - pad - (int)((h-2*pad)*verifierTimes[nPoints-1]/maxTime)-3, 6, 6);
        // plot matcher line on top :3
        g2.setColor(new Color(220, 20, 60)); // crimson for matcher
        for (int i = 0; i < nPoints-1; i++) {
            int x1 = pad + (int)((w-2*pad) * i/(nPoints-1));
            int y1 = h - pad - (int)((h-2*pad) * matcherTimes[i]/maxTime);
            int x2 = pad + (int)((w-2*pad) * (i+1)/(nPoints-1));
            int y2 = h - pad - (int)((h-2*pad) * matcherTimes[i+1]/maxTime);
            g2.drawLine(x1, y1, x2, y2);
            g2.fillOval(x1-3, y1-3, 6, 6);
        }
        g2.fillOval(pad + (w-2*pad)*(nPoints-1)/(nPoints-1)-3, h - pad - (int)((h-2*pad)*matcherTimes[nPoints-1]/maxTime)-3, 6, 6);
        // draw a legend box :3
        int legendX = w-170, legendY = pad+10;
        g2.setColor(Color.WHITE);
        g2.fillRect(legendX-10, legendY-10, 140, 50);
        g2.setColor(Color.BLACK);
        g2.drawRect(legendX-10, legendY-10, 140, 50);
        g2.setColor(new Color(220, 20, 60));
        g2.fillRect(legendX, legendY, 20, 10);
        g2.setColor(Color.BLACK);
        g2.drawString("Matcher", legendX+30, legendY+10);
        g2.setColor(new Color(0, 128, 255));
        g2.fillRect(legendX, legendY+20, 20, 10);
        g2.setColor(Color.BLACK);
        g2.drawString("Verifier", legendX+30, legendY+30);
        
        // draw n labels :3
        g2.setColor(Color.BLACK);
        for (int i = 0; i < nPoints; i++) {
            int x = pad + (int)((w-2*pad) * i/(nPoints-1));
            g2.drawString(Integer.toString(ns[i]), x-5, h-pad+20);
        }
        // draw y labels (just a few)
        for (int i = 0; i <= 5; i++) {
            int y = h - pad - (int)((h-2*pad) * i/5.0);
            double t = maxTime * i/5.0;
            g2.drawString(String.format("%.1f", t), 10, y+5);
        }
    }
    
    public static void main(String[] args) throws Exception {
        // hi! please put your timing data in data/timing.csv :3
        // format: n,matcherTimeMs,verifierTimeMs
        java.util.List<Integer> ns = new java.util.ArrayList<>();
        java.util.List<Double> matcher = new java.util.ArrayList<>();
        java.util.List<Double> verifier = new java.util.ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("data/timing.csv"));
        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty() || line.startsWith("n")) continue;
            String[] parts = line.split(",");
            ns.add(Integer.parseInt(parts[0]));
            matcher.add(Double.parseDouble(parts[1]));
            verifier.add(Double.parseDouble(parts[2]));
        }
        br.close();
        int[] nArr = ns.stream().mapToInt(i->i).toArray();
        double[] mArr = matcher.stream().mapToDouble(d->d).toArray();
        double[] vArr = verifier.stream().mapToDouble(d->d).toArray();
        JFrame frame = new JFrame("Gale-Shapley Timing Graph :3");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.add(new GraphPlotter(nArr, mArr, vArr));
        frame.setVisible(true);
    }
}
