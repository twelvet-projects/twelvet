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
	 * 知识库数据来源类型
	 */
	public enum DocSourceTypeEnums {

		INPUT("INPUT", "录入"), UPLOAD("UPLOAD", "上传")

		;

		private final String code;

		private final String desc;

		DocSourceTypeEnums(String code, String desc) {
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

	/**
	 * 储存向量metadata枚举
	 */
	public enum VectorMetadataEnums {

		KNOWLEDGE_ID("knowledgeId", "知识库ID"), DOC_ID("docId", "文档ID"), SLICE_ID("sliceId", "切片ID"),

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

	/**
	 * 聊天用户类型
	 */
	public enum UserTypeEnums {

		USER("USER", "用户"), AI("AI", "AI")

		;

		private final String code;

		private final String desc;

		UserTypeEnums(String code, String desc) {
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

	/**
	 * 聊天内容类型
	 */
	public enum ChatTypeEnums {

		TEXT("TEXT", "文本"), IMAGES("IMAGES", "图片")

		;

		private final String code;

		private final String desc;

		ChatTypeEnums(String code, String desc) {
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
