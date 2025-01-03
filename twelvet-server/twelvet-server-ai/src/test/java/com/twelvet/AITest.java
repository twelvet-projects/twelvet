package com.twelvet;

import com.twelvet.server.ai.AiApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RAtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * <p>
 * AI测试
 * <p>
 *
 * @since 2025/011
 */
@SpringBootTest(classes = AiApplication.class)
public class AITest {

	private final static Logger log = LoggerFactory.getLogger(AITest.class);

	/**
	 * 工作ID获取测试
	 */
	@Test
	public void getWorkIdTest() {
		TikaDocumentReader tikaDocumentReader = new TikaDocumentReader("https://static.twelvet.cn/ai/README_ZH.md",
				ExtractedTextFormatter.builder().withNumberOfTopPagesToSkipBeforeDelete(1).build());
		List<Document> read = tikaDocumentReader.get();
		log.info("分析成功");
	}

}
