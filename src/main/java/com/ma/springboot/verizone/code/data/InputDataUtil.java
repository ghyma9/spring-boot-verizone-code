package com.ma.springboot.verizone.code.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InputDataUtil {
	@Value("${input.file.path}")
	private String filePath;

	@Value("${input.file.name}")
	private String fileName;

	private List<NumberPairDTO> numPairList;

	@PostConstruct
	public void init() {
		System.out.println(filePath);
		System.out.println(fileName);
	}

	public List<NumberPairDTO> getPairedNumberList() {
		String fullFileName = filePath.replace("%systemdrive%", System.getenv("SystemDrive")) + fileName;
		System.out.println(fullFileName);

		numPairList = new ArrayList<NumberPairDTO>();
		try (Scanner sc = new Scanner(new File(fullFileName))) {
			sc.next();	// Skip the header
			while (sc.hasNext()) {
				String[] numlist = sc.next().split(",");
				int[] pairArray = Stream.of(numlist).mapToInt(Integer :: parseInt).toArray();
				NumberPairDTO dto = new NumberPairDTO();
				dto.setX(pairArray[0]);
				dto.setY(pairArray[1]);
				numPairList.add(dto);
				System.out.println(dto.toString());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		return numPairList;
	}
	
	public String readConsoleLine() {
		String rstr = null;

		displayConsoleNotice();
		BufferedReader breader = new BufferedReader(new InputStreamReader(System.in));
		try {
			rstr = breader.readLine();
			System.out.println("Equation is: " + rstr);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return rstr;
	}

	private void displayConsoleNotice() {
		System.out.println("Enter an equation of the format 'c op Nx'");
		System.out.println("Where:");
		System.out.println("c - can be possible or negative");
		System.out.println("N - is always positive");
		System.out.println("op - is either + or -");
		System.out.println("Example: \"11.3 + 1.35x\" or \"-10 - 0.7x\"");
		System.out.print("Enter the equation: ");
	}

	public int[] getIntParamArray(char xy) {
		if (numPairList == null || numPairList.size() == 0) {
			return null;
		}
		int[] intParamArray = new int[numPairList.size()];

		int ind = 0;
		for (NumberPairDTO dto : numPairList) {
			if (xy == 'x') {
				intParamArray[ind++] = dto.getX();
			}
			else {
				intParamArray[ind++] = dto.getY();
			}
		}

		return intParamArray;
	}
}
