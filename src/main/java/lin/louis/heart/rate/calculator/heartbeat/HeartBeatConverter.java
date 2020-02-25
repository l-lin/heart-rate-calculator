package lin.louis.heart.rate.calculator.heartbeat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
			return HeartBeat.invalid();
		}
		var heartBeatValues = s.split(separator);
		if (heartBeatValues.length < 3) {
			logger.error("The input value does not contains enough parameters");
			return HeartBeat.invalid();
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
			return HeartBeat.invalid();
		}
		var qrs = HeartQRS.from(heartBeatValues[2]);
		return new HeartBeat(timestamp, hri, qrs);
	}
}