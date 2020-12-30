package test.java;

import filesprocessing.DirectoryProcessor;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


public class SecondaryTester {

	private static final String DIR_PATH = "C:\\Users\\yoavs\\Desktop\\University\\Year2\\OOP\\Ex5\\src\\test\\java\\";
	private static final String COMMAND_FILES_PATH = DIR_PATH + "MyCommandFiles\\";
	private static final String OUTPUT_FILES_PATH = DIR_PATH + "MyOutputFiles\\";
	private static final String TEST_FILES_PATH = DIR_PATH + "Test_directory\\";

	@Test
	public void tests() throws Exception {
		String expected, actual;
		PrintStream org_out = System.out;
		PrintStream org_err = System.err;

		File[] command_files = (new File(COMMAND_FILES_PATH)).listFiles();

		for (File file : command_files) {
			expected = new String(Files.readAllBytes(
					Paths.get(OUTPUT_FILES_PATH + file.getName())), StandardCharsets.UTF_8);

			ByteArrayOutputStream bytes_err = new ByteArrayOutputStream();
			System.setErr(new PrintStream(bytes_err, true, "UTF-8"));
			ByteArrayOutputStream bytes_out = new ByteArrayOutputStream();
			System.setOut(new PrintStream(bytes_out, true, "UTF-8"));

			String[] newArgs = {TEST_FILES_PATH, COMMAND_FILES_PATH + file.getName()};
			DirectoryProcessor.main(newArgs);

			System.setErr(org_err);
			System.setOut(org_out);

			actual = "";
			if (!bytes_err.toString("UTF-8").isEmpty()) {
				actual += bytes_err.toString("UTF-8");
			}
			actual += bytes_out.toString("UTF-8");

			Assert.assertEquals("difference found in file " + file.getName(), expected, actual);
		}
	}
}


