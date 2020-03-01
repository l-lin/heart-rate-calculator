package lin.louis.heart.rate.calculator.heartrate.reset;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.junit.jupiter.api.Test;

import lin.louis.heart.rate.calculator.heartbeat.HeartBeat;
import lin.louis.heart.rate.calculator.heartbeat.HeartBeatQRS;


class TimestampResetCheckerTest {

	private ResetChecker checker = new TimestampResetChecker();

	@Test
	void isReset() {
		var heartBeats = new CircularFifoQueue<HeartBeat>(8);
		heartBeats.add(new HeartBeat(
				LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 21),
				80,
				HeartBeatQRS.NORMAL
		));
		heartBeats.add(new HeartBeat(
				LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 22),
				80,
				HeartBeatQRS.NORMAL
		));
		// DATE PRIOR TO PREVIOUS DATE
		heartBeats.add(new HeartBeat(
				LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 11),
				80,
				HeartBeatQRS.NORMAL
		));
		heartBeats.add(new HeartBeat(
				LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 23),
				80,
				HeartBeatQRS.NORMAL
		));
		assertTrue(checker.isReset(heartBeats));
	}

	@Test
	void isNotReset() {
		var heartBeats = new CircularFifoQueue<HeartBeat>(8);
		heartBeats.add(new HeartBeat(
				LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 11),
				80,
				HeartBeatQRS.NORMAL
		));
		heartBeats.add(new HeartBeat(
				LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 12),
				80,
				HeartBeatQRS.NORMAL
		));
		heartBeats.add(new HeartBeat(
				LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 15),
				80,
				HeartBeatQRS.NORMAL
		));
		heartBeats.add(new HeartBeat(
				LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 19),
				80,
				HeartBeatQRS.NORMAL
		));
		assertFalse(checker.isReset(heartBeats));

		heartBeats.clear();
		heartBeats.add(
				new HeartBeat(
						LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 11),
						80,
						HeartBeatQRS.NORMAL
				));
		assertFalse(checker.isReset(heartBeats), "One heart beat");
	}
}