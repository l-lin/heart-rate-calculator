package lin.louis.heart.rate.calculator.heartrate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.Test;


class HeartRateConverterTest {

	private HeartRateConverter converter = new HeartRateConverter(" ");

	@Test
	void apply() {
		assertAll(
				() -> assertEquals("1574676011000 91.3", converter.apply(
						new HeartRate(LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 11), "91.3"))
				),
				() -> assertEquals("1574676011000 NaN", converter.apply(
						new HeartRate(LocalDateTime.of(2019, Month.NOVEMBER.getValue(), 25, 10, 0, 11), "NaN"))
				),
				() -> assertEquals("", converter.apply(null))
		);
	}
}