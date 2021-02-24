package eci.arsw.covidanalyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A Camel Application
 */
public class CovidAnalyzerTool {

	public ResultAnalyzer resultAnalyzer;
	public TestReader testReader;
	private int amountOfFilesTotal;
	public AtomicInteger amountOfFilesProcessed;
	private final int amountOfThreads = 5;

	public CovidAnalyzerTool() {
		resultAnalyzer = new ResultAnalyzer();
		testReader = new TestReader();
		amountOfFilesProcessed = new AtomicInteger();
	}

	public void processResultData() {
		amountOfFilesProcessed.set(0);
		List<File> resultFiles = getResultFileList();
		amountOfFilesTotal = resultFiles.size();
		for (int x = 0; x < amountOfThreads; x++) {
			int limiteinf = x * (amountOfFilesTotal / amountOfThreads);
			int limitesup;
			if (x != amountOfThreads - 1) {
				limitesup = ((x + 1) * (amountOfFilesTotal / amountOfThreads)) - 1;
			} else {
				limitesup = amountOfFilesTotal - 1;
			}
			ProcessDataThread t = new ProcessDataThread(limiteinf, limitesup, this, resultFiles);
			t.start();
		}
	}

	private List<File> getResultFileList() {
		List<File> csvFiles = new ArrayList<>();
		try (Stream<Path> csvFilePaths = Files.walk(Paths.get("src/main/resources/"))
				.filter(path -> path.getFileName().toString().endsWith(".csv"))) {
			csvFiles = csvFilePaths.map(Path::toFile).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return csvFiles;
	}

	public Set<Result> getPositivePeople() {
		return resultAnalyzer.listOfPositivePeople();
	}

	/**
	 * A main() so we can easily run these routing rules in our IDE
	 */
	public static void main(String... args) throws Exception {
		CovidAnalyzerTool covidAnalyzerTool = new CovidAnalyzerTool();
		Thread processingThread = new Thread(() -> covidAnalyzerTool.processResultData());
		processingThread.start();
		while (true) {
			Scanner scanner = new Scanner(System.in);
			String line = scanner.nextLine();
			if (line.contains("exit"))
				break;
			String message = "Processed %d out of %d files.\nFound %d positive people:\n%s";
			Set<Result> positivePeople = covidAnalyzerTool.getPositivePeople();
			String affectedPeople = positivePeople.stream().map(Result::toString).reduce("",
					(s1, s2) -> s1 + "\n" + s2);
			message = String.format(message, covidAnalyzerTool.amountOfFilesProcessed.get(),
					covidAnalyzerTool.amountOfFilesTotal, positivePeople.size(), affectedPeople);
			System.out.println(message);
		}
	}

}
