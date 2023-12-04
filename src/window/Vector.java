package window;

public class Vector {
    private int x;
    private int y;
    private final int max_x;
    private final int max_y;
    private int angle;
    private final int mod;
    private final int width;
    private final int height;

    public Vector(int mx, int my, int mod, int width, int height) {
        max_x = mx;
        max_y = my;
        this.mod = mod;
        x = (int) (Math.random() * max_x * 0.7 + 1);
        y = (int) (Math.random() * max_y * 0.7 + 1);
        angle = (int) (Math.random() * 360 + 1);
        this.width = width;
        this.height = height;
    }

    public void nextPos() {
        int x0 = x;
        int y0 = y;
        y0 += (int) (Math.sin(Math.toRadians(angle)) * mod);
        x0 += (int) (Math.cos(Math.toRadians(angle)) * mod);
        if (x0 < 0 || x0 + width > max_x)
            angle = 180 - angle;
        else {
            if (y0 < 0 || y0 + height > max_y)
                angle = 360 - angle;
            else {
                x = x0;
                y = y0;
            }
        }
    }

    public int cx() {
        return x;
    }

    public int cy() {
        return y;
    }

}

