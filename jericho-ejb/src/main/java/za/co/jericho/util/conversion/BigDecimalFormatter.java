package za.co.jericho.util.conversion;

import java.math.BigDecimal;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-04-11
 */
public interface BigDecimalFormatter {
    
    public String convertBigDecimalAsCurrency(BigDecimal value);
    
}
