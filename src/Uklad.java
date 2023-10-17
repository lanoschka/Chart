import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Uklad extends JPanel {
    int wys, szer, jed;
    String f;
    JPanel loading;
    JLabel wait;
    int unit;
    public Uklad(int w, int h , int _unit) {
        unit=_unit;
        jed=unit;
        setPreferredSize(new Dimension(w, h));
        setLayout(null);
        setBackground(Color.BLACK);
        wys = w; szer = h;jed = unit;
        loading=new JPanel();
        loading.setBounds(0,0,w,h);
        loading.setBackground(Color.BLACK);
        loading.setLayout(new GridBagLayout());

        wait = new JLabel();
        wait.setForeground(Color.WHITE);
        wait.setFont(new Font("",Font.BOLD,15));
        wait.setText("wpisz wzór funkcji i kliknij klawisz rysuj");

        loading.add(wait);
        add(loading);
    }

    public void przerysuj(String wzor, int skala){
        jed=skala;
        f=wzor;
        wait.setText("obliczam...");
        loading.setVisible(true);
        SwingWorker<Void,Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                repaint();
                return null;
            }
            protected void done(){
                loading.setVisible(false);
            }
        };
        worker.execute();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d=(Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);

        List<Line2D> osie = new ArrayList<Line2D>();


        osie.add(new Line2D.Double(0,wys/2,szer,wys/2));
        osie.add(new Line2D.Double(szer/2,0,szer/2,wys));
        osie.add(new Line2D.Double(szer/2,0,szer/2-5,5));
        osie.add(new Line2D.Double(szer/2,0,szer/2+5,5));
        osie.add(new Line2D.Double(szer,wys/2,szer-5,wys/2-5));
        osie.add(new Line2D.Double(szer,wys/2,szer-5,wys/2+5));
        //jednostki na osiach
        final double zeroX = szer/2;
        final double zeroY = wys/2;
        for(double i=zeroX;i<szer;i+=jed){
            osie.add(new Line2D.Double(i,zeroX+2,i,zeroX-2));
        }
        for(double i=zeroX;i>0;i-=jed){
            osie.add(new Line2D.Double(i,zeroX+2,i,zeroX-2));
        }
        for(double i=zeroY;i<wys;i+=jed){
            osie.add(new Line2D.Double(zeroX+2,i,zeroX-2,i));
        }
        for(double i=zeroY;i>0;i-=jed){
            osie.add(new Line2D.Double(zeroX+2,i,zeroX-2,i));
        }
        //rysowanie układu i jednostek
        for(int i = 0; i<osie.size();i++) {
            g2d.draw((Line2D) osie.get(i));

        }

            if(f==null) return;
            g2d.setColor(Color.ORANGE);
            g2d.setStroke(new BasicStroke(1.2f));
            List<Line2D> wykres = new ArrayList<Line2D>();
            double steps = szer/2*jed;
            List<Double> x = new ArrayList<Double>();
            List<Double> y = new ArrayList<Double>();
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("js");
            for(double i=-steps;i<steps;i++){
                double liczba=i/jed;
                x.add(liczba);
                try{
                    engine.put("x",liczba);
                    y.add(new Double(String.valueOf(engine.eval(f))));
                }
                catch(ScriptException e){}
            }
            for(int i=0;i<(x.size()-1);i++){
                double dx1 = x.get(i)*jed+szer/2;
                double dy1 = y.get(i)*jed+wys/2;
                dy1 = wys - dy1;
                double dx2 = x.get(i+1)*jed+szer/2;
                double dy2 = y.get(i+1)*jed+wys/2;
                dy2 = wys - dy2;
                wykres.add(new Line2D.Double(dx1,dy1,dx2,dy2));
            }
            for(int i =0;i<wykres.size();i++){
                g2d.draw((Line2D)wykres.get(i));
            }


    }
}
