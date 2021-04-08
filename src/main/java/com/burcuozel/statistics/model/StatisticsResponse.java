package com.burcuozel.statistics.model;

import java.math.RoundingMode;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StatisticsResponse {

	private String sum;
	private String avg;
	private String max;
	private String min;
	private long count;

	public StatisticsResponse(Statistics statistics) {
		count = statistics.getCount();
		sum = statistics.getSum().setScale(2, RoundingMode.HALF_UP).toString();
		avg = statistics.getAvg().setScale(2, RoundingMode.HALF_UP).toString();
		max = statistics.getMax().setScale(2, RoundingMode.HALF_UP).toString();
		min = statistics.getMin().setScale(2, RoundingMode.HALF_UP).toString();
	}

}
