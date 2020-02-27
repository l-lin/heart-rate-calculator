package lin.louis.heart.rate.calculator.heartrate;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lin.louis.heart.rate.calculator.heartbeat.HeartBeat;
import lin.louis.heart.rate.calculator.heartrate.reset.ResetCheckerFacade;


/**
 * Create heart rate from given heart beats
 */
public class HeartRateFactory {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private final int nbHeartBeats;

	private final ResetCheckerFacade resetCheckerFacade;

	private final HeartRateValueComputor heartRateValueComputor;

	private final HeartRateValueConverter heartRateValueConverter;

	public HeartRateFactory(
			int nbHeartBeats,
			ResetCheckerFacade resetCheckerFacade,
			HeartRateValueComputor heartRateValueComputor,
			HeartRateValueConverter heartRateValueConverter
	) {
		this.nbHeartBeats = nbHeartBeats;
		this.resetCheckerFacade = resetCheckerFacade;
		this.heartRateValueComputor = heartRateValueComputor;
		this.heartRateValueConverter = heartRateValueConverter;
	}

	public HeartRate create(List<HeartBeat> heartBeatList) {
		if (heartBeatList.isEmpty()) {
			logger.debug("Cannot create HeartRate as there are no heart beats detected");
			return HeartRate.nan(null);
		}
		var lastHeartBeat = heartBeatList.get(heartBeatList.size() - 1);
		var t = lastHeartBeat.getTimestamp().orElse(null);
		if (!hasEnoughHeartBeats(heartBeatList)) {
			logger.debug("There are not enough heart beats ({}) to create a heart rate (need {} heart beats) at {}",
					heartBeatList.size(),
					nbHeartBeats, t);
			return HeartRate.nan(t);
		}
		if (resetCheckerFacade.isReset(heartBeatList)) {
			logger.debug("Reset at {}", t);
			return HeartRate.resetHeartRate(t);
		}

		var value = heartRateValueComputor.compute(heartBeatList.stream()
				.map(HeartBeat::getHri)
				.filter(Optional::isPresent)
				.mapToInt(Optional::get)
				.toArray());
		return new HeartRate(t, heartRateValueConverter.apply(value));
	}

	private boolean hasEnoughHeartBeats(List<HeartBeat> heartBeatList) {
		return nbHeartBeats <= heartBeatList.size();
	}
}
