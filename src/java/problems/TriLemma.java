package problems;

import datastructures.Coordinate;
import utilities.Kattio;

public class TriLemma {
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);
        int numTriangles = io.getInt();
        for (int i = 1; i <= numTriangles; i++) {
            io.print("Case #" + i + ": ");
            Triangle t;
            try {
                t = new Triangle(io.getInt(), io.getInt(), io.getInt(), io.getInt(), io.getInt(), io.getInt());
            } catch (IllegalArgumentException e) {
                io.println("not a triangle");
                continue;
            }

            if (t.isIsosceles()) {
                io.print("isosceles ");
            } else {
                io.print("scalene ");
            }

            if (t.isRight()) {
                io.print("right ");
            } else if (t.isObtuse()) {
                io.print("obtuse ");
            } else {
                io.print("acute ");
            }
            io.println("triangle");
        }
        io.close();
    }

    public static boolean equal(double a, double b) {
        return Math.abs(a - b) < 0.0000001;
    }

    public static class Triangle {
        Coordinate<Integer> one;
        Coordinate<Integer> two;
        Coordinate<Integer> three;

        public Triangle(int oneX, int oneY, int twoX, int twoY, int threeX, int threeY) {
            one = new Coordinate<>(oneX, oneY);
            two = new Coordinate<>(twoX, twoY);
            three = new Coordinate<>(threeX, threeY);
            if (equal(area(), 0d))
                throw new IllegalArgumentException("Not a valid triangle");
        }

        private double area() {
            int xy = one.x * two.y + two.x * three.y + three.x * one.y;
            int yx = one.y * two.x + two.y * three.x + three.y * one.x;
            return Math.abs(xy - yx) / 2d;
        }

        private double angle(Coordinate<Integer> main, Coordinate<Integer> n1, Coordinate<Integer> n2) {
            double l1 = sideLength(main, n1);
            double l2 = sideLength(main, n2);
            double l3 = sideLength(n1, n2);
            double cosAngle = (l1 * l1 + l2 * l2 - l3 * l3) / (2 * l1 * l2);
            return Math.acos(cosAngle);
        }

        private double sideLength(Coordinate<Integer> a, Coordinate<Integer> b) {
            return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
        }

        public double angleOne() {
            return angle(one, two, three);
        }

        public double angleTwo() {
            return angle(two, one, three);
        }

        public double angleThree() {
            return angle(three, two, one);
        }

        public boolean isRight() {
            return equal(angleOne(), Math.PI / 2) || equal(angleTwo(), Math.PI / 2) || equal(angleThree(), Math.PI / 2);
        }

        public boolean isObtuse() {
            return !isRight() && (angleOne() > Math.PI / 2 || angleTwo() > Math.PI / 2 || angleThree() > Math.PI / 2);
        }

        public boolean isIsosceles() {
            double s1 = sideLength(one, two);
            double s2 = sideLength(two, three);
            double s3 = sideLength(three, one);
            return equal(s1, s2) || equal(s1, s3) || equal(s2, s3);
        }
    }
}
