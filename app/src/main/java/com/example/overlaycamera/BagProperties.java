package com.example.overlaycamera;

import java.util.ArrayList;
import java.util.List;

public class BagProperties {
    public String side1;
    public String side2;
    public String side3;
    public List<String> sides = new ArrayList<>();

    public BagProperties(String side1, String side2, String side3) {
        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;
        sides.add(side1);
        sides.add(side2);
        sides.add(side3);
    }
}
