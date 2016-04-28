package za.co.jericho.util.conversion;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2016-04-11
 */
public class BigDecimalDataFormatter implements BigDecimalFormatter {

    @Override
    public String convertBigDecimalAsCurrency(BigDecimal value) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return decimalFormat.format(value);
    }
    
}
