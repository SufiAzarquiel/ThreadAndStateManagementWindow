package shared;

import window.Window;

import java.util.ArrayList;

public class Timer implements Runnable{
    private int value;
    private ArrayList<Window> windows;

    public Timer() {
        value =0;
        Thread hilo = new Thread(this);
        hilo.start();
    }

    @Override
    public void run() {
        while (true) {
            if (windows != null) {
                for (Window window : windows) {
                    window.setTitle("n: " + window.getWindowNumber() + " - " + value + "s");
                }
            }

            try {Thread.sleep(1000);} catch (InterruptedException e) {
                e.printStackTrace();
            }
            value +=1;
        }
    }

    public synchronized int getValue() {
        return value;
    }

    public synchronized void setValue(int value) {
        this.value = value;
    }

    public void setWindows(ArrayList<Window> windows) {
        this.windows = windows;
    }
}