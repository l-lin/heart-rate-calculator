package lin.louis.heart.rate.calculator.heartrate.reset;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import lin.louis.heart.rate.calculator.heartbeat.HeartBeat;
import lin.louis.heart.rate.calculator.heartbeat.HeartBeatQRS;


class ResetCheckerFacadeTest {

	private ResetCheckerFacade checkerFacade = new ResetCheckerFacade(Arrays.asList(
			new GapResetChecker(Duration.ofSeconds(5)),
			new HriResetChecker(0, 250),
			new QRSResetChecker(),
			new TimestampResetChecker()
	));

	@Test
	void isReset() {
		assertTrue(checkerFacade.isReset(Arrays.asList(
				new HeartBeat(LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 21), 80, HeartBeatQRS.NORMAL),
				new HeartBeat(LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 23), 0, HeartBeatQRS.INVALID),
				new HeartBeat(LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 27), -88, HeartBeatQRS.PACED),
				new HeartBeat(LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 29),
						180,
						HeartBeatQRS.PREMATURE_VENTRICULAR),
				new HeartBeat(LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 31),
						8,
						HeartBeatQRS.SUPRA_VENTRICULAR)
		)));
	}

	@Test
	void isNotReset() {
		assertFalse(checkerFacade.isReset(Arrays.asList(
				new HeartBeat(LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 21), 80, HeartBeatQRS.NORMAL),
				new HeartBeat(LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 23), 8, HeartBeatQRS.FUSION),
				new HeartBeat(LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 27), 88, HeartBeatQRS.PACED),
				new HeartBeat(LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 29),
						180,
						HeartBeatQRS.PREMATURE_VENTRICULAR),
				new HeartBeat(LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 31),
						8,
						HeartBeatQRS.SUPRA_VENTRICULAR)
		)));
	}
}