package lin.louis.heart.rate.calculator.heartbeat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class HeartQRSTest {

	@Test
	void from() {
		Assertions.assertAll(
				() -> Assertions.assertEquals(HeartQRS.SUPRA_VENTRICULAR, HeartQRS.from("A")),
				() -> Assertions.assertEquals(HeartQRS.PREMATURE_VENTRICULAR, HeartQRS.from("V")),
				() -> Assertions.assertEquals(HeartQRS.NORMAL, HeartQRS.from("N")),
				() -> Assertions.assertEquals(HeartQRS.FUSION, HeartQRS.from("F")),
				() -> Assertions.assertEquals(HeartQRS.PACED, HeartQRS.from("P")),
				() -> Assertions.assertEquals(HeartQRS.INVALID, HeartQRS.from("X")),
				() -> Assertions.assertEquals(HeartQRS.INVALID, HeartQRS.from("FOOBAR"))
		);
	}
}