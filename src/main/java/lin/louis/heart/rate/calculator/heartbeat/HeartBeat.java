package lin.louis.heart.rate.calculator.heartbeat;

import java.time.LocalDateTime;


public class HeartBeat {
	private LocalDateTime timestamp;
	private int hri;
	private HeartQRS qrs;

	public static HeartBeat invalid() {
		return new HeartBeat(LocalDateTime.now(), 0, HeartQRS.INVALID);
	}

	public HeartBeat(LocalDateTime timestamp, int hri, HeartQRS qrs) {
		this.timestamp = timestamp;
		this.hri = hri;
		this.qrs = qrs;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public int getHri() {
		return hri;
	}

	public void setHri(int hri) {
		this.hri = hri;
	}

	public HeartQRS getQrs() {
		return qrs;
	}

	public void setQrs(HeartQRS qrs) {
		this.qrs = qrs;
	}
}
