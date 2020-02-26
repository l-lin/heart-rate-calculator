package lin.louis.heart.rate.calculator.heartrate.reset;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import lin.louis.heart.rate.calculator.heartbeat.HeartBeat;
import lin.louis.heart.rate.calculator.heartbeat.HeartBeatQRS;


class QRSResetCheckerTest {

	private ResetChecker checker = new QRSResetChecker();

	@Test
	void isReset() {
		assertTrue(checker.isReset(Arrays.asList(
				new HeartBeat(LocalDateTime.now(), 80, HeartBeatQRS.NORMAL),
				new HeartBeat(LocalDateTime.now(), 8, HeartBeatQRS.FUSION),
				new HeartBeat(LocalDateTime.now(), 88, HeartBeatQRS.INVALID),
				new HeartBeat(LocalDateTime.now(), 180, HeartBeatQRS.PREMATURE_VENTRICULAR),
				new HeartBeat(LocalDateTime.now(), 8, HeartBeatQRS.SUPRA_VENTRICULAR)
		)), "Invalid heart beat");
		assertTrue(checker.isReset(Arrays.asList(
				new HeartBeat(LocalDateTime.now(), 80, HeartBeatQRS.NORMAL),
				new HeartBeat(LocalDateTime.now(), 8, HeartBeatQRS.FUSION),
				new HeartBeat(LocalDateTime.now(), 88, null),
				new HeartBeat(LocalDateTime.now(), 180, HeartBeatQRS.PREMATURE_VENTRICULAR),
				new HeartBeat(LocalDateTime.now(), 8, HeartBeatQRS.SUPRA_VENTRICULAR)
		)), "Null heart beat");
	}

	@Test
	void isNotReset() {
		assertFalse(checker.isReset(Arrays.asList(
				new HeartBeat(LocalDateTime.now(), 80, HeartBeatQRS.NORMAL),
				new HeartBeat(LocalDateTime.now(), 8, HeartBeatQRS.FUSION),
				new HeartBeat(LocalDateTime.now(), 88, HeartBeatQRS.PACED),
				new HeartBeat(LocalDateTime.now(), 180, HeartBeatQRS.PREMATURE_VENTRICULAR),
				new HeartBeat(LocalDateTime.now(), 8, HeartBeatQRS.SUPRA_VENTRICULAR)
		)), "Happy path");
	}
}