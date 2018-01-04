package com.hebangdata.utils;

import org.apache.ibatis.session.SqlSession;

public interface IMyBatiesExecution<T> {
  T execute(final SqlSession session);
}
