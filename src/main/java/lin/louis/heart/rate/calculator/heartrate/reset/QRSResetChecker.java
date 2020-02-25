package lin.louis.heart.rate.calculator.heartrate.reset;

import java.util.Arrays;

import lin.louis.heart.rate.calculator.heartbeat.HeartBeat;
import lin.louis.heart.rate.calculator.heartbeat.HeartQRS;


public class QRSResetChecker implements ResetChecker {

	@Override
	public boolean isReset(HeartBeat... heartBeats) {
		return Arrays.stream(heartBeats).anyMatch(heartBeat -> HeartQRS.INVALID == heartBeat.getQrs());
	}
}
