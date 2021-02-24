package eci.arsw.covidanalyzer;

import eci.arsw.covidanalyzer.model.Result;
import eci.arsw.covidanalyzer.model.ResultType;
import eci.arsw.covidanalyzer.service.CovidAggregateException;
import eci.arsw.covidanalyzer.service.ICovidAggregateService;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CovidAggregateController {

	@Autowired
	ICovidAggregateService covidAggregateService;


	@RequestMapping(value = "/covid/result/true-positive", method = RequestMethod.POST)
	public ResponseEntity<?> addTruePositiveResult(@RequestBody Result result) {
		try {
			covidAggregateService.aggregateResult(result, ResultType.TRUE_POSITIVE);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (CovidAggregateException e) {
			Logger.getLogger(CovidAggregateController.class.getName()).log(Level.SEVERE, e.getMessage(), e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/covid/result/true-negative", method = RequestMethod.POST)
	public ResponseEntity<?> addTrueNegativeResult(@RequestBody Result result) {
		// TODO
		try {
			covidAggregateService.aggregateResult(result, ResultType.TRUE_NEGATIVE);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (CovidAggregateException e) {
			Logger.getLogger(CovidAggregateController.class.getName()).log(Level.SEVERE, e.getMessage(), e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/covid/result/false-positive", method = RequestMethod.POST)
	public ResponseEntity<?> addFalsePositiveResult(@RequestBody Result result) {
		// TODO
		try {
			covidAggregateService.aggregateResult(result, ResultType.FALSE_POSITIVE);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (

		CovidAggregateException e) {
			Logger.getLogger(CovidAggregateController.class.getName()).log(Level.SEVERE, e.getMessage(), e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/covid/result/false-negative", method = RequestMethod.POST)
	public ResponseEntity<?> addFalseNegativeResult(@RequestBody Result result) {
		// TODO
		try {
			covidAggregateService.aggregateResult(result, ResultType.FALSE_NEGATIVE);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (CovidAggregateException e) {
			Logger.getLogger(CovidAggregateController.class.getName()).log(Level.SEVERE, e.getMessage(), e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	// TODO: Implemente todos los metodos GET que hacen falta.

	@RequestMapping(value = "/covid/result/true-positive", method = RequestMethod.GET)
	public ResponseEntity<?> getTruePositiveResult() {
		return new ResponseEntity<>(covidAggregateService.getResult(ResultType.TRUE_POSITIVE), HttpStatus.ACCEPTED);
	}

	// TODO: Implemente todos los metodos GET que hacen falta.

	@RequestMapping(value = "/covid/result/true-negative", method = RequestMethod.GET)
	public ResponseEntity<?> getTrueNegativeResult() {
		return new ResponseEntity<>(covidAggregateService.getResult(ResultType.TRUE_NEGATIVE), HttpStatus.ACCEPTED);
	}

	// TODO: Implemente todos los metodos GET que hacen falta.

	@RequestMapping(value = "/covid/result/false-positive", method = RequestMethod.GET)
	public ResponseEntity<?> getFalsePositiveResult() {
		return new ResponseEntity<>(covidAggregateService.getResult(ResultType.FALSE_POSITIVE), HttpStatus.ACCEPTED);
	}

	// TODO: Implemente todos los metodos GET que hacen falta.

	@RequestMapping(value = "/covid/result/false-negative", method = RequestMethod.GET)
	public ResponseEntity<?> getFalseNegativeResult() {
		return new ResponseEntity<>(covidAggregateService.getResult(ResultType.FALSE_NEGATIVE), HttpStatus.ACCEPTED);
	}

	// TODO: Implemente el método.

	@RequestMapping(value = "/covid/result/persona/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> savePersonaWithMultipleTests(@PathVariable UUID id, @RequestBody Result r) {
		try {
			covidAggregateService.upsertPersonWithMultipleTests(id, r.getType());
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		} catch (CovidAggregateException e) {
			Logger.getLogger(CovidAggregateController.class.getName()).log(Level.SEVERE, null, e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}