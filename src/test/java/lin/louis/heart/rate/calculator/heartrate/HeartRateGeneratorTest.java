package lin.louis.heart.rate.calculator.heartrate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import lin.louis.heart.rate.calculator.heartbeat.HeartBeatConverter;
import lin.louis.heart.rate.calculator.heartrate.reset.GapResetChecker;
import lin.louis.heart.rate.calculator.heartrate.reset.HriResetChecker;
import lin.louis.heart.rate.calculator.heartrate.reset.QRSResetChecker;
import lin.louis.heart.rate.calculator.heartrate.reset.ResetCheckerFacade;
import lin.louis.heart.rate.calculator.heartrate.reset.TimestampResetChecker;


class HeartRateGeneratorTest {

	HeartRateGenerator generator = new HeartRateGenerator(
			8,
			new HeartBeatConverter(" "),
			new HeartRateConverter(" "),
			new HeartRateFactory(
					8,
					new ResetCheckerFacade(Arrays.asList(
							new GapResetChecker(Duration.ofSeconds(5)),
							new HriResetChecker(0, 250),
							new QRSResetChecker(),
							new TimestampResetChecker()
					)),
					new HeartRateComputor(),
					new HeartRateValueConverter()
			)
	);

	@Test
	void generate() throws IOException {
		// GIVEN
		String inStr = "1574793524435 108 V\n"
				+ "1574793525677 48 N\n"
				+ "1574793526521 71 N\n"
				+ "1574793527367 71 N\n"
				+ "1574793528221 70 N\n"
				+ "1574793529063 71 N\n"
				+ "1574793529903 71 N\n"
				+ "1574793530769 69 N\n";

		// WHEN
		String outStr;
		try (
				InputStream in = new ByteArrayInputStream(inStr.getBytes(StandardCharsets.UTF_8));
				ByteArrayOutputStream out = new ByteArrayOutputStream()
		) {
			generator.generate(in, out);
			outStr = new String(out.toByteArray());
		}

		// THEN
		assertFalse(outStr.isBlank());
		assertEquals("1574793524435 NaN\n"
				+ "1574793525677 NaN\n"
				+ "1574793526521 NaN\n"
				+ "1574793527367 NaN\n"
				+ "1574793528221 NaN\n"
				+ "1574793529063 NaN\n"
				+ "1574793529903 NaN\n"
				+ "1574793530769 71.0\n", outStr);
	}

	@Test
	void generate_withFlush() throws IOException {
		// GIVEN
		String inStr = "1574793412717 95 V\n"
				+ "1574793413783 56 N\n"
				+ "1574793414567 77 N\n"
				+ "1574793415343 77 N\n"
				+ "1574793416429 0 N\n"
				+ "1574793417023 101 V\n"
				+ "1574793418095 56 N\n"
				+ "1574793418915 73 N\n"
				+ "1574793419749 72 N\n"
				+ "1574793400571 73 N\n"
				+ "1574793421389 73 N";

		// WHEN
		String outStr;
		try (
				InputStream in = new ByteArrayInputStream(inStr.getBytes(StandardCharsets.UTF_8));
				ByteArrayOutputStream out = new ByteArrayOutputStream()
		) {
			generator.generate(in, out);
			outStr = new String(out.toByteArray());
		}

		// THEN
		assertFalse(outStr.isBlank());
		assertEquals("1574793412717 NaN\n"
				+ "1574793413783 NaN\n"
				+ "1574793414567 NaN\n"
				+ "1574793415343 NaN\n"
				+ "1574793416429 NaN\n"
				+ "1574793417023 NaN\n"
				+ "1574793418095 NaN\n"
				+ "1574793418915 75.0\n"
				+ "1574793419749 72.5\n"
				+ "1574793400571 NaN\n"
				+ "1574793421389 NaN\n", outStr);
	}
}