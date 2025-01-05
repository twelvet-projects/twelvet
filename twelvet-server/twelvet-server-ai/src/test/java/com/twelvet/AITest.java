package com.twelvet;

import com.twelvet.api.ai.domain.dto.AiDocDTO;
import com.twelvet.framework.utils.JacksonUtils;
import com.twelvet.server.ai.AiApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RAtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;

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

	@Autowired
	private StreamBridge streamBridge;

	/**
	 * 发送MQ消息测试
	 */
	@Test
	public void sendMqTest() {
		AiDocDTO aiDocDTO = new AiDocDTO();
		aiDocDTO.setContent("测试消息");
		streamBridge.send("addRAGDocChannel-out-0", MessageBuilder.withPayload("消息测试").build());
		log.info("消息发送成功");
	}

	/**
	 * 测试文件识别
	 */
	@Test
	public void tikaTest() {
		TikaDocumentReader tikaDocumentReader = new TikaDocumentReader("https://static.twelvet.cn/ai/video.mp4");
		List<Document> read = tikaDocumentReader.get();
		read.forEach(document -> {
			log.info("分析成功");
			log.info(document.getContent());
			log.info("文件名称：{}", document.getMetadata().get("source"));
		});

	}

}
