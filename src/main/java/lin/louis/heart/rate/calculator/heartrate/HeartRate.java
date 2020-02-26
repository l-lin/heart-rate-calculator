package lin.louis.heart.rate.calculator.heartrate;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.lang.Nullable;


public class HeartRate {

	private static final String NAN_VALUE = "NaN";

	private final LocalDateTime timestamp;

	private final String value;

	private boolean isReset;

	public static HeartRate nan(@Nullable LocalDateTime t) {
		return new HeartRate(t, NAN_VALUE);
	}

	public static HeartRate resetHeartRate(@Nullable LocalDateTime t) {
		var heartRate = new HeartRate(t, NAN_VALUE);
		heartRate.isReset = true;
		return heartRate;
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

	public boolean isReset() {
		return isReset;
	}
}
