package lin.louis.heart.rate.calculator.heartrate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


class HeartRateValueConverterTest {

	private HeartRateValueConverter heartRateValueConverter = new HeartRateValueConverter();

	@Test
	void apply() {
		assertAll(
				() -> assertEquals("1.0", heartRateValueConverter.apply(1d), "one number"),
				() -> assertEquals("12.0", heartRateValueConverter.apply(12d), "two numbers"),
				() -> assertEquals("120.0", heartRateValueConverter.apply(120d), "three numbers"),
				() -> assertEquals("91.5", heartRateValueConverter.apply(91.5d), "one decimal"),
				() -> assertEquals("91.5", heartRateValueConverter.apply(91.54d), "two decimals lower half"),
				() -> assertEquals("91.6", heartRateValueConverter.apply(91.56d), "two decimals upper half"),
				() -> assertEquals("91.5", heartRateValueConverter.apply(91.55d), "two decimals even half")
		);

	}
}