/**
 * @name        Arc Cosine (inverse cosine) — result in Degrees
 * @package     calculator.operators
 * @file        AcosOperator.java
 * @description Returns the arc cosine of a value, converted to degrees
 */

package calculator.operators;

public class AcosOperator implements UnaryOperator {
    @Override
    public Double execute(Double num) {
        return Math.toDegrees(Math.acos(num));
    }
}
