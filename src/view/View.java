package view;

import shared.Shared;
import window.Window;

import java.util.ArrayList;

public class View {

    public static void main(String[] args) {
        int windowAmount = 5;
        Shared flag = new Shared();
        ArrayList<Window> windows = new ArrayList<>();
        for (int i = 1; i <= windowAmount; i++) {
            windows.add(new Window(i, flag));
        }
        flag.getTimer().setWindows(windows);
    }

}
