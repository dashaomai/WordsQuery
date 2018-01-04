package com.hebangdata.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * MyBaties 操作封装
 */
public class MyBatiesUtils {
  private static final Logger log = LoggerFactory.getLogger("MyBatiesUtils");

  private static final String MYBATIES_CONF = "mybatis.xml";
  private static SqlSessionFactory sqlSessionFactory;

  static {
    try {
      final InputStream inputStream = Resources.getResourceAsStream(MYBATIES_CONF);
      sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    } catch (IOException ex) {
      log.error("Can't locate the config for MyBaties: {}", MYBATIES_CONF);
    }
  }

  public static <T> T Execute(final IMyBatiesExecution<T> execution) {
    return Execute(execution, ExecutorType.SIMPLE);
  }

  /**
   * 执行一个 MyBaties 操作
   * @param execution
   * @param <T>
   * @return
   */
  public static <T> T Execute(final IMyBatiesExecution<T> execution, final ExecutorType execType) {
    try (final SqlSession session = sqlSessionFactory.openSession(execType)) {
      final T result = execution.execute(session);

      session.commit();

      return result;
    }
  }
}
