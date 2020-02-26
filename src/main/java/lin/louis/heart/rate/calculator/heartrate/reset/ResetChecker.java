package lin.louis.heart.rate.calculator.heartrate.reset;

import java.util.List;

import lin.louis.heart.rate.calculator.heartbeat.HeartBeat;


public interface ResetChecker {

	boolean isReset(List<HeartBeat> heartBeatList);
}
