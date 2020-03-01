package lin.louis.heart.rate.calculator.heartrate;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.lang.Nullable;


public class HeartRate {

	private final LocalDateTime timestamp;

	private final Double value;

	private boolean isReset;

	public static HeartRate nan(@Nullable LocalDateTime t) {
		return new HeartRate(t, Double.NaN);
	}

	public static HeartRate resetHeartRate(@Nullable LocalDateTime t) {
		var heartRate = new HeartRate(t, Double.NaN);
		heartRate.isReset = true;
		return heartRate;
	}

	public HeartRate(LocalDateTime timestamp, Double value) {
		this.timestamp = timestamp;
		this.value = value;
	}

	public Optional<LocalDateTime> getTimestamp() {
		return Optional.ofNullable(timestamp);
	}

	public Double getValue() {
		return value;
	}

	public boolean isReset() {
		return isReset;
	}
}
