package com.company;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class Plot extends JPanel {
    int height, width, unit;
    String function;
    JPanel loading;
    JLabel wait;

    public Plot(int w, int h, int _unit) {
        unit = _unit;
        setPreferredSize(new Dimension(w, h));
        setLayout(null);
        setBackground(Color.BLACK);
        height = w;
        width = h;
        unit = _unit;
        loading = new JPanel();
        loading.setBounds(0, 0, w, h);
        loading.setBackground(Color.BLACK);
        loading.setLayout(new GridBagLayout());

        wait = new JLabel();
        wait.setForeground(Color.WHITE);
        wait.setFont(new Font("", Font.BOLD, 15));
        wait.setText("Enter a function and click the draw button");

        loading.add(wait);
        add(loading);
    }

    public void redraw(String formula, int scale) {
        unit = scale;
        function = formula;
        wait.setText("Calculating...");
        loading.setVisible(true);
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                repaint();
                return null;
            }

            protected void done() {
                loading.setVisible(false);
            }
        };
        worker.execute();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);

        List<Line2D> axes = new ArrayList<>();

        axes.add(new Line2D.Double(0, height / 2, width, height / 2));
        axes.add(new Line2D.Double(width / 2, 0, width / 2, height));
        axes.add(new Line2D.Double(width / 2, 0, width / 2 - 5, 5));
        axes.add(new Line2D.Double(width / 2, 0, width / 2 + 5, 5));
        axes.add(new Line2D.Double(width, height / 2, width - 5, height / 2 - 5));
        axes.add(new Line2D.Double(width, height / 2, width - 5, height / 2 + 5));

        final double zeroX = width / 2;
        final double zeroY = height / 2;

        for (double i = zeroX; i < width; i += unit) {
            axes.add(new Line2D.Double(i, zeroX + 2, i, zeroX - 2));
        }

        for (double i = zeroX; i > 0; i -= unit) {
            axes.add(new Line2D.Double(i, zeroX + 2, i, zeroX - 2));
        }

        for (double i = zeroY; i < height; i += unit) {
            axes.add(new Line2D.Double(zeroX + 2, i, zeroX - 2, i));
        }

        for (double i = zeroY; i > 0; i -= unit) {
            axes.add(new Line2D.Double(zeroX + 2, i, zeroX - 2, i));
        }

        for (int i = 0; i < axes.size(); i++) {
            g2d.draw((Line2D) axes.get(i));
        }

        if (function == null) return;

        g2d.setColor(Color.ORANGE);
        g2d.setStroke(new BasicStroke(1.2f));

        List<Line2D> plot = new ArrayList<>();
        double steps = width / 2 * unit;

        List<Double> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");

        for (double i = -steps; i < steps; i++) {
            double number = i / unit;
            x.add(number);
            try {
                engine.put("x", number);
                y.add(new Double(String.valueOf(engine.eval(function))));
            } catch (ScriptException e) {
            }
        }

        for (int i = 0; i < (x.size() - 1); i++) {
            double dx1 = x.get(i) * unit + width / 2;
            double dy1 = y.get(i) * unit + height / 2;
            dy1 = height - dy1;
            double dx2 = x.get(i + 1) * unit + width / 2;
            double dy2 = y.get(i + 1) * unit + height / 2;
            dy2 = height - dy2;
            plot.add(new Line2D.Double(dx1, dy1, dx2, dy2));
        }

        for (int i = 0; i < plot.size(); i++) {
            g2d.draw((Line2D) plot.get(i));
        }
    }
}