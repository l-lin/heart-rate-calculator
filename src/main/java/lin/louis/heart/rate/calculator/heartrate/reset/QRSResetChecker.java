package lin.louis.heart.rate.calculator.heartrate.reset;

import java.util.List;

import lin.louis.heart.rate.calculator.heartbeat.HeartBeat;
import lin.louis.heart.rate.calculator.heartbeat.HeartQRS;


public class QRSResetChecker implements ResetChecker {

	@Override
	public boolean isReset(List<HeartBeat> heartBeatList) {
		return heartBeatList.stream()
				.anyMatch(heartBeat -> heartBeat.getQrs().isPresent() && HeartQRS.INVALID == heartBeat.getQrs().get());
	}
}
