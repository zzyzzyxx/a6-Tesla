package a6Tesla;

import java.io.IOException;

public class TeslaApplication {

	public static void main(String[] args) throws IOException {
		TeslaService teslaService = new TeslaService();
        teslaService.readFile();
	}
}
