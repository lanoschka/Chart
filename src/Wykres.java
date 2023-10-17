import java.awt.*;

public class Wykres {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Okno("wykres");
            }
        });
    }
}
