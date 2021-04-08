package com.burcuozel.statistics.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Statistics {

	private BigDecimal sum;
	private BigDecimal min;
	private BigDecimal max;
	private long count;
	private LocalDateTime timestamp;

	public Statistics() {
		sum = new BigDecimal(0);
	}

	public void accept(BigDecimal value) {
		++count;
		sum = sum.add(value);
		min = min == null ? value : min.min(value);
		max = max == null ? value : max.max(value);
	}

	public void combine(Statistics other) {
		count += other.count;
		sum = sum.add(other.sum);
		min = min == null ? other.min : min.min(other.min);
		max = max == null ? other.max : max.max(other.max);
	}

	public BigDecimal getMin() {
		if (count == 0) {
			return new BigDecimal(0);
		}

		return min;
	}

	public BigDecimal getMax() {
		if (count == 0) {
			return new BigDecimal(0);
		}

		return max;
	}

	public BigDecimal getAvg() {
		if (count == 0) {
			return new BigDecimal(0);
		}

		return sum.divide(new BigDecimal(count), 2, RoundingMode.HALF_UP);
	}
}