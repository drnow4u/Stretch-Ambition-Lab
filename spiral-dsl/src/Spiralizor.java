public class Spiralizor {

    public static int[][] spiralize(int size) {
        var spirale = new int[size][size];
        char d = 'r';
        int x = 0, xmin = 0, xmax = size - 1;
        int y = 0, ymin = 2, ymax = size - 1;

        do {
            spirale[y][x] = 1;
            switch (d) {
                case 'r':
                    if (x < xmax) x++;
                    else {
                        if ((x == xmax + 1) || (y == ymax)) {
                            return spirale;
                        } else {
                            d = 'd';
                            xmax -= 2;
                        }
                    }
                    break;
                case 'l':
                    if (x > xmin) x--;
                    else {
                        if ((x == xmin - 1)||(y==ymin)) {
                            return spirale;
                        } else {
                            d = 'u';
                            xmin += 2;
                        }
                    }
                    break;
                case 'u':
                    if (y > ymin) y--;
                    else {
                        if (y == ymax + 1) {
                            return spirale;
                        } else {
                            d = 'r';
                            ymin += 2;
                        }
                    }
                    break;
                case 'd':
                    if (y < ymax)
                        y++;
                    else {
                        if (y == ymin - 1) {
                            return spirale;
                        } else {
                            d = 'l';
                            ymax -= 2;
                        }
                    }
                    break;
            }
        } while (true);

    }

}
