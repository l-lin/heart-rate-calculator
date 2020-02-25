package lin.louis.heart.rate.calculator.heartrate.reset;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import lin.louis.heart.rate.calculator.heartbeat.HeartBeat;
import lin.louis.heart.rate.calculator.heartbeat.HeartQRS;


class HriResetCheckerTest {

	private ResetChecker checker = new HriResetChecker(0, 250);

	@Test
	void isReset() {
		assertTrue(checker.isReset(new HeartBeat(LocalDateTime.now(), -2, HeartQRS.NORMAL)),
				"Heart beat below min");
		assertTrue(checker.isReset(new HeartBeat(LocalDateTime.now(), 300, HeartQRS.NORMAL)),
				"Heart beat above max");
	}

	@Test
	void isNotReset() {
		assertFalse(checker.isReset(
				new HeartBeat(LocalDateTime.now(), 80, HeartQRS.NORMAL),
				new HeartBeat(LocalDateTime.now(), 8, HeartQRS.NORMAL),
				new HeartBeat(LocalDateTime.now(), 88, HeartQRS.NORMAL),
				new HeartBeat(LocalDateTime.now(), 180, HeartQRS.NORMAL),
				new HeartBeat(LocalDateTime.now(), 8, HeartQRS.NORMAL)
		), "Happy path");
	}
}