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
	public enum ModelProviderEnums {

		DASHSCOPE("dashscope", "阿里云"),

		;

		private final String code;

		private final String desc;

		ModelProviderEnums(String code, String desc) {
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

		LLM("llm", "大语言模型"), TTS("tts", "文字转语音模型"), STT("stt", "语音转文字模型"), EMBEDDING("embedding", "向量模型"),
		RERANKER("reranker", "排序模型"), IMAGE("image", "图片模型"), VIDEO("video", "视频模型"),

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

		NPX("npx", "npx"), UVX("uvx", "uvx"), JAVA("java", "java")

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
