package lin.louis.heart.rate.calculator.heartbeat;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.lang.Nullable;


public class HeartBeat {

	public static final HeartBeat INVALID = new HeartBeat(null, null, null);

	private final LocalDateTime timestamp;

	private Integer hri;

	private HeartBeatQRS qrs;

	public HeartBeat(@Nullable LocalDateTime timestamp, @Nullable Integer hri, @Nullable HeartBeatQRS qrs) {
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

	public Optional<HeartBeatQRS> getQrs() {
		return Optional.ofNullable(qrs);
	}
}
