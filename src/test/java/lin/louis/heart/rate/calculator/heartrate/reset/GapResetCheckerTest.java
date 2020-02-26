package lin.louis.heart.rate.calculator.heartrate.reset;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import lin.louis.heart.rate.calculator.heartbeat.HeartBeat;
import lin.louis.heart.rate.calculator.heartbeat.HeartBeatQRS;


class GapResetCheckerTest {

	private ResetChecker checker = new GapResetChecker(Duration.ofSeconds(5));

	@Test
	void isReset() {
		assertTrue(checker.isReset(Arrays.asList(
				new HeartBeat(
						LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 11),
						80,
						HeartBeatQRS.NORMAL
				),
				new HeartBeat(
						LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 12),
						80,
						HeartBeatQRS.NORMAL
				),
				new HeartBeat(
						LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 21),
						80,
						HeartBeatQRS.NORMAL
				),
				new HeartBeat(
						LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 23),
						80,
						HeartBeatQRS.NORMAL
				)
		)), "With a gap");
	}

	@Test
	void isNotReset() {
		assertFalse(checker.isReset(Arrays.asList(
				new HeartBeat(
						LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 11),
						80,
						HeartBeatQRS.NORMAL
				),
				new HeartBeat(
						LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 12),
						80,
						HeartBeatQRS.NORMAL
				),
				new HeartBeat(
						LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 14),
						80,
						HeartBeatQRS.NORMAL
				),
				new HeartBeat(
						LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 18),
						80,
						HeartBeatQRS.NORMAL
				),
				new HeartBeat(
						LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 19),
						80,
						HeartBeatQRS.NORMAL
				)
		)));
		assertFalse(checker.isReset(Collections.singletonList(new HeartBeat(
				LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 19),
				80,
				HeartBeatQRS.NORMAL
		))), "One heart beat");
	}
}