package lin.louis.heart.rate.calculator.heartrate;

import java.time.ZoneOffset;
import java.util.function.Function;


public class HeartRateConverter implements Function<HeartRate, String> {

	private final String separator;

	public HeartRateConverter(String separator) {this.separator = separator;}

	@Override
	public String apply(HeartRate heartRate) {
		if (heartRate == null) {
			return "";
		}
		return String.format("%s%s%s",
				heartRate.getTimestamp().toInstant(ZoneOffset.UTC).toEpochMilli(),
				separator,
				heartRate.getValue());
	}
}
