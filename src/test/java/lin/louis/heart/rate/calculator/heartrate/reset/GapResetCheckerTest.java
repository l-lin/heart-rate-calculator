package lin.louis.heart.rate.calculator.heartrate.reset;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.junit.jupiter.api.Test;

import lin.louis.heart.rate.calculator.heartbeat.HeartBeat;
import lin.louis.heart.rate.calculator.heartbeat.HeartBeatQRS;


class GapResetCheckerTest {

	private ResetChecker checker = new GapResetChecker(Duration.ofSeconds(5));

	@Test
	void isReset() {
		var heartBeats = new CircularFifoQueue<HeartBeat>(8);
		heartBeats.add(new HeartBeat(LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 11),
				80,
				HeartBeatQRS.NORMAL));
		heartBeats.add(new HeartBeat(LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 12),
				0,
				HeartBeatQRS.NORMAL));
		heartBeats.add(new HeartBeat(LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 21),
				88,
				HeartBeatQRS.NORMAL));
		heartBeats.add(new HeartBeat(LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 23),
				180,
				HeartBeatQRS.PREMATURE_VENTRICULAR));
		assertTrue(checker.isReset(heartBeats), "With a gap");
	}

	@Test
	void isNotReset() {
		var heartBeats = new CircularFifoQueue<HeartBeat>(8);
		heartBeats.add(new HeartBeat(LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 11),
				80,
				HeartBeatQRS.NORMAL));
		heartBeats.add(new HeartBeat(LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 12),
				0,
				HeartBeatQRS.NORMAL));
		heartBeats.add(new HeartBeat(LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 14),
				0,
				HeartBeatQRS.NORMAL));
		heartBeats.add(new HeartBeat(LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 18),
				88,
				HeartBeatQRS.NORMAL));
		heartBeats.add(new HeartBeat(LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 19),
				180,
				HeartBeatQRS.PREMATURE_VENTRICULAR));
		assertFalse(checker.isReset(heartBeats));

		heartBeats.clear();
		heartBeats.add(new HeartBeat(
				LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 19),
				80,
				HeartBeatQRS.NORMAL
		));
		assertFalse(checker.isReset(heartBeats), "One heart beat");
	}
}