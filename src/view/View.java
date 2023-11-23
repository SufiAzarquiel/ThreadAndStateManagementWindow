package view;

import shared.Shared;
import window.Window;

public class View {

    public static void main(String[] args) {
        int windowAmount = 4;
        Shared flag = new Shared();
        for (int i = 1; i <= windowAmount - 1; i++) {
            new Window(i, flag);
        }

    }

}
