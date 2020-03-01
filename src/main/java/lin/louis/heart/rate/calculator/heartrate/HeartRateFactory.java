package lin.louis.heart.rate.calculator.heartrate;

import java.util.Optional;
import java.util.Queue;

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

	public HeartRateFactory(
			int nbHeartBeats,
			ResetCheckerFacade resetCheckerFacade,
			HeartRateValueComputor heartRateValueComputor
	) {
		this.nbHeartBeats = nbHeartBeats;
		this.resetCheckerFacade = resetCheckerFacade;
		this.heartRateValueComputor = heartRateValueComputor;
	}

	public HeartRate create(Queue<HeartBeat> heartBeats) {
		if (heartBeats.isEmpty()) {
			logger.debug("Cannot create HeartRate as there are no heart beats detected");
			return HeartRate.nan(null);
		}
		var lastHeartBeat = getLastHeartBeat(heartBeats);
		var t = lastHeartBeat.getTimestamp().orElse(null);
		if (!hasEnoughHeartBeats(heartBeats)) {
			logger.debug("There are not enough heart beats ({}) to create a heart rate (need {} heart beats) at {}",
					heartBeats.size(),
					nbHeartBeats, t);
			return HeartRate.nan(t);
		}
		if (resetCheckerFacade.isReset(heartBeats)) {
			logger.debug("Reset at {}", t);
			return HeartRate.resetHeartRate(t);
		}

		var value = heartRateValueComputor.compute(heartBeats.stream()
				.map(HeartBeat::getHri)
				.filter(Optional::isPresent)
				.mapToInt(Optional::get)
				.toArray());
		return new HeartRate(t, value);
	}

	/**
	 * Not the best way... but since it's only queue of size 8, it should be ok, performance wise
	 * @param heartBeats the heart beats
	 * @return the last element of the queue
	 */
	private HeartBeat getLastHeartBeat(Queue<HeartBeat> heartBeats) {
		return heartBeats.stream().reduce((a, b) -> b).orElse(heartBeats.peek());
	}

	private boolean hasEnoughHeartBeats(Queue<HeartBeat> heartBeatList) {
		return nbHeartBeats <= heartBeatList.size();
	}
}
