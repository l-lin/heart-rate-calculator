package lin.louis.heart.rate.calculator.heartrate.reset;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.junit.jupiter.api.Test;

import lin.louis.heart.rate.calculator.heartbeat.HeartBeat;
import lin.louis.heart.rate.calculator.heartbeat.HeartBeatQRS;


class HriResetCheckerTest {

	private ResetChecker checker = new HriResetChecker(0, 250);

	@Test
	void isReset() {
		var heartBeats = new CircularFifoQueue<HeartBeat>(8);
		heartBeats.add(new HeartBeat(LocalDateTime.now(), -2, HeartBeatQRS.NORMAL));
		assertTrue(checker.isReset(heartBeats), "Heart beat below min");

		heartBeats.clear();
		heartBeats.add(new HeartBeat(LocalDateTime.now(), 300, HeartBeatQRS.NORMAL));
		assertTrue(checker.isReset(heartBeats), "Heart beat above max");
	}

	@Test
	void isNotReset() {
		var heartBeats = new CircularFifoQueue<HeartBeat>(8);
		heartBeats.add(new HeartBeat(LocalDateTime.now(), 80, HeartBeatQRS.NORMAL));
		heartBeats.add(new HeartBeat(LocalDateTime.now(), 8, HeartBeatQRS.NORMAL));
		heartBeats.add(new HeartBeat(LocalDateTime.now(), 88, HeartBeatQRS.NORMAL));
		heartBeats.add(new HeartBeat(LocalDateTime.now(), 180, HeartBeatQRS.NORMAL));
		heartBeats.add(new HeartBeat(LocalDateTime.now(), 8, HeartBeatQRS.NORMAL));
		assertFalse(checker.isReset(heartBeats), "Happy path");

		heartBeats.clear();
		heartBeats.add(new HeartBeat(LocalDateTime.now(), 0, HeartBeatQRS.NORMAL));
		assertFalse(checker.isReset(heartBeats), "Min value");

		heartBeats.clear();
		heartBeats.add(new HeartBeat(LocalDateTime.now(), 250, HeartBeatQRS.NORMAL));
		assertFalse(checker.isReset(heartBeats), "Max value");
	}
}