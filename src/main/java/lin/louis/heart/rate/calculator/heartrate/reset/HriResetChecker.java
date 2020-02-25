package lin.louis.heart.rate.calculator.heartrate.reset;

import java.util.Arrays;

import lin.louis.heart.rate.calculator.heartbeat.HeartBeat;


public class HriResetChecker implements ResetChecker {

	private final int hriMin;

	private final int hriMax;

	public HriResetChecker(int hriMin, int hriMax) {
		this.hriMin = hriMin;
		this.hriMax = hriMax;
	}

	@Override
	public boolean isReset(HeartBeat... heartBeats) {
		return Arrays.stream(heartBeats)
				.anyMatch(heartBeat -> heartBeat.getHri() < hriMin || heartBeat.getHri() > hriMax);
	}
}
