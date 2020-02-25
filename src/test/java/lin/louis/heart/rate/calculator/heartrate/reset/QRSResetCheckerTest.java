package lin.louis.heart.rate.calculator.heartrate.reset;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import lin.louis.heart.rate.calculator.heartbeat.HeartBeat;
import lin.louis.heart.rate.calculator.heartbeat.HeartQRS;


class QRSResetCheckerTest {
	private ResetChecker checker = new QRSResetChecker();

	@Test
	void isReset() {
		assertTrue(checker.isReset(new HeartBeat(LocalDateTime.now(), 300, HeartQRS.INVALID)),
				"Invalid heart beat");
	}

	@Test
	void isNotReset() {
		assertFalse(checker.isReset(
				new HeartBeat(LocalDateTime.now(), 80, HeartQRS.NORMAL),
				new HeartBeat(LocalDateTime.now(), 8, HeartQRS.FUSION),
				new HeartBeat(LocalDateTime.now(), 88, HeartQRS.PACED),
				new HeartBeat(LocalDateTime.now(), 180, HeartQRS.PREMATURE_VENTRICULAR),
				new HeartBeat(LocalDateTime.now(), 8, HeartQRS.SUPRA_VENTRICULAR)
		), "Happy path");
	}
}