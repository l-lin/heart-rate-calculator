package lin.louis.heart.rate.calculator.heartbeat;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;


class HeartBeatQRSTest {

	@Test
	void from() {
		assertAll(
				() -> assertEquals(HeartBeatQRS.SUPRA_VENTRICULAR, HeartBeatQRS.from("A")),
				() -> assertEquals(HeartBeatQRS.PREMATURE_VENTRICULAR, HeartBeatQRS.from("V")),
				() -> assertEquals(HeartBeatQRS.NORMAL, HeartBeatQRS.from("N")),
				() -> assertEquals(HeartBeatQRS.FUSION, HeartBeatQRS.from("F")),
				() -> assertEquals(HeartBeatQRS.PACED, HeartBeatQRS.from("P")),
				() -> assertEquals(HeartBeatQRS.INVALID, HeartBeatQRS.from("X")),
				() -> assertEquals(HeartBeatQRS.PREMATURE_VENTRICULAR, HeartBeatQRS.from("v"), "Ignore case"),
				() -> assertNull(HeartBeatQRS.from("FOOBAR"))
		);
	}
}