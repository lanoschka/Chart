
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Okno extends JFrame implements ActionListener {
    JTextField pole,pole2;
    JButton rysuj;
    Uklad uklad;
    public Okno(String wykres) {

        setDefaultCloseOperation(3);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        this.setBackground(Color.GRAY);

        JPanel lewy=new JPanel();
        JPanel kontener = new JPanel();
        add(kontener);
        kontener.setLayout(null);
        kontener.setPreferredSize(new Dimension(150,500));
        kontener.setBackground(Color.GRAY);

        lewy.setLayout(new GridLayout(5,1));
        lewy.setBackground(Color.GRAY);
        lewy.setForeground(Color.ORANGE);
        lewy.setBounds(0,0,150,130);

        kontener.add(lewy);
        JLabel etykieta = new JLabel("wprowadź funkcje", SwingConstants.CENTER);
        pole = new JTextField();
        pole.setBackground(Color.darkGray);
        pole.setForeground(Color.orange);
        pole2 = new JTextField();
        pole2.setBackground(Color.darkGray);
        pole2.setForeground(Color.orange);
        rysuj = new JButton("rysuj");
        rysuj.setBackground(Color.BLACK);
        rysuj.setForeground(Color.ORANGE);
        rysuj.addActionListener(this);
        JLabel etykieta2 = new JLabel("podaj skalę", SwingConstants.CENTER);

        //  JPanel bot = new JPanel();
        //  bot.setBackground(Color.GRAY);
        //JPanel top = new JPanel();
        //   lewy.add(top);
        lewy.add(etykieta);
        lewy.add(pole);
        lewy.add(etykieta2);
        lewy.add(pole2);
        lewy.add(rysuj);
       // lewy.add(bot);


        uklad = new Uklad(500,500,40);
        add(uklad);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }
    public void actionPerformed(ActionEvent e) {
        Object zrodlo = e.getSource();
        if(zrodlo==rysuj) uklad.przerysuj(pole.getText(), Integer.parseInt(pole2.getText()));
    }
}
