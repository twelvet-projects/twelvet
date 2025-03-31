package com.twelvet.api.ai.constant;

/**
 * <p>
 * 大模型相关枚举
 * <p>
 *
 * @since 2024/12/20
 */
public class ModelEnums {

	/**
	 * 模型供应商枚举
	 */
	public enum ModelSupplierEnums {

		KNOWLEDGE_ID("knowledgeId", "知识库ID"), DOC_ID("docId", "文档ID"), SLICE_ID("sliceId", "切片ID"),

		;

		private final String code;

		private final String desc;

		ModelSupplierEnums(String code, String desc) {
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
	 * 模型类型枚举
	 */
	public enum ModelTypeEnums {

		USER("USER", "用户"), AI("AI", "AI")

		;

		private final String code;

		private final String desc;

		ModelTypeEnums(String code, String desc) {
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
	 * AI MCP 类型枚举
	 */
	public enum McpTypeEnums {

		STDIO("STDIO", "STDIO"), SSE("SSE", "SSE")

		;

		private final String code;

		private final String desc;

		McpTypeEnums(String code, String desc) {
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
	 * AI MCP 命令枚举
	 */
	public enum McpCommandEnums {

		NPX("npx", "npx")

		;

		private final String code;

		private final String desc;

		McpCommandEnums(String code, String desc) {
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
