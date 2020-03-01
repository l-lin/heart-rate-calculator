package lin.louis.heart.rate.calculator.heartrate.reset;

import java.util.Queue;

import lin.louis.heart.rate.calculator.heartbeat.HeartBeat;
import lin.louis.heart.rate.calculator.heartbeat.HeartBeatQRS;


public class QRSResetChecker implements ResetChecker {

	@Override
	public boolean isReset(Queue<HeartBeat> heartBeatList) {
		return heartBeatList.stream()
				.anyMatch(heartBeat -> heartBeat.getQrs().isEmpty() ||
						HeartBeatQRS.INVALID == heartBeat.getQrs().get());
	}
}
