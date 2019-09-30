package innerClasses;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.Date;

public class TalkingClock {
    public int interval;
    public boolean beep;

    public TalkingClock(int interval, boolean beep) {
        this.interval = interval;
        this.beep = beep;
    }

    public void start() {
        ActionListener listener = new TimePrinter();
        Timer t = new Timer(this.interval, listener);
        t.start();
    }

    public class TimePrinter implements ActionListener {
//        static String motto;
//        public TalkingClock instance;
//        public TimePrinter(TalkingClock instance, String motto) {
//            this.instance = instance;
//            TimePrinter.motto = motto;
//        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("At the stone, the time is " + new Date());
            if (beep) Toolkit.getDefaultToolkit().beep();
        }
    }
}
