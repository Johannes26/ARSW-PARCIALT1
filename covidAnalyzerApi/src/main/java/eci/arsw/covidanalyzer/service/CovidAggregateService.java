package eci.arsw.covidanalyzer.service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;

import eci.arsw.covidanalyzer.model.Result;
import eci.arsw.covidanalyzer.model.ResultType;

@Service
public class CovidAggregateService implements ICovidAggregateService {

	private List<Result> results = new CopyOnWriteArrayList<>();

	public CovidAggregateService() {
		results.add(new Result("johan", ResultType.TRUE_POSITIVE));
	}

	@Override
	public void aggregateResult(Result result, ResultType type) throws CovidAggregateException {
		int index = 0;
		for (Result r : results) {
			if (r.getFirstName().equals(result.getFirstName())) {
				throw new CovidAggregateException("user already exits");
			}
			index++;
		}
		Result foundResult = new Result(result.getFirstName(), type);
		results.add(foundResult);
	}

	@Override
	public List<Result> getResult(ResultType type) {
		List<Result> resultsbytype = new CopyOnWriteArrayList<>();
		for (Result r : results) {
			if (r.getType().equals(type)) {
				resultsbytype.add(r);
			}
		}
		return resultsbytype;
	}

	@Override
	public void upsertPersonWithMultipleTests(UUID id, ResultType type) throws CovidAggregateException {
		Result foundResult = null;
		int index = 0;
		for (Result r : results) {
			System.out.println("---------------"+id.toString()+"-------"+r.getId());
			if (r.getId().toString().equals(id.toString())) {
				foundResult = r;
				break;
			}
			index++;
		}
		if (foundResult == null) {
			throw new CovidAggregateException("Not found user");
		}
		results.remove(index);
		foundResult.setType(type);
		foundResult.setTestAmount(foundResult.getTestAmount() + 1);
		results.add(foundResult);
	}

}
