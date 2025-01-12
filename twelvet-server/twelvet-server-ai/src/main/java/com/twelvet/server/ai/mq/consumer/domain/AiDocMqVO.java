package com.twelvet.server.ai.mq.consumer.domain;

import com.twelvet.api.ai.domain.AiDoc;
import com.twelvet.api.ai.domain.AiDocSlice;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.ai.document.Document;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 批量处理添加RAG文档消息VO
 * <p>
 *
 * @since 2025/1/10
 */
public class AiDocMqVO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 知识库文档
	 */
	@Schema(description = "知识库文档")
	private AiDoc aiDoc;

	/**
	 * 知识库文档切片
	 */
	@Schema(description = "知识库文档切片")
	private List<AiDocSlice> aiDocSliceList;

	/**
	 * 知识库文档切片向量
	 */
	@Schema(description = "知识库文档切片向量")
	private List<Document> documentList;

	public AiDoc getAiDoc() {
		return aiDoc;
	}

	public void setAiDoc(AiDoc aiDoc) {
		this.aiDoc = aiDoc;
	}

	public List<AiDocSlice> getAiDocSliceList() {
		return aiDocSliceList;
	}

	public void setAiDocSliceList(List<AiDocSlice> aiDocSliceList) {
		this.aiDocSliceList = aiDocSliceList;
	}

	public List<Document> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<Document> documentList) {
		this.documentList = documentList;
	}

	@Override
	public String toString() {
		return "AiDocMqVO{" + "aiDoc=" + aiDoc + ", aiDocSliceList=" + aiDocSliceList + ", documentList=" + documentList
				+ '}';
	}

}
