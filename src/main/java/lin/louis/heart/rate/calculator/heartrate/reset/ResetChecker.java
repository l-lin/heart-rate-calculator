package lin.louis.heart.rate.calculator.heartrate.reset;

import lin.louis.heart.rate.calculator.heartbeat.HeartBeat;


public interface ResetChecker {
	boolean isReset(HeartBeat... heartBeats);
}
