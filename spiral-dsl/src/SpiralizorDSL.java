import java.util.HashMap;

import static java.lang.Math.*;

public class SpiralizorDSL {
    public static final int SCREEN_WIDTH = 100;
    public static final int SCREEN_HEIGHT = 100;

    public static void main(String[] args) {

        var screen = run(
                make("a", 20),
                right(90),
                forward(var("a")),
                right(90),
                make("r", div(var("a"), 2)),
                repeat(var("r"),
                        repeat(2,
                                forward(var("a")),
                                right(90)
                        ),
                        make("a", sub(var("a"), 2))
                )
        );
        System.out.print(screen);
    }

    private static Screen run(Statement... statements) {
        return run(block(statements));
    }

    private static Screen run(Statement statement) {
        var screen = new Screen(SCREEN_WIDTH, SCREEN_HEIGHT);
        var variables = new HashMap<String, Double>();

        statement.exec(screen, variables);
        return screen;
    }

    private static Statement block(Statement[] statements) {
        return new Block(statements);
    }

    private static Statement var(String name) {
        return new Variable(name);
    }

    private static Make make(String name, Statement value) {
        return new Make(name, value);
    }

    private static Make make(String name, int value) {
        return new Make(name, constant(value));
    }

    private static Right right(double angle) {
        return new Right(angle);
    }

    private static Statement forward(Statement distance) {
        return new Forward(distance);
    }

    private static Statement repeat(Statement amount, Statement... statements) {
        return new Repeat(amount, block(statements));
    }

    private static Statement repeat(double amount, Statement... statements) {
        return new Repeat(constant(amount), block(statements));
    }

    private static Statement constant(double value) {
        return new Constant(value);
    }

    private static Statement sub(Statement minuend, int subtrahend) {
        return new Sub(minuend, constant(subtrahend));
    }

    private static Statement div(Statement numerator, int denominator) {
        return new Div(numerator, constant(denominator));
    }

    private interface Statement {
        Double exec(Screen screen, HashMap<String, Double> variables);

    }

    private record Make(String name, Statement value) implements Statement {
        @Override
        public Double exec(Screen screen, HashMap<String, Double> variables) {
            variables.put(name, value.exec(screen, variables));
            return null;
        }

    }

    private record Right(double angle) implements Statement {
        @Override
        public Double exec(Screen screen, HashMap<String, Double> variables) {
            screen.turtle_rotation = screen.turtle_rotation - toIntExact(round(angle));
            return null;
        }

    }

    private record Forward(Statement distance) implements Statement {
        @Override
        public Double exec(Screen screen, HashMap<String, Double> variables) {
            var x0 = screen.turtle_x;
            var y0 = screen.turtle_y;
            for (int d = 0; d <= distance.exec(screen, variables); d++) {
                screen.turtle_x = x0 + toIntExact(round(cos(toRadians(screen.turtle_rotation)) * d));
                screen.turtle_y = y0 + toIntExact(round(sin(toRadians(screen.turtle_rotation)) * d));
                screen.point(screen.turtle_x, screen.turtle_y);
            }
            return null;
        }
    }

    private record Variable(String name) implements Statement {
        @Override
        public Double exec(Screen screen, HashMap<String, Double> variables) {
            return variables.get(name);
        }
    }

    private record Repeat(Statement amount, Statement statement) implements Statement {
        @Override
        public Double exec(Screen screen, HashMap<String, Double> variables) {
            for (int i = 0; i < amount.exec(screen, variables); i++) {
                statement.exec(screen, variables);
            }
            return null;
        }
    }

    private record Constant(double value) implements Statement {
        @Override
        public Double exec(Screen screen, HashMap<String, Double> variables) {
            return value;
        }
    }

    private record Div(Statement numerator, Statement denominator) implements Statement {
        @Override
        public Double exec(Screen screen, HashMap<String, Double> variables) {
            return numerator.exec(screen, variables) / denominator.exec(screen, variables);
        }
    }

    private record Sub(Statement minuend, Statement subtrahend) implements Statement {
        @Override
        public Double exec(Screen screen, HashMap<String, Double> variables) {
            return minuend.exec(screen, variables) - subtrahend.exec(screen, variables);
        }
    }

    private record Block(Statement[] statements) implements Statement {
        @Override
        public Double exec(Screen screen, HashMap<String, Double> variables) {
            for (var statement : statements) {
                statement.exec(screen, variables);
            }
            return null;
        }
    }

    private static class Screen {
        private final boolean[][] screen;
        private int width;
        private int height;
        int turtle_x;
        int turtle_y;

        private int turtle_rotation;

        Screen(int width, int height) {
            this.width = width;
            this.height = height;
            this.turtle_x = width / 2;
            this.turtle_y = height / 2;
            this.turtle_rotation = 90;
            this.screen = new boolean[width][height];
        }

        @Override
        public String toString() {
            String s = "";
            for (int yy = 0; yy < height; yy++) {
                for (int xx = 0; xx < width; xx++) {
                    s = screen[xx][height - yy - 1] ? s + "0" : s + " ";
                }
                s += "\n";
            }
            return s;
        }

        public void point(int x, int y) {
            screen[x][y] = true;
        }

    }
}