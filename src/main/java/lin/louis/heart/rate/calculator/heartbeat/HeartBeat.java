package lin.louis.heart.rate.calculator.heartbeat;

import java.time.LocalDateTime;


public class HeartBeat {

	public static final HeartBeat INVALID = new HeartBeat(null, 0, HeartQRS.INVALID);

	private final LocalDateTime timestamp;

	private Integer hri;

	private HeartQRS qrs;

	public HeartBeat(LocalDateTime timestamp, Integer hri, HeartQRS qrs) {
		this.timestamp = timestamp;
		this.hri = hri;
		this.qrs = qrs;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public Integer getHri() {
		return hri;
	}

	public HeartQRS getQrs() {
		return qrs;
	}

	public void flush() {
		hri = 0;
		qrs = HeartQRS.INVALID;
	}
}
