package com.edubrawlers.game.equations;

import java.util.ArrayList;
import java.util.List;

// In the EquationList class
public class EquationsSet {
    public static List<Equations> EQUATIONS_LIST = new ArrayList<>();

    static {
        EQUATIONS_LIST.add(new Equations("5^2 * (3 + 2) - 4^3 =", new String[] {"61", "90", "24", "53"}, 0));
        EQUATIONS_LIST.add(new Equations("4 * (2^3 + 1) - 3^2 =", new String[] {"45", "27", "32", "68"}, 1));
        EQUATIONS_LIST.add(new Equations("2^3 x (3 + 2^2) - 5 =", new String[] {"51", "23", "78", "45"}, 0));
        EQUATIONS_LIST.add(new Equations("7 x (2^3 + 2) - 4^2 =", new String[] {"94", "54", "40", "21"}, 2));
        EQUATIONS_LIST.add(new Equations("5 x (3^2 + 2) - 2^3 =", new String[] {"69", "56", "27", "57"}, 3));

        EQUATIONS_LIST.add(new Equations("f(x)=x^2+7x-1; evaluate f(5): ", new String[] {"39", "59", "67", "47"}, 1));
        EQUATIONS_LIST.add(new Equations("f(x)=-x^2-3x+7; evaluate f(6): ", new String[] {"-47", "-23", "47", "-93"}, 0));
        EQUATIONS_LIST.add(new Equations("f(x)=x^2-7x+10; find the roots: ", new String[] {"x = -2; x = -5", "x = 7; x = -2", "x = -2; x = 5", "x = 2; x = 5"}, 3));
        EQUATIONS_LIST.add(new Equations("(x+3)(x-8); Evaluate: ", new String[] {"x^2-6x+24", "x^2-11x+5", "x^2-5x-24", "x^2+5x+24"}, 2));
        EQUATIONS_LIST.add(new Equations("(x-5)(x-8); Evaluate: ", new String[] {"x^2+13x+40", "x^2-16x+13", "x^2-13x+40", "x^2+13x-40"}, 3));

        EQUATIONS_LIST.add(new Equations("Convert 3(pi) / 4 radians to degrees: ", new String[] {"135 degrees", "45 degrees", "90 degrees", "225 degrees"}, 0));
        EQUATIONS_LIST.add(new Equations("cos( 0 ); Evaluate: ", new String[] {"None", "-1", "0", "1"}, 3));
        EQUATIONS_LIST.add(new Equations("Convert 5(pi) / 6 radians to degrees: ", new String[] {"150 degree", "125 degree", "145 degree", "225 degree"}, 0));
        EQUATIONS_LIST.add(new Equations("sin^2(x) + cos^2(x)= ?", new String[] {"0", "1", "-1", "tan^2 (x)"}, 1));
        EQUATIONS_LIST.add(new Equations("2i^8; Evaluate: ", new String[] {"-4", "4", "2", "1"}, 2));

        EQUATIONS_LIST.add(new Equations("Derive y = 2 sin x: ", new String[] {"y' = cos (2x)", "y' = 4 sin (x)", "y' = -2 cos (x)", "y' = 2 cos (x)"}, 3));
        EQUATIONS_LIST.add(new Equations("Derive y = 4e^x: ", new String[] {"y' = 4e^x", "y' = e^x", "y' = 4x", "y' = -4e^x"}, 0));
        EQUATIONS_LIST.add(new Equations("Derive y = x^2 / 2: ", new String[] {"y' = 2x", "y' = x", "y' = -x", "y' = 4x"}, 1));
        EQUATIONS_LIST.add(new Equations("Derive y =  (2x+1) / 4: ", new String[] {"y' = 0", "y' = 2", "y' = 1", "y' = 1/2"}, 3));
        EQUATIONS_LIST.add(new Equations("Derive y = sin x /cos x: ", new String[] {"y = tan x", "y = sec^2 (x)", "y = sec x", "y = tan^2 (x)"}, 1));

    }
}