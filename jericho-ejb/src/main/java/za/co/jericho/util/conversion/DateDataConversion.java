package za.co.jericho.util.conversion;

import java.text.SimpleDateFormat;
import java.util.Date;
import za.co.jericho.exception.DateConversionException;

/**
 *
 * @author Jaco Koekemoer
 * Date: 2015-07-20
 */
public class DateDataConversion implements DateConversion {

    @Override
    public String getStringDateFromDate(Date date, String format) {
        if (date == null) {
            throw new DateConversionException("Date is null");
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }
    
}
