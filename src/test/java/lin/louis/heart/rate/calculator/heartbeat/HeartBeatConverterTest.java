package lin.louis.heart.rate.calculator.heartbeat;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.Test;


class HeartBeatConverterTest {

	private HeartBeatConverter converter = new HeartBeatConverter(" ");

	@Test
	void apply_normalHeartBeat() {
		// GIVEN
		var heartBeatStr = "1574676011000 80 N";

		// WHEN
		var heartBeat = converter.apply(heartBeatStr);

		// THEN
		assertNotNull(heartBeat);
		var expectedTimestamp = LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 11);
		assertAll("heart beat",
				() -> {
					assertTrue(heartBeat.getTimestamp().isPresent());
					assertEquals(expectedTimestamp, heartBeat.getTimestamp().get());
				},
				() -> {
					assertTrue(heartBeat.getHri().isPresent());
					assertEquals(80, heartBeat.getHri().get());
				},
				() -> {
					assertTrue(heartBeat.getQrs().isPresent());
					assertEquals(HeartBeatQRS.NORMAL, heartBeat.getQrs().get());
				}
		);
	}

	@Test
	void apply_ignoringTooManyParameters() {
		// GIVEN
		String heartBeatStr = "1574676011000 80 N foobar barfoo";

		// WHEN
		var heartBeat = converter.apply(heartBeatStr);

		// THEN
		assertNotNull(heartBeat);
		var expectedTimestamp = LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 11);
		assertAll("heart beat",
				() -> {
					assertTrue(heartBeat.getTimestamp().isPresent());
					assertEquals(expectedTimestamp, heartBeat.getTimestamp().get());
				},
				() -> {
					assertTrue(heartBeat.getHri().isPresent());
					assertEquals(80, heartBeat.getHri().get());
				},
				() -> {
					assertTrue(heartBeat.getQrs().isPresent());
					assertEquals(HeartBeatQRS.NORMAL, heartBeat.getQrs().get());
				}
		);
	}

	@Test
	void edgeCases() {
		assertAll(
				() -> assertInvalid("Empty string", converter.apply("")),
				() -> assertInvalid("Null string", converter.apply(null)),
				() -> assertInvalid("String containing only spaces", converter.apply("    ")),
				() -> assertInvalid("Not enough parameters", converter.apply("1574676011000 80")),
				() -> assertInvalid("Timestamp parsing error", converter.apply("foobar 80 N")),
				() -> assertInvalid("HRI parsing error", converter.apply("1574676011000 foobar N")),
				() -> assertInvalid("QRS parsing error", converter.apply("1574676011000 80 H"))
		);
	}

	private void assertInvalid(String heading, HeartBeat hb) {
		assertNotNull(hb);
		assertAll(heading,
				() -> assertTrue(hb.getTimestamp().isEmpty()),
				() -> assertTrue(hb.getHri().isEmpty()),
				() -> assertTrue(hb.getQrs().isEmpty())
		);
	}
}