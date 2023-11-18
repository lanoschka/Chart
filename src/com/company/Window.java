package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame implements ActionListener {
    JTextField field, scaleField;
    JButton drawButton;
    Plot plot;

    public Window(String chart) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(Color.GRAY);

        JPanel leftPanel = new JPanel();
        JPanel container = new JPanel();
        add(container);
        container.setLayout(null);
        container.setPreferredSize(new Dimension(150, 500));
        container.setBackground(Color.GRAY);

        leftPanel.setLayout(new GridLayout(5, 1));
        leftPanel.setBackground(Color.GRAY);
        leftPanel.setForeground(Color.ORANGE);
        leftPanel.setBounds(0, 0, 150, 130);

        container.add(leftPanel);

        JLabel label = new JLabel("Enter a function", SwingConstants.CENTER);
        field = new JTextField();
        field.setBackground(Color.darkGray);
        field.setForeground(Color.orange);

        scaleField = new JTextField();
        scaleField.setBackground(Color.darkGray);
        scaleField.setForeground(Color.orange);

        drawButton = new JButton("Draw");
        drawButton.setBackground(Color.BLACK);
        drawButton.setForeground(Color.ORANGE);
        drawButton.addActionListener(this);

        JLabel label2 = new JLabel("Enter scale", SwingConstants.CENTER);

        leftPanel.add(label);
        leftPanel.add(field);
        leftPanel.add(label2);
        leftPanel.add(scaleField);
        leftPanel.add(drawButton);

        plot = new Plot(500, 500, 40);
        add(plot);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == drawButton) {
            plot.redraw(field.getText(), Integer.parseInt(scaleField.getText()));
        }
    }
}