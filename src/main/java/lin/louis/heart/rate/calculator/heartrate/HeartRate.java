package lin.louis.heart.rate.calculator.heartrate;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.lang.Nullable;


public class HeartRate {

	private static final String NAN_VALUE = "NaN";

	private final LocalDateTime timestamp;

	private final String value;

	public static HeartRate nan(@Nullable LocalDateTime t) {
		return new HeartRate(t, NAN_VALUE);
	}

	public HeartRate(LocalDateTime timestamp, String value) {
		this.timestamp = timestamp;
		this.value = value;
	}

	public Optional<LocalDateTime> getTimestamp() {
		return Optional.ofNullable(timestamp);
	}

	public String getValue() {
		return value;
	}
}
