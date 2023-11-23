package window;

public class Vector {
    private int x;
    private int y;
    private final int max_x;
    private final int max_y;
    private int angle; // en grados enteros
    private final int mod;

    public Vector(int mx, int my, int mod) {
        max_x = mx;
        max_y = my;
        this.mod = mod;
        x = max_x / 2;
        y = max_y / 2;
        angle = (int) (Math.random() * 360 + 1);
        System.out.println(angle);
    }

    public void nextPos() {
        int x0 = x;
        int y0 = y;
        y0 += (int) (Math.sin(Math.toRadians(angle)) * mod);
        x0 += (int) (Math.cos(Math.toRadians(angle)) * mod);
        if (x0 < 0 || x0 > max_x)
            angle = 180 - angle;
        else {
            if (y0 < 0 || y0 > max_y)
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

