package MathParts;


import java.util.Objects;

public class Vector2D {

    private int x;
    private int y;
    private static final double EPSILON = 1e-9;

    /**
     * Creates a vector with the given X and Y components.
     *
     * @param x the X component
     * @param y the Y component
     */
    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the X component of this vector.
     *
     * @return the X component
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the X component of this vector.
     *
     * @param x the new X component
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns the Y component of this vector.
     *
     * @return the Y component
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the Y component of this vector.
     *
     * @param y the new Y component
     */
    public void setY(int y) {
        this.y = y;
    }

    
    public Vector2D copy() {
        return new Vector2D(x, y);
    }

    /**
     * Negates both components of this vector in place.
     */
    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
    }
    



    private static boolean intEquals(int d1, int d2) {
        return Math.abs(d1 - d2) < EPSILON;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Vector2D other = (Vector2D) obj;
        return intEquals(this.x, other.x) && (intEquals(this.y, other.y));
    }

    @Override
    public int hashCode() {
        return Objects.hash(Math.round(x / EPSILON), Math.round(y / EPSILON));
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }


    /**
     * Calculates the element-by-element sum of two {@code Vector2D}s.
     *
     * @param v1 the first vector
     * @param v2 the second vector
     * @return the sum of the two input vectors
     */
    public static Vector2D add(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.getX() + v2.getX(), v1.getY() + v2.getY());
    }

    /**
     * Calculates the scalar sum of a {@code Vector2D} and a value. That is, a
     * vector whose components are each the result of that original component plus
     * the input value.
     *
     * @param v1 the first vector
     * @param v  the scalar value
     * @return the scalar sum
     */
    public static Vector2D add(Vector2D v1, int v) {
        return new Vector2D(v1.getX() + v, v1.getY() + v);
    }

    /**
     * Calculates the element-by-element difference of two {@code Vector2D}s.
     *
     * @param v1 the first vector
     * @param v2 the second vector
     * @return the difference of the two input vectors
     */
    public static Vector2D sub(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.getX() - v2.getX(), v1.getY() - v2.getY());
    }

    /**
     * Calculates the scalar difference of a {@code Vector2D} and a value. That is,
     * a vector whose components are each the result of that original component
     * minus the input value.
     *
     * @param v1 the first vector
     * @param v  the scalar value
     * @return the scalar difference
     */
    public static Vector2D sub(Vector2D v1, int v) {
        return new Vector2D(v1.getX() - v, v1.getY() - v);
    }

    /**
     * Calculates the element-by-element product of two {@code Vector2D}s.
     *
     * @param v1 the first vector
     * @param v2 the second vector
     * @return the product of the two input vectors
     */
    public static Vector2D mul(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.getX() * v2.getX(), v1.getY() * v2.getY());
    }

    /**
     * Calculates the scalar product of a {@code Vector2D} and a value. That is, a
     * vector whose components are each the result of that original component times
     * the input value.
     *
     * @param v1 the first vector
     * @param v  the scalar value
     * @return the scalar product
     */
    public static Vector2D mul(Vector2D v1, int v) {
        return new Vector2D(v1.getX() * v, v1.getY() * v);
    }

    /**
     * Calculates the element-by-element quotient of two {@code Vector2D}s.
     *
     * @param v1 the first vector
     * @param v2 the second vector
     * @return the quotient of the two input vectors
     */
    public static Vector2D div(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.getX() / v2.getX(), v1.getY() / v2.getY());
    }

    /**
     * Calculates the scalar quotient of a {@code Vector2D} and a value. That is, a
     * vector who's components are each the result of that original component
     * divided by the input value.
     *
     * @param v1 the first vector
     * @param v  the scalar value
     * @return the scalar quotient
     */
    public static Vector2D div(Vector2D v1, int v) {
        return new Vector2D(v1.getX() / v, v1.getY() / v);
    }

    public static boolean shareCommon(Vector2D v1, Vector2D v2){
        int x1 = v1.getX();
        int y1 = v1.getY();

        int x2 = v2.getX();
        int y2=  v2.getY();

        if (Objects.equals(x1,y1) || Objects.equals(x1,x2) || Objects.equals(x1,y2)){
            return true;
        } else if (Objects.equals(y1,x2) || Objects.equals(y1,y2)) {
            return true;
        } else if (Objects.equals(x2,y2)) {
            return true;
        } else {
            return false;
        }

    }

}
