package com.twelvet.framework.utils;

import com.twelvet.framework.utils.exception.TWTUtilsException;

import java.math.BigDecimal;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 阿拉伯数字与中文之间的转换
 */
public class ArabicNumeralsUtils {

	public ArabicNumeralsUtils() {
		throw new TWTUtilsException("This is a utility class and cannot be instantiated");
	}

	/**
	 * 中文数字
	 */
	private static final String[] CN_NUM = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };

	/**
	 * 中文数字单位
	 */
	private static final String[] CN_UNIT = { "", "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千" };

	/**
	 * 特殊字符：负
	 */
	private static final String CN_NEGATIVE = "负";

	/**
	 * 特殊字符：点
	 */
	private static final String CN_POINT = "点";

	/**
	 * 中文数字转阿拉伯数字
	 * @param chineseNumber 中文数字
	 * @return 阿拉伯数字
	 */
	public static int chineseTransIntNumber(String chineseNumber) {
		int result = 0;
		// 存放一个单位的数字如：十万
		int temp = 1;
		// 判断是否有chArr
		int count = 0;
		char[] cnArr = new char[] { '一', '二', '三', '四', '五', '六', '七', '八', '九' };
		char[] chArr = new char[] { '十', '百', '千', '万', '亿' };
		for (int i = 0; i < chineseNumber.length(); i++) {
			// 判断是否是chArr
			boolean b = true;
			char c = chineseNumber.charAt(i);
			// 非单位，即数字
			for (int j = 0; j < cnArr.length; j++) {
				if (c == cnArr[j]) {
					// 添加下一个单位之前，先把上一个单位值添加到结果中
					if (0 != count) {
						result += temp;
						count = 0;
					}
					// 下标+1，就是对应的值
					temp = j + 1;
					b = false;
					break;
				}
			}
			// 单位{'十','百','千','万','亿'}
			if (b) {
				for (int j = 0; j < chArr.length; j++) {
					if (c == chArr[j]) {
						switch (j) {
							case 0 -> temp *= 10;
							case 1 -> temp *= 100;
							case 2 -> temp *= 1000;
							case 3 -> temp *= 10000;
							case 4 -> temp *= 100000000;
							default -> {
							}
						}
						count++;
					}
				}
			}
			// 遍历到最后一个字符
			if (i == chineseNumber.length() - 1) {
				result += temp;
			}
		}
		return result;
	}

	/**
	 * int 转 中文数字 支持到int最大值
	 * @param intNum 要转换的整型数
	 * @return 中文数字
	 */
	public static String intTransIntChineseNumber(int intNum) {
		StringBuilder stringBuffer = new StringBuilder();
		boolean isNegative = false;
		if (intNum < 0) {
			isNegative = true;
			intNum *= -1;
		}
		int count = 0;
		while (intNum > 0) {
			stringBuffer.insert(0, CN_NUM[intNum % 10] + CN_UNIT[count]);
			intNum = intNum / 10;
			count++;
		}

		if (isNegative) {
			stringBuffer.insert(0, CN_NEGATIVE);
		}

		return stringBuffer.toString()
			.replaceAll("零[千百十]", "零")
			.replaceAll("零+万", "万")
			.replaceAll("零+亿", "亿")
			.replaceAll("亿万", "亿零")
			.replaceAll("零+", "零")
			.replaceAll("零$", "");
	}

	/**
	 * bigDecimal 转 中文数字 整数部分只支持到int的最大值
	 * @param bigDecimalNum 要转换的BigDecimal数
	 * @return 中文数字
	 */
	public static String bigDecimalTransIntChineseNumber(BigDecimal bigDecimalNum) {
		if (bigDecimalNum == null) {
			return CN_NUM[0];
		}

		StringBuilder stringBuffer = new StringBuilder();

		// 将小数点后面的零给去除
		String numStr = bigDecimalNum.abs().stripTrailingZeros().toPlainString();

		String[] split = numStr.split("\\.");
		String integerStr = intTransIntChineseNumber(Integer.parseInt(split[0]));

		stringBuffer.append(integerStr);

		// 如果传入的数有小数，则进行切割，将整数与小数部分分离
		if (split.length == 2) {
			// 有小数部分
			stringBuffer.append(CN_POINT);
			String decimalStr = split[1];
			char[] chars = decimalStr.toCharArray();
			for (char aChar : chars) {
				int index = Integer.parseInt(String.valueOf(aChar));
				stringBuffer.append(CN_NUM[index]);
			}
		}

		// 判断传入数字为正数还是负数
		int signum = bigDecimalNum.signum();
		if (signum == -1) {
			stringBuffer.insert(0, CN_NEGATIVE);
		}

		return stringBuffer.toString();
	}

}
