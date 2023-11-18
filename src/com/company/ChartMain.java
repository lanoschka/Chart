package com.company;

import java.awt.EventQueue;

public class ChartMain {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Window("chart");
            }
        });
    }
}