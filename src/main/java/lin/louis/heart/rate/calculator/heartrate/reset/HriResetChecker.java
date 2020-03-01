package lin.louis.heart.rate.calculator.heartrate.reset;

import java.util.Queue;

import lin.louis.heart.rate.calculator.heartbeat.HeartBeat;


public class HriResetChecker implements ResetChecker {

	private final int hriMin;

	private final int hriMax;

	public HriResetChecker(int hriMin, int hriMax) {
		this.hriMin = hriMin;
		this.hriMax = hriMax;
	}

	@Override
	public boolean isReset(Queue<HeartBeat> heartBeatList) {
		return heartBeatList.stream()
				.anyMatch(heartBeat -> heartBeat.getHri().isPresent() &&
						(heartBeat.getHri().get() < hriMin || heartBeat.getHri().get() > hriMax)
				);
	}
}
