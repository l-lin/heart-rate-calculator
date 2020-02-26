package lin.louis.heart.rate.calculator.heartrate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


class HeartRateComputorTest {

	private HeartRateComputor computor = new HeartRateComputor();

	@Test
	void compute() {
		assertAll(
				() -> {
					assertEquals(4.5, computor.compute(1, 2, 3, 4, 5, 6, 7, 8), "pair number of values");
					assertEquals(4, computor.compute(1, 2, 3, 4, 5, 6, 7), "impair number of values");
					assertEquals(5, computor.compute(5), "one element");
					assertEquals(0, computor.compute(), "no element");
				}
		);
	}
}