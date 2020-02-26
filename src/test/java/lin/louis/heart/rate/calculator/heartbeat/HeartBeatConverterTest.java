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
	void normalHeartBeat() {
		var hb = converter.apply("1574676011000 80 N");
		assertNotNull(hb);
		var expectedTimestamp = LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 11);
		assertAll("heart beat",
				() -> {
					assertTrue(hb.getTimestamp().isPresent());
					assertEquals(expectedTimestamp, hb.getTimestamp().get());
				},
				() -> {
					assertTrue(hb.getHri().isPresent());
					assertEquals(80, hb.getHri().get());
				},
				() -> {
					assertTrue(hb.getQrs().isPresent());
					assertEquals(HeartQRS.NORMAL, hb.getQrs().get());
				}
		);
	}

	@Test
	void ignoringTooManyParameters() {
		var hb = converter.apply("1574676011000 80 N foobar barfoo");
		assertNotNull(hb);
		var expectedTimestamp = LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 11);
		assertAll("heart beat",
				() -> {
					assertTrue(hb.getTimestamp().isPresent());
					assertEquals(expectedTimestamp, hb.getTimestamp().get());
				},
				() -> {
					assertTrue(hb.getHri().isPresent());
					assertEquals(80, hb.getHri().get());
				},
				() -> {
					assertTrue(hb.getQrs().isPresent());
					assertEquals(HeartQRS.NORMAL, hb.getQrs().get());
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
				() -> assertInvalid("HRI parsing error", converter.apply("1574676011000 foobar N"))
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