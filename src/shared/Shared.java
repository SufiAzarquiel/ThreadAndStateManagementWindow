package shared;

public class Shared {
    private int state = 0;
    /*
     sharedWindowNumber = 0;
     sharedWindowTitle = "";
    */

    public Shared() {
        super();
    }

    public boolean isMoving(int windowNumber) {
        return switch (state) {
            case 0 -> true;
            case 1 -> windowNumber != 1;
            case 2 -> windowNumber != 1 && windowNumber != 2;
            case 3 -> false;
            default -> throw new IllegalStateException("Unexpected value: " + state);
        };
    }

    public void updateState(int windowNumber) {
        switch (state) {
            case 0:
                if (windowNumber == 1) {
                    state = 1;
                }
                notifyAll();
                break;
            case 1:
                state = 2;
                if (windowNumber != 2) {
                    state = 0;
                    notifyAll();
                }
                break;
            case 2:
                state = 3;
                if (windowNumber != 3) {
                    state = 0;
                    notifyAll();
                }
                break;
            case 3:
                break;
        }
        System.out.println("state after click: " + state);
        System.out.println("Clicked window: " + windowNumber);
    }
}
