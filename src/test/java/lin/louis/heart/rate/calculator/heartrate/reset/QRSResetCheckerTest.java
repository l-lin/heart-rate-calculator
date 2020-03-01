package lin.louis.heart.rate.calculator.heartrate.reset;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.junit.jupiter.api.Test;

import lin.louis.heart.rate.calculator.heartbeat.HeartBeat;
import lin.louis.heart.rate.calculator.heartbeat.HeartBeatQRS;


class QRSResetCheckerTest {

	private ResetChecker checker = new QRSResetChecker();

	@Test
	void isReset() {
		var heartBeats = new CircularFifoQueue<HeartBeat>(8);
		heartBeats.add(new HeartBeat(LocalDateTime.now(), 80, HeartBeatQRS.NORMAL));
		heartBeats.add(new HeartBeat(LocalDateTime.now(), 8, HeartBeatQRS.FUSION));
		heartBeats.add(new HeartBeat(LocalDateTime.now(), 88, HeartBeatQRS.INVALID));
		heartBeats.add(new HeartBeat(LocalDateTime.now(), 180, HeartBeatQRS.PREMATURE_VENTRICULAR));
		heartBeats.add(new HeartBeat(LocalDateTime.now(), 8, HeartBeatQRS.SUPRA_VENTRICULAR));
		assertTrue(checker.isReset(heartBeats), "Invalid heart beat");

		heartBeats.clear();
		heartBeats.add(new HeartBeat(LocalDateTime.now(), 80, HeartBeatQRS.NORMAL));
		heartBeats.add(new HeartBeat(LocalDateTime.now(), 8, HeartBeatQRS.FUSION));
		heartBeats.add(new HeartBeat(LocalDateTime.now(), 88, null));
		heartBeats.add(new HeartBeat(LocalDateTime.now(), 180, HeartBeatQRS.PREMATURE_VENTRICULAR));
		heartBeats.add(new HeartBeat(LocalDateTime.now(), 8, HeartBeatQRS.SUPRA_VENTRICULAR));
		assertTrue(checker.isReset(heartBeats), "Null heart beat");
	}

	@Test
	void isNotReset() {
		var heartBeats = new CircularFifoQueue<HeartBeat>(8);
		heartBeats.add(new HeartBeat(LocalDateTime.now(), 80, HeartBeatQRS.NORMAL));
		heartBeats.add(new HeartBeat(LocalDateTime.now(), 8, HeartBeatQRS.FUSION));
		heartBeats.add(new HeartBeat(LocalDateTime.now(), 88, HeartBeatQRS.PACED));
		heartBeats.add(new HeartBeat(LocalDateTime.now(), 180, HeartBeatQRS.PREMATURE_VENTRICULAR));
		heartBeats.add(new HeartBeat(LocalDateTime.now(), 8, HeartBeatQRS.SUPRA_VENTRICULAR));
		assertFalse(checker.isReset(heartBeats), "Happy path");
	}
}