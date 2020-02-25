package lin.louis.heart.rate.calculator.heartrate.reset;

import java.time.Duration;

import lin.louis.heart.rate.calculator.heartbeat.HeartBeat;


public class GapResetChecker implements ResetChecker {

	private final Duration gapDuration;

	public GapResetChecker(Duration gapDuration) {this.gapDuration = gapDuration;}

	@Override
	public boolean isReset(HeartBeat... heartBeats) {
		for (int i = 0; i < heartBeats.length - 1; i++) {
			var firstTimestamp = heartBeats[i].getTimestamp();
			var secondTimestamp = heartBeats[i + 1].getTimestamp();

			if (firstTimestamp.plus(gapDuration).isBefore(secondTimestamp)) {
				return true;
			}
		}
		return false;
	}
}
