package com.ma.springboot.verizone.code.compute;

import java.util.Optional;

import org.springframework.stereotype.Component;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

@Component
public class CaculationUtil {

	public double[] caculateDataArrayWithEquation(int[] paramArray, Optional<String> equation) {
		if (paramArray == null || paramArray.length == 0 || equation.isPresent() == false) {
			return null;
		}

		double[] result = new double[paramArray.length];
		int ind = 0;
		for (int x : paramArray) {
			double yPlam = caculatePlamY(equation.get(), x);
			result[ind++] = yPlam;
		}
		return result;
	}

	public double[] caculateDeltaSquareArray(int[] yParamArray, double[] yPlamArray) {
		if (yParamArray == null || yParamArray.length == 0 || 
				yParamArray == null || yParamArray.length == 0) {
			return null;
		}

		double[] result = new double[yPlamArray.length];
		int ind = 0;
		for (int y : yParamArray) {
			double deltaSquare = caculateDeltaSquare(yPlamArray[ind], yParamArray[ind]);
			result[ind++] = deltaSquare;
		}

		return result;
	}

	private double caculatePlamY(String equation, double v) {
		Expression expression = new ExpressionBuilder(equation)
				.variables("x")
				.build()
				.setVariable("x", v);
		double result = expression.evaluate();
		return result;
	}

	private double caculateDeltaSquare(double yPlam, int yParam) {
		return (yPlam - yParam)*(yPlam - yParam);
	}
}
