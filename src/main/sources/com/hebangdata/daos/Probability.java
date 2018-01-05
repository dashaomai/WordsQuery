package com.hebangdata.daos;

/**
 * 词语的出现次数实体类
 */
public class Probability {
	public String initial;
	public String letter;
	public java.math.BigDecimal count_0;
	public java.math.BigDecimal count_1;
	public java.math.BigDecimal count_2;
	public java.math.BigDecimal count_3;

	public Probability(
			final String initial, final String letter,
			final java.math.BigDecimal count_0,
			final java.math.BigDecimal count_1,
			final java.math.BigDecimal count_2,
			final java.math.BigDecimal count_3
	) {
		this.initial = initial;
		this.letter = letter;
		this.count_0 = count_0;
		this.count_1 = count_1;
		this.count_2 = count_2;
		this.count_3 = count_3;
	}

	public Probability(
			final Character initial, final Character letter,
			final int count_0,
			final int count_1,
			final int count_2,
			final int count_3
	) {
		this.initial = initial.toString();
		this.letter = letter.toString();
		this.count_0 = new java.math.BigDecimal(count_0);
		this.count_1 = new java.math.BigDecimal(count_1);
		this.count_2 = new java.math.BigDecimal(count_2);
		this.count_3 = new java.math.BigDecimal(count_3);
	}
}
