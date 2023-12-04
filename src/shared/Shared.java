package shared;

public class Shared {
    private int state = 0;
    private final Timer timer;

    public Shared() {
        super();
        timer = new Timer();
    }

    public synchronized boolean isMoving(int windowNumber) {
        return switch (state) {
            case 0 -> true;
            case 3 -> windowNumber != 3; // stop window 3
            case 2 -> windowNumber != 3 && windowNumber != 2; // stop windows 3 and 2
            case 1 -> {
                boolean b = windowNumber != 3 && windowNumber != 2 && windowNumber != 1;// stop windows 3, 2 and 1
                if (timer.getValue() >= 10) {
                    state = 4;
                    b = false;
                }
                yield b;
            }
            case 4 -> false;
            default -> throw new IllegalStateException("Unexpected value: " + state);
        };
    }

    public synchronized void updateState(int windowNumber) {
        switch (state) {
            case 0 -> {
                if (windowNumber == 3) {
                    state = 3;
                }
            }
            case 3 -> {
                if (windowNumber == 2) {
                    state = 2;
                } else if (windowNumber != 3) {
                    state = 0;
                    // user missed the sequence, notify all threads to move windows again
                    notifyAll();
                }
            }
            case 2 -> {
                if (windowNumber == 1) {
                    state = 1;
                    // user completed the sequence, reset timer
                    timer.setValue(0);
                } else if (windowNumber != 2) {
                    state = 0;
                    notifyAll();
                }
            }
            case 1 -> {
                if (windowNumber != 1) {
                    state = 0;
                    // user wants to start again
                    // notify all threads to move windows again
                    notifyAll();
                }
            }
        }
        System.out.println("state after click: " + state);
        System.out.println("Clicked window: " + windowNumber);
    }

    public Timer getTimer() {
        return timer;
    }
}
