package lin.louis.heart.rate.calculator.heartrate.reset;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import lin.louis.heart.rate.calculator.heartbeat.HeartBeat;
import lin.louis.heart.rate.calculator.heartbeat.HeartBeatQRS;


class HriResetCheckerTest {

	private ResetChecker checker = new HriResetChecker(0, 250);

	@Test
	void isReset() {
		assertTrue(checker.isReset(Collections.singletonList(new HeartBeat(LocalDateTime.now(),
						-2,
						HeartBeatQRS.NORMAL))),
				"Heart beat below min");
		assertTrue(checker.isReset(Collections.singletonList(new HeartBeat(LocalDateTime.now(),
						300,
						HeartBeatQRS.NORMAL))),
				"Heart beat above max");
	}

	@Test
	void isNotReset() {
		assertFalse(checker.isReset(Arrays.asList(
				new HeartBeat(LocalDateTime.now(), 80, HeartBeatQRS.NORMAL),
				new HeartBeat(LocalDateTime.now(), 8, HeartBeatQRS.NORMAL),
				new HeartBeat(LocalDateTime.now(), 88, HeartBeatQRS.NORMAL),
				new HeartBeat(LocalDateTime.now(), 180, HeartBeatQRS.NORMAL),
				new HeartBeat(LocalDateTime.now(), 8, HeartBeatQRS.NORMAL)
		)), "Happy path");
		assertFalse(checker.isReset(Collections.singletonList(
				new HeartBeat(LocalDateTime.now(), 0, HeartBeatQRS.NORMAL)
		)), "Min value");
		assertFalse(checker.isReset(Collections.singletonList(
				new HeartBeat(LocalDateTime.now(), 250, HeartBeatQRS.NORMAL)
		)), "Max value");
	}
}