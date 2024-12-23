package com.twelvet.api.ai.domain.ocr;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.swagger.v3.oas.annotations.media.Schema;
import jdk.jfr.Description;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 发票识别OCR
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024/12/23
 */
@Schema(description = "发票识别OCR")
public record InvoiceOCR(

		@JsonPropertyDescription("发票代码") @Schema(description = "发票代码") @JsonProperty(required = true,
				value = "invoiceNo") String invoiceNo,

		@JsonPropertyDescription("发票金额") @Schema(description = "发票金额") @JsonProperty(required = true,
				value = "amount") BigDecimal amount) {
}
