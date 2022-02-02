package com.ma.springboot.verizone.code;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.ma.springboot.verizone.code.compute.CaculationUtil;
import com.ma.springboot.verizone.code.data.InputDataUtil;
import com.ma.springboot.verizone.code.data.NumberPairDTO;

@SpringBootApplication
public class SpringBootVerizoneCodeApplication {
	@Autowired
	private InputDataUtil inputDataUtil;

	@Autowired
	private CaculationUtil caculationUtil;

	private String equationStr;
	private List<NumberPairDTO> numPairList;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootVerizoneCodeApplication.class, args);
	}

	@PostConstruct
	public void start() {
		numPairList = this.getPairedNumberList();
		equationStr = this.readConsoleLine();
		double mean = calculateMean();
		System.out.println("Mean: " + mean);
	}

	private List<NumberPairDTO> getPairedNumberList() {
		return inputDataUtil.getPairedNumberList();
	}

	private String readConsoleLine() {
		return inputDataUtil.readConsoleLine();
	}

	private double calculateMean() {
		int[] xValueArray = inputDataUtil.getIntParamArray('x');
		int[] yValueArray = inputDataUtil.getIntParamArray('y');
		Optional<String> optEquation = Optional.of(equationStr);
		double[] yPlamArray = caculationUtil.caculateDataArrayWithEquation(xValueArray, optEquation);
		double[] deltaArray = caculationUtil.caculateDeltaSquareArray(yValueArray, yPlamArray);
		double sum = DoubleStream.of(deltaArray).sum();
		double mean = sum / deltaArray.length;
		System.out.println("Sum: " + sum);

		return mean;
	}
}
