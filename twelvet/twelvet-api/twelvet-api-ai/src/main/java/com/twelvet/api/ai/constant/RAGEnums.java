package com.twelvet.api.ai.constant;

/**
 * <p>
 * RAG枚举
 * <p>
 *
 * @since 2024/11/26
 */
public class RAGEnums {

	/**
	 * 储存向量metadata枚举
	 */
	public enum VectorMetadataEnums {

		MODEL_ID("modelId", "知识库ID"), DOC_ID("docId", "文档ID"), SLICE_ID("sliceId", "切片ID"),

		;

		private final String code;

		private final String desc;

		VectorMetadataEnums(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public String getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}

	}

}
