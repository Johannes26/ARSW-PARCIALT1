package eci.arsw.covidanalyzer;

import java.io.File;
import java.util.List;

public class ProcessDataThread extends Thread {

	private int _limiteinf;
	private int _limitesup;
	private CovidAnalyzerTool _covidAnalyzerTool;
	private List<File> _resultFiles;

	public ProcessDataThread(int limiteinf, int limitesup, CovidAnalyzerTool covidAnalyzerTool,
			List<File> resultFiles) {
		_limiteinf = limiteinf;
		_limitesup = limitesup;
		_covidAnalyzerTool = covidAnalyzerTool;
		_resultFiles = resultFiles;
	}

	public void run() {
		for (int i = _limiteinf; i <= _limitesup; i++) {
			System.out.println(_limiteinf+"-----"+_limitesup);
			List<Result> results = _covidAnalyzerTool.testReader.readResultsFromFile(_resultFiles.get(i));
			for (Result result : results) {
				_covidAnalyzerTool.resultAnalyzer.addResult(result);
			}
			_covidAnalyzerTool.amountOfFilesProcessed.incrementAndGet();
		}
	}
}
