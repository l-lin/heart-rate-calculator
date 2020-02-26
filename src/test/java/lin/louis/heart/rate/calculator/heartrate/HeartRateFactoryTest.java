package lin.louis.heart.rate.calculator.heartrate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import lin.louis.heart.rate.calculator.heartbeat.HeartBeat;
import lin.louis.heart.rate.calculator.heartbeat.HeartQRS;
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
			new HeartRateComputor(),
			new HeartRateValueConverter()
	);

	@Test
	void create() {
		// GIVEN
		List<HeartBeat> heartBeatList = Arrays.asList(
				new HeartBeat(newLocalDateTime(1), 80, HeartQRS.NORMAL),
				new HeartBeat(newLocalDateTime(2), 100, HeartQRS.PREMATURE_VENTRICULAR),
				new HeartBeat(newLocalDateTime(3), 83, HeartQRS.NORMAL),
				new HeartBeat(newLocalDateTime(4), 80, HeartQRS.PACED),
				new HeartBeat(newLocalDateTime(5), 91, HeartQRS.SUPRA_VENTRICULAR),
				new HeartBeat(newLocalDateTime(7), 88, HeartQRS.NORMAL),
				new HeartBeat(newLocalDateTime(8), 70, HeartQRS.NORMAL),
				new HeartBeat(newLocalDateTime(10), 10, HeartQRS.FUSION)
		);

		// WHEN
		var heartRate = heartRateFactory.create(heartBeatList);

		// THEN
		assertNotNull(heartRate);
		assertAll(
				() -> {
					assertTrue(heartRate.getTimestamp().isPresent());
					assertEquals(newLocalDateTime(10), heartRate.getTimestamp().get());
				},
				() -> assertEquals("81.5", heartRate.getValue())
		);
	}

	@Test
	void create_moreThan8HeartBeats() {
		// GIVEN
		List<HeartBeat> heartBeatList = Arrays.asList(
				new HeartBeat(newLocalDateTime(1), 80, HeartQRS.NORMAL),
				new HeartBeat(newLocalDateTime(2), 100, HeartQRS.PREMATURE_VENTRICULAR),
				new HeartBeat(newLocalDateTime(3), 83, HeartQRS.NORMAL),
				new HeartBeat(newLocalDateTime(4), 80, HeartQRS.PACED),
				new HeartBeat(newLocalDateTime(5), 91, HeartQRS.SUPRA_VENTRICULAR),
				new HeartBeat(newLocalDateTime(7), 88, HeartQRS.NORMAL),
				new HeartBeat(newLocalDateTime(8), 70, HeartQRS.NORMAL),
				new HeartBeat(newLocalDateTime(10), 90, HeartQRS.FUSION),
				new HeartBeat(newLocalDateTime(11), 110, HeartQRS.NORMAL),
				new HeartBeat(newLocalDateTime(13), 10, HeartQRS.FUSION),
				new HeartBeat(newLocalDateTime(17), 193, HeartQRS.PREMATURE_VENTRICULAR)
		);

		// WHEN
		var heartRate = heartRateFactory.create(heartBeatList);

		// THEN
		assertNotNull(heartRate);
		assertAll(
				() -> {
					assertTrue(heartRate.getTimestamp().isPresent());
					assertEquals(newLocalDateTime(17), heartRate.getTimestamp().get());
				},
				() -> assertEquals("88.0", heartRate.getValue())
		);
	}

	@Test
	void create_reset() {
		// GIVEN
		List<HeartBeat> heartBeatList = Arrays.asList(
				new HeartBeat(newLocalDateTime(1), 80, HeartQRS.NORMAL),
				new HeartBeat(newLocalDateTime(2), 100, HeartQRS.PREMATURE_VENTRICULAR),
				new HeartBeat(newLocalDateTime(3), 83, HeartQRS.NORMAL),
				new HeartBeat(newLocalDateTime(4), 80, HeartQRS.PACED),
				new HeartBeat(newLocalDateTime(5), 91, HeartQRS.INVALID),
				new HeartBeat(newLocalDateTime(7), 88, HeartQRS.NORMAL),
				new HeartBeat(newLocalDateTime(8), 70, HeartQRS.NORMAL),
				new HeartBeat(newLocalDateTime(10), 90, HeartQRS.FUSION),
				new HeartBeat(newLocalDateTime(11), 110, HeartQRS.NORMAL),
				new HeartBeat(newLocalDateTime(13), 10, HeartQRS.FUSION),
				new HeartBeat(newLocalDateTime(17), 193, HeartQRS.PREMATURE_VENTRICULAR)
		);

		// WHEN
		var heartRate = heartRateFactory.create(heartBeatList);

		// THEN
		assertNotNull(heartRate);
		assertAll(
				() -> {
					assertTrue(heartRate.getTimestamp().isPresent());
					assertEquals(newLocalDateTime(17), heartRate.getTimestamp().get());
				},
				() -> assertEquals("NaN", heartRate.getValue()),
				() -> {
					assertEquals(11, heartBeatList.size());
					heartBeatList.forEach(hb -> {
						assertTrue(hb.getQrs().isEmpty());
						assertTrue(hb.getQrs().isEmpty());
					});
				}
		);
	}

	@Test
	void create_lessThan8HeartBeats() {
		// GIVEN
		List<HeartBeat> heartBeatList = Arrays.asList(
				new HeartBeat(newLocalDateTime(1), 80, HeartQRS.NORMAL),
				new HeartBeat(newLocalDateTime(2), 100, HeartQRS.PREMATURE_VENTRICULAR),
				new HeartBeat(newLocalDateTime(3), 83, HeartQRS.NORMAL),
				new HeartBeat(newLocalDateTime(4), 80, HeartQRS.PACED)
		);

		// WHEN
		var heartRate = heartRateFactory.create(heartBeatList);

		// THEN
		assertNotNull(heartRate);
		assertAll(
				() -> {
					assertTrue(heartRate.getTimestamp().isPresent());
					assertEquals(newLocalDateTime(4), heartRate.getTimestamp().get());
				},
				() -> assertEquals("NaN", heartRate.getValue())
		);
	}

	@Test
	void create_emptyList() {
		// WHEN
		var heartRate = heartRateFactory.create(Collections.emptyList());

		// THEN
		assertNotNull(heartRate);
		assertAll(
				() -> assertTrue(heartRate.getTimestamp().isEmpty()),
				() -> assertEquals("NaN", heartRate.getValue())
		);
	}

	private LocalDateTime newLocalDateTime(int seconds) {
		return LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, seconds);
	}
}