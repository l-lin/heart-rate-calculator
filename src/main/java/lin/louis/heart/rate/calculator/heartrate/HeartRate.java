package lin.louis.heart.rate.calculator.heartrate;

import java.time.LocalDateTime;


public class HeartRate {

	private static final String NAN_VALUE = "NaN";

	private LocalDateTime timestamp;

	private String value;

	public static HeartRate nan(LocalDateTime t) {
		return new HeartRate(t, NAN_VALUE);
	}

	public HeartRate(LocalDateTime timestamp, String value) {
		this.timestamp = timestamp;
		this.value = value;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
