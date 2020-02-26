package lin.louis.heart.rate.calculator.heartrate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import lin.louis.heart.rate.calculator.heartbeat.HeartBeat;
import lin.louis.heart.rate.calculator.heartbeat.HeartBeatConverter;


public class HeartRateGenerator {

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
			List<HeartBeat> heartBeatList = new ArrayList<>(nbHeartBeats);
			while ((line = r.readLine()) != null) {
				if (heartBeatList.size() == nbHeartBeats) {
					heartBeatList.remove(0);
				}
				var heartBeat = heartBeatConverter.apply(line);
				heartBeatList.add(heartBeat);
				var hearRate = heartRateFactory.create(heartBeatList);
				w.write(heartRateConverter.apply(hearRate));
				w.newLine();
			}
		}
	}
}
