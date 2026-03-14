/**
 * @name        Arc Sine (inverse sine) — result in Degrees
 * @package     calculator.operators
 * @file        AsinOperator.java
 * @description Returns the arc sine of a value, converted to degrees
 */

package calculator.operators;

public class AsinOperator implements UnaryOperator {
    @Override
    public Double execute(Double num) {
        return Math.toDegrees(Math.asin(num));
    }
}
