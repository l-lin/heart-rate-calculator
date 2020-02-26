package lin.louis.heart.rate.calculator.heartrate;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.function.DoubleFunction;


public class HeartRateValueConverter implements DoubleFunction<String> {

	@Override
	public String apply(double value) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
		numberFormat.setMinimumFractionDigits(1);
		numberFormat.setMaximumFractionDigits(1);
		return numberFormat.format(value);
	}
}
