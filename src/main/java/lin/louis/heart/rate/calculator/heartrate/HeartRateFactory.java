package lin.louis.heart.rate.calculator.heartrate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lin.louis.heart.rate.calculator.heartbeat.HeartBeat;
import lin.louis.heart.rate.calculator.heartrate.reset.ResetCheckerFacade;


public class HeartRateFactory {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private final int nbHeartBeats;

	private final ResetCheckerFacade resetCheckerFacade;

	private final HeartRateComputor heartRateComputor;

	private final HeartRateValueConverter heartRateValueConverter;

	public HeartRateFactory(
			int nbHeartBeats,
			ResetCheckerFacade resetCheckerFacade,
			HeartRateComputor heartRateComputor,
			HeartRateValueConverter heartRateValueConverter
	) {
		this.nbHeartBeats = nbHeartBeats;
		this.resetCheckerFacade = resetCheckerFacade;
		this.heartRateComputor = heartRateComputor;
		this.heartRateValueConverter = heartRateValueConverter;
	}

	public HeartRate create(List<HeartBeat> heartBeatList) {
		if (heartBeatList.isEmpty()) {
			return HeartRate.nan(null);
		}
		var t = heartBeatList.get(heartBeatList.size() - 1);
		if (!hasEnoughHeartBeats(heartBeatList)) {
			return HeartRate.nan(t.getTimestamp().orElse(null));
		}
		if (resetCheckerFacade.isReset(heartBeatList)) {
			logger.debug("Reset at {}", t.getTimestamp().orElse(null));
			heartBeatList.forEach(HeartBeat::flush);
			return HeartRate.nan(t.getTimestamp().orElse(null));
		}

		var value = heartRateComputor.compute(heartBeatList.stream()
				.map(HeartBeat::getHri)
				.filter(Optional::isPresent)
				.mapToInt(Optional::get)
				.toArray());
		return new HeartRate(t.getTimestamp().orElse(null), heartRateValueConverter.apply(value));
	}

	private boolean hasEnoughHeartBeats(List<HeartBeat> heartBeatList) {
		return nbHeartBeats <= heartBeatList.size();
	}
}
