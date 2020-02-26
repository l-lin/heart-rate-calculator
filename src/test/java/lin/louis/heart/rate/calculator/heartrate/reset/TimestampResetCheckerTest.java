package lin.louis.heart.rate.calculator.heartrate.reset;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import lin.louis.heart.rate.calculator.heartbeat.HeartBeat;
import lin.louis.heart.rate.calculator.heartbeat.HeartQRS;


class TimestampResetCheckerTest {

	private ResetChecker checker = new TimestampResetChecker();

	@Test
	void isReset() {
		assertTrue(checker.isReset(Arrays.asList(
				new HeartBeat(
						LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 21),
						80,
						HeartQRS.NORMAL
				),
				new HeartBeat(
						LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 22),
						80,
						HeartQRS.NORMAL
				),
				new HeartBeat(
						LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 11),
						80,
						HeartQRS.NORMAL
				),
				new HeartBeat(
						LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 23),
						80,
						HeartQRS.NORMAL
				)
		)));
	}

	@Test
	void isNotReset() {
		assertFalse(checker.isReset(Arrays.asList(
				new HeartBeat(
						LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 11),
						80,
						HeartQRS.NORMAL
				),
				new HeartBeat(
						LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 12),
						80,
						HeartQRS.NORMAL
				),
				new HeartBeat(
						LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 15),
						80,
						HeartQRS.NORMAL
				),
				new HeartBeat(
						LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 19),
						80,
						HeartQRS.NORMAL
				)
		)));
		assertFalse(checker.isReset(Collections.singletonList(
				new HeartBeat(
						LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 11),
						80,
						HeartQRS.NORMAL
				)
		)), "One heart beat");
	}
}