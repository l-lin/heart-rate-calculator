package lin.louis.heart.rate.calculator.heartrate.reset;

import java.util.ArrayList;
import java.util.Queue;

import lin.louis.heart.rate.calculator.heartbeat.HeartBeat;


public class TimestampResetChecker implements ResetChecker {

	@Override
	public boolean isReset(Queue<HeartBeat> heartBeats) {
		var heartBeatList = new ArrayList<>(heartBeats);
		for (var i = 0; i < heartBeatList.size() - 1; i++) {
			var first = heartBeatList.get(i).getTimestamp();
			var second = heartBeatList.get(i + 1).getTimestamp();
			if (first.isPresent() && second.isPresent() && first.get().isAfter(second.get())) {
				return true;
			}
		}
		return false;
	}
}
