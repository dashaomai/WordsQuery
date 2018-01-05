package com.hebangdata.daos

trait ProbabilityMapper {
  def find(initial: String, letter: String): Probability
  def insert(entity: Probability): Int
  def delete(): Unit
}
