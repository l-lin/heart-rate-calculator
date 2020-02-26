package lin.louis.heart.rate.calculator.heartrate.reset;

import java.util.List;

import lin.louis.heart.rate.calculator.heartbeat.HeartBeat;


public class ResetCheckerFacade {

	private final List<ResetChecker> resetCheckers;

	public ResetCheckerFacade(List<ResetChecker> resetCheckers) {this.resetCheckers = resetCheckers;}

	public boolean isReset(List<HeartBeat> heartBeatList) {
		return resetCheckers.stream().anyMatch(checker -> checker.isReset(heartBeatList));
	}
}
