package lin.louis.heart.rate.calculator.heartrate.reset;

import lin.louis.heart.rate.calculator.heartbeat.HeartBeat;


public class TimestampResetChecker implements ResetChecker {

	@Override
	public boolean isReset(HeartBeat... heartBeats) {
		for (int i = 0; i < heartBeats.length - 1; i++) {
			if (heartBeats[i].getTimestamp().isAfter(heartBeats[i + 1].getTimestamp())) {
				return true;
			}
		}
		return false;
	}
}
