package lin.louis.heart.rate.calculator.heartbeat;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.lang.Nullable;


public class HeartBeat {

	public static final HeartBeat INVALID = new HeartBeat(null, null, null);

	private final LocalDateTime timestamp;

	private Integer hri;

	private HeartQRS qrs;

	public HeartBeat(@Nullable LocalDateTime timestamp, @Nullable Integer hri, @Nullable HeartQRS qrs) {
		this.timestamp = timestamp;
		this.hri = hri;
		this.qrs = qrs;
	}

	public Optional<LocalDateTime> getTimestamp() {
		return Optional.ofNullable(timestamp);
	}

	public Optional<Integer> getHri() {
		return Optional.ofNullable(hri);
	}

	public Optional<HeartQRS> getQrs() {
		return Optional.ofNullable(qrs);
	}

	public void flush() {
		hri = null;
		qrs = null;
	}
}
