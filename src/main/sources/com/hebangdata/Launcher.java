package com.hebangdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Launcher {
	private static final Logger log = LoggerFactory.getLogger("Launcher");

	public static void main(String[] args) throws IOException {
		// 获得解析后的语料速查表
		final Map<Character, Map<Character, AtomicInteger[]>> map = parseWords("assets/大词库20171225.txt", "assets/所有去重.grouped.txt", 3);

		// 循环读输入的查询串
		final BufferedReader inputStream = new BufferedReader(new InputStreamReader(System.in));
		String input;
		do {
			log.info("请输入要查询的二字词：");
			input = inputStream.readLine();

			if (input.equals("quit")) {
				log.info("收到退出指令。");
				break;
			}
			else {
				final char[] bytes = input.toCharArray();
				if (bytes.length > 1) {
					final int[] result = query(map, bytes[0], bytes[1]);
					if (null == result) {
						log.warn("查询词：{} 没有结果。", input);
					} else {
						log.info("查询词：{} 的结果是：{}", input, result);
					}
				}
			}
		}
		while (input != null);

		log.info("感谢您的使用，再见！");
	}

	// 读取词库，构造词库首字母的有效 Set
	private static Set<Character> parseInitial(final String path) throws IOException {
		final long begin = System.currentTimeMillis();

		final Set<Character> availableInitial = Collections.synchronizedSet(new HashSet<>());

		final Stream<String> stream = Files.lines(Paths.get(path));

		stream.forEach(line -> {
			boolean isInitial = true;
			for (final char chr : line.toCharArray()) {
				if (',' == chr) {
					isInitial = true;
				} else if (isInitial && isHan(chr)) {
					isInitial = false;

					availableInitial.add(chr);
				}
			}
		});

		stream.close();

		final long end = System.currentTimeMillis();

		log.info("读取词库首字母建模，获得了 {} 个有效首字母，耗时：{} 秒。", availableInitial.size(), (end - begin) / 1000L);

		return availableInitial;
	}

	private static boolean isHan(final char chr) {
		final Character.UnicodeScript us = Character.UnicodeScript.of(chr);

		return Character.UnicodeScript.HAN == us || Character.UnicodeScript.LATIN == us;
	}

	// 读取完整的语料库，构造整个语料统计搜索的 HashMap
	private static Map<Character, Map<Character, AtomicInteger[]>> parseWords(final String initialPath, final String wordsPath, final int depth) throws IOException {
		final Set<Character> availableInitial = parseInitial(initialPath);

		final long begin = System.currentTimeMillis();

		final Map<Character, Map<Character, AtomicInteger[]>> wordsNodeMap = new ConcurrentHashMap<>();

		final Stream<String> stream = Files.lines(Paths.get(wordsPath)).parallel();
		stream.forEach(line -> {
			// 为空或者长度不足，都不予处理
			if (null == line || line.length() < 2) return;

			final char[] chars = line.toCharArray();
			for (int i = 0, m = chars.length - 1; i<m; i++) {
				final char initial = chars[i];

				// 只有当词库中有该首字母，才允许进一步计算其节点
				if (availableInitial.contains(initial)) {
					// 取得首字符对应的字典
					final Map<Character, AtomicInteger[]> wordsMap = wordsNodeMap.computeIfAbsent(initial, kk -> new ConcurrentHashMap<>());

					for (int j = 1, n = Math.min(depth + 2, m - i + 1); j<n; j++) {
						final char letter = chars[i + j];
						final int level = j - 1;

						// 取得之后的单词节点
						final AtomicInteger[] wordsNode = wordsMap.computeIfAbsent(letter, kk -> {
							final AtomicInteger[] node = new AtomicInteger[depth + 1];

							for (int k = 0, l = depth + 1; k < l; k++) {
								node[k] = new AtomicInteger(0);
							}

							return node;
						});

						wordsNode[level].incrementAndGet();
					}
				}
			}
		});

		final long end = System.currentTimeMillis();

		log.info("读取语料库建模，产生了 {} 个有效首字符语料信息，耗时：{} 秒。", wordsNodeMap.size(), (end - begin) / 1000L);

		availableInitial.clear();

		return wordsNodeMap;
	}

	private static int[] query(final Map<Character, Map<Character, AtomicInteger[]>> map, final char initial, final char letter) {
		if (null == map) return null;

		final Map<Character, AtomicInteger[]> wordsMap = map.get(initial);
		if (null == wordsMap) return null;

		final AtomicInteger[] wordsNode = wordsMap.get(letter);
		if (null == wordsNode) return null;

		final int[] result = new int[wordsNode.length];
		for (int i = 0, m = result.length; i<m; i++) {
			result[i] = wordsNode[i].get();
		}

		return result;
	}
}
