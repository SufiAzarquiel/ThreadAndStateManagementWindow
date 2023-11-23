package window;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;

import javax.swing.JButton;
import javax.swing.JFrame;

import shared.Shared;

public class Window extends JFrame implements Runnable, ActionListener {
    /**
     * sufiDev - November 2023
     */
    private final JButton btn;
    private final Shared flag;
    private final Vector v;
    private final long DELAY;
    private final int windowNumber;

    public Window(int i, Shared flag) {
        super();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(d.width / 5, d.height / 5);
        String title = "n: " + i;
        setTitle(title);
        setResizable(false);
        setVisible(false);

        this.flag = flag;
        windowNumber = i;

        btn = new JButton("Stop");
        add(btn);
        btn.addActionListener(this);

        v = new Vector(d.width, d.height, 2);
        DELAY = 20;

        Thread thread = new Thread(this);
        thread.setName(title);
        thread.start();
    }

    public void run() {
        while (true) {
            synchronized (flag) {
                while (!flag.isMoving(windowNumber)) {
                    try {
                        flag.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            
            setTitle(flag.title(windowNumber));

            v.nextPos();
            this.setLocation(v.cx(), v.cy());

            if (!isVisible())
                setVisible(true);

            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn) {
            handleButton();
        }
    }

    private void handleButton() {
        synchronized (flag) {
            flag.updateState(windowNumber);
        }
    }
}
