package com.hebangdata.daos

/**
  * 词语的出现次数实体类
  * @param initial
  * @param letter
  * @param count_0
  * @param count_1
  * @param count_2
  * @param count_3
  */
final case class Probability
(
  initial: String,
  letter: String,
  count_0: java.math.BigDecimal,
  count_1: java.math.BigDecimal,
  count_2: java.math.BigDecimal,
  count_3: java.math.BigDecimal
) {

}

trait ProbabilityMapper {
  def find(initial: String, letter: String): Probability
  def insert(entity: Probability): Int
  def delete(): Unit
}
