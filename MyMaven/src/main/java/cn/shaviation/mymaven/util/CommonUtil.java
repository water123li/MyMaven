package cn.shaviation.mymaven.util;

import java.io.File;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang3.ArrayUtils;

public class CommonUtil {

	public static <T> T getOne(Collection<T> collection) {
		if (collection == null || collection.isEmpty()) {
			return null;
		}
		return collection.iterator().next();
	}
	
	public static Collection<?> convertArrayToCollection(Object arrayObject){
		if (arrayObject == null || arrayObject.getClass().isArray()) {
			throw new IllegalArgumentException();
		}
		try {
			if (arrayObject.getClass().getComponentType().isPrimitive()) {
				return Arrays.asList((Object[])MethodUtils.invokeExactMethod(ArrayUtils.class, "toObjct", arrayObject));
			} else {
				return Arrays.asList((Object[])arrayObject);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
	/**
	 * 格式化数据，小数保留3位
	 * @param originalData
	 * @return
	 */
	public static String formatData(Object originalData){
		String convertedValue = null;
		if (originalData == null) {
			return null;
		}
		if (originalData instanceof Double || originalData instanceof Float) {
			NumberFormat format = NumberFormat.getNumberInstance();
			format.setGroupingUsed(false);
			format.setMinimumFractionDigits(0);
			format.setMaximumFractionDigits(3);
			format.setMinimumIntegerDigits(1);
			format.setRoundingMode(RoundingMode.HALF_UP);
		}else {
			convertedValue = originalData.toString();
		}
		return convertedValue;
	}
	/**
	 * 获取临时文件路径
	 * @return	临时文件路径
	 * @throws Exception
	 */
	public static String getTempDir() throws Exception{
		String tempDir = System.getProperty("java.io.tmpdir");
		if (tempDir == null) {
			tempDir = System.getenv("TMP");
		}
		tempDir= new File(tempDir).getCanonicalPath();
		if (!tempDir.endsWith("\\")) {
			tempDir = tempDir + "\\";
		}
		return tempDir;
	}
}
