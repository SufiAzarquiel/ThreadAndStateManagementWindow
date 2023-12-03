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
    private int state = 0;

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
        int countdown = 7;
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
            
            setTitle(
                    switch (state) {
                        case 0 -> "n: " + windowNumber;
                        case 1 -> String.valueOf(countdown);
                        case 2 -> "1";
                        default -> throw new IllegalStateException("Unexpected value: " + state);
                    }
            );

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
            updateState();
        }
    }

    private void updateState() {
        state = switch (state) {
            case 0 -> 1;
            case 1 -> 2;
            case 2 -> 0;
            default -> throw new IllegalStateException("Unexpected value: " + state);
        };
    }
}
