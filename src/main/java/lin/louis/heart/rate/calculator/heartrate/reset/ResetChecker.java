package lin.louis.heart.rate.calculator.heartrate.reset;

import java.util.Queue;

import lin.louis.heart.rate.calculator.heartbeat.HeartBeat;


public interface ResetChecker {

	boolean isReset(Queue<HeartBeat> heartBeatList);
}
