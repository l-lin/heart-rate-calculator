package lin.louis.heart.rate.calculator.heartrate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Queue;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lin.louis.heart.rate.calculator.heartbeat.HeartBeat;
import lin.louis.heart.rate.calculator.heartbeat.HeartBeatConverter;


/**
 * Reads the heart beats from InputStream, generate and write the heart rates in OutputStream
 */
public class HeartRateGenerator {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final int nbHeartBeats;

	private final HeartBeatConverter heartBeatConverter;

	private final HeartRateConverter heartRateConverter;

	private final HeartRateFactory heartRateFactory;

	public HeartRateGenerator(
			int nbHeartBeats,
			HeartBeatConverter heartBeatConverter,
			HeartRateConverter heartRateConverter,
			HeartRateFactory heartRateFactory
	) {
		this.nbHeartBeats = nbHeartBeats;
		this.heartBeatConverter = heartBeatConverter;
		this.heartRateConverter = heartRateConverter;
		this.heartRateFactory = heartRateFactory;
	}

	public void generate(InputStream in, OutputStream out) throws IOException {
		try (
				var r = new BufferedReader(new InputStreamReader(in));
				var w = new BufferedWriter(new OutputStreamWriter(out))
		) {
			String line;
			Queue<HeartBeat> heartBeats = new CircularFifoQueue<>(nbHeartBeats);
			var lineNb = 1;
			while ((line = r.readLine()) != null) {
				logger.debug("Reading line {}: {}", lineNb++, line);
				var heartBeat = heartBeatConverter.apply(line);
				heartBeats.add(heartBeat);

				var heartRate = heartRateFactory.create(heartBeats);
				String heartRateValue = heartRateConverter.apply(heartRate);

				writeHeartRate(w, heartRateValue);

				flushIfReset(heartBeats, heartRate);
			}
		}
	}

	private void writeHeartRate(BufferedWriter w, String heartRateValue) throws IOException {
		if (!heartRateValue.isBlank()) {
			w.write(heartRateValue);
			w.newLine();
		}
	}

	private void flushIfReset(Queue<HeartBeat> heartBeats, HeartRate heartRate) {
		if (heartRate.isReset()) {
			heartBeats.clear();
		}
	}

}
