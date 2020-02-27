package lin.louis.heart.rate.calculator.heartbeat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Convert a heart beat string representation into a Java Object one
 */
public class HeartBeatConverter implements Function<String, HeartBeat> {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final String separator;

	public HeartBeatConverter(String separator) {
		this.separator = separator;
	}

	@Override
	public HeartBeat apply(String s) {
		if (null == s || "".equals(s.trim())) {
			logger.error("The input value must not be null or empty");
			return HeartBeat.INVALID;
		}
		var heartBeatValues = s.split(separator);
		if (heartBeatValues.length < 3) {
			logger.error("The input value does not contains enough parameters");
			return HeartBeat.INVALID;
		}

		LocalDateTime timestamp;
		int hri;
		try {
			timestamp = LocalDateTime.ofInstant(
					Instant.ofEpochMilli(Long.parseLong(heartBeatValues[0])),
					ZoneOffset.UTC
			);
			hri = Integer.parseInt(heartBeatValues[1]);
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			return HeartBeat.INVALID;
		}
		var qrs = HeartBeatQRS.from(heartBeatValues[2]);
		if (qrs == null) {
			logger.error("Invalid QRS '{}'", heartBeatValues[2]);
			return HeartBeat.INVALID;
		}
		return new HeartBeat(timestamp, hri, qrs);
	}
}
