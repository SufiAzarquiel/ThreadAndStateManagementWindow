package window;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import shared.Shared;

public class Window extends JFrame implements Runnable, ActionListener {
    /**
     * sufiDev - November 2023
     */
    private final JButton btn;
    private final Shared shared;
    private final Vector v;
    private final long DELAY;
    private final int windowNumber;

    public Window(int i, Shared shared) {
        super();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int height = d.height / 5;
        int width = d.width / 5;
        setSize(width, height);
        String title = "n: " + i + " - " + 0 + "s";
        setTitle(title);
        setResizable(false);
        setVisible(false);

        this.shared = shared;
        windowNumber = i;

        btn = new JButton("Stop");
        add(btn);
        btn.addActionListener(this);

        v = new Vector(d.width, d.height, 2, width, height);
        setLocation(v.cx(), v.cy());
        setVisible(true);
        DELAY = 20;

        Thread thread = new Thread(this);
        thread.setName(title);
        thread.start();
    }

    public void run() {
        while (true) {
            synchronized (shared) {
                while (!shared.isMoving(windowNumber)) {
                    try {
                        shared.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }


            v.nextPos();
            this.setLocation(v.cx(), v.cy());



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
        synchronized (shared) {
            shared.updateState(windowNumber);
        }
    }

    public int getWindowNumber() {
        return windowNumber;
    }
}
