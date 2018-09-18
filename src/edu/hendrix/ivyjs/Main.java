package edu.hendrix.ivyjs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        try {
            FileIO.AssembleAndSave("/Users/ivy/Desktop/nand2tetris/projects/06/rect/Rect.asm");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
