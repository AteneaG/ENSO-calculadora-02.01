/**
 * @name        Arc Tangent (inverse tangent) — result in Degrees
 * @package     calculator.operators
 * @file        AtanOperator.java
 * @description Returns the arc tangent of a value, converted to degrees
 */

package calculator.operators;

public class AtanOperator implements UnaryOperator {
    @Override
    public Double execute(Double num) {
        return Math.toDegrees(Math.atan(num));
    }
}
