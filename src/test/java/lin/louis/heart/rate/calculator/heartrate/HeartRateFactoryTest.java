package lin.louis.heart.rate.calculator.heartrate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Queue;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.junit.jupiter.api.Test;

import lin.louis.heart.rate.calculator.heartbeat.HeartBeat;
import lin.louis.heart.rate.calculator.heartbeat.HeartBeatQRS;
import lin.louis.heart.rate.calculator.heartrate.reset.GapResetChecker;
import lin.louis.heart.rate.calculator.heartrate.reset.HriResetChecker;
import lin.louis.heart.rate.calculator.heartrate.reset.QRSResetChecker;
import lin.louis.heart.rate.calculator.heartrate.reset.ResetCheckerFacade;
import lin.louis.heart.rate.calculator.heartrate.reset.TimestampResetChecker;


class HeartRateFactoryTest {

	private HeartRateFactory heartRateFactory = new HeartRateFactory(
			8,
			new ResetCheckerFacade(Arrays.asList(
					new GapResetChecker(Duration.ofSeconds(5)),
					new HriResetChecker(0, 250),
					new QRSResetChecker(),
					new TimestampResetChecker()
			)),
			new HeartRateValueComputor()
	);

	@Test
	void create() {
		// GIVEN
		Queue<HeartBeat> heartBeats = new CircularFifoQueue<>(8);
		heartBeats.add(new HeartBeat(newLocalDateTime(1), 80, HeartBeatQRS.NORMAL));
		heartBeats.add(new HeartBeat(newLocalDateTime(2), 100, HeartBeatQRS.PREMATURE_VENTRICULAR));
		heartBeats.add(new HeartBeat(newLocalDateTime(3), 83, HeartBeatQRS.NORMAL));
		heartBeats.add(new HeartBeat(newLocalDateTime(4), 80, HeartBeatQRS.PACED));
		heartBeats.add(new HeartBeat(newLocalDateTime(5), 91, HeartBeatQRS.SUPRA_VENTRICULAR));
		heartBeats.add(new HeartBeat(newLocalDateTime(7), 88, HeartBeatQRS.NORMAL));
		heartBeats.add(new HeartBeat(newLocalDateTime(8), 70, HeartBeatQRS.NORMAL));
		heartBeats.add(new HeartBeat(newLocalDateTime(10), 10, HeartBeatQRS.FUSION));

		// WHEN
		var heartRate = heartRateFactory.create(heartBeats);

		// THEN
		assertNotNull(heartRate);
		assertAll(
				() -> {
					assertTrue(heartRate.getTimestamp().isPresent());
					assertEquals(newLocalDateTime(10), heartRate.getTimestamp().get());
				},
				() -> assertEquals(81.5d, heartRate.getValue())
		);
	}

	@Test
	void create_moreThan8HeartBeats() {
		// GIVEN
		Queue<HeartBeat> heartBeats = new CircularFifoQueue<>(8);
		heartBeats.add(new HeartBeat(newLocalDateTime(1), 80, HeartBeatQRS.NORMAL));
		heartBeats.add(new HeartBeat(newLocalDateTime(2), 100, HeartBeatQRS.PREMATURE_VENTRICULAR));
		heartBeats.add(new HeartBeat(newLocalDateTime(3), 83, HeartBeatQRS.NORMAL));
		heartBeats.add(new HeartBeat(newLocalDateTime(4), 80, HeartBeatQRS.PACED));
		heartBeats.add(new HeartBeat(newLocalDateTime(5), 91, HeartBeatQRS.SUPRA_VENTRICULAR));
		heartBeats.add(new HeartBeat(newLocalDateTime(7), 88, HeartBeatQRS.NORMAL));
		heartBeats.add(new HeartBeat(newLocalDateTime(8), 70, HeartBeatQRS.NORMAL));
		heartBeats.add(new HeartBeat(newLocalDateTime(10), 10, HeartBeatQRS.FUSION));
		heartBeats.add(new HeartBeat(newLocalDateTime(11), 110, HeartBeatQRS.NORMAL));
		heartBeats.add(new HeartBeat(newLocalDateTime(13), 10, HeartBeatQRS.FUSION));
		heartBeats.add(new HeartBeat(newLocalDateTime(17), 193, HeartBeatQRS.PREMATURE_VENTRICULAR));

		// WHEN
		var heartRate = heartRateFactory.create(heartBeats);

		// THEN
		assertNotNull(heartRate);
		assertAll(
				() -> {
					assertTrue(heartRate.getTimestamp().isPresent());
					assertEquals(newLocalDateTime(17), heartRate.getTimestamp().get());
				},
				() -> assertEquals(84.0d, heartRate.getValue())
		);
	}

	@Test
	void create_reset() {
		// GIVEN
		Queue<HeartBeat> heartBeats = new CircularFifoQueue<>(8);
		heartBeats.add(new HeartBeat(newLocalDateTime(1), 80, HeartBeatQRS.NORMAL));
		heartBeats.add(new HeartBeat(newLocalDateTime(2), 100, HeartBeatQRS.PREMATURE_VENTRICULAR));
		heartBeats.add(new HeartBeat(newLocalDateTime(3), 83, HeartBeatQRS.NORMAL));
		heartBeats.add(new HeartBeat(newLocalDateTime(4), 80, HeartBeatQRS.PACED));
		// Reset at this moment
		heartBeats.add(new HeartBeat(newLocalDateTime(5), 91, HeartBeatQRS.INVALID));
		heartBeats.add(new HeartBeat(newLocalDateTime(7), 88, HeartBeatQRS.NORMAL));
		heartBeats.add(new HeartBeat(newLocalDateTime(8), 70, HeartBeatQRS.NORMAL));
		heartBeats.add(new HeartBeat(newLocalDateTime(10), 10, HeartBeatQRS.FUSION));
		heartBeats.add(new HeartBeat(newLocalDateTime(11), 110, HeartBeatQRS.NORMAL));
		heartBeats.add(new HeartBeat(newLocalDateTime(13), 10, HeartBeatQRS.FUSION));
		heartBeats.add(new HeartBeat(newLocalDateTime(17), 193, HeartBeatQRS.PREMATURE_VENTRICULAR));

		// WHEN
		var heartRate = heartRateFactory.create(heartBeats);

		// THEN
		assertNotNull(heartRate);
		assertAll(
				() -> {
					assertTrue(heartRate.getTimestamp().isPresent());
					assertEquals(newLocalDateTime(17), heartRate.getTimestamp().get());
				},
				() -> assertEquals(Double.NaN, heartRate.getValue())
		);
	}

	@Test
	void create_lessThan8HeartBeats() {
		// GIVEN
		Queue<HeartBeat> heartBeats = new CircularFifoQueue<>(8);
		heartBeats.add(new HeartBeat(newLocalDateTime(1), 80, HeartBeatQRS.NORMAL));
		heartBeats.add(new HeartBeat(newLocalDateTime(2), 100, HeartBeatQRS.PREMATURE_VENTRICULAR));
		heartBeats.add(new HeartBeat(newLocalDateTime(3), 83, HeartBeatQRS.NORMAL));
		heartBeats.add(new HeartBeat(newLocalDateTime(4), 80, HeartBeatQRS.PACED));

		// WHEN
		var heartRate = heartRateFactory.create(heartBeats);

		// THEN
		assertNotNull(heartRate);
		assertAll(
				() -> {
					assertTrue(heartRate.getTimestamp().isPresent());
					assertEquals(newLocalDateTime(4), heartRate.getTimestamp().get());
				},
				() -> assertEquals(Double.NaN, heartRate.getValue())
		);
	}

	@Test
	void create_emptyList() {
		// WHEN
		var heartRate = heartRateFactory.create(new CircularFifoQueue<>(8));

		// THEN
		assertNotNull(heartRate);
		assertAll(
				() -> assertTrue(heartRate.getTimestamp().isEmpty()),
				() -> assertEquals(Double.NaN, heartRate.getValue())
		);
	}

	private LocalDateTime newLocalDateTime(int seconds) {
		return LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, seconds);
	}
}