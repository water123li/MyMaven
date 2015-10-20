package cn.shaviation.mymaven.util;

import java.io.File;
import java.io.IOException;

/**
 * 创建文件工具
 * @author Administrator
 *
 */
public class FileUtil {
	/**
	 * 根据目录和文件名创建文件
	 * @param dir	目录路径
	 * @param fileName	文件名
	 * @return
	 * @throws IOException
	 */
	public static File createFile(String dir, String fileName) throws IOException {
		File file = null;
		File fileDir = new File(dir);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
			file = new File(fileDir, fileName);
			file.createNewFile();
		} else {
			file = new File(fileDir, fileName);
			if (file.exists()) {
				System.out.println("文件" + file.getAbsolutePath() + "已存在！");
			} else {
				file.createNewFile();
			}
		}

		return file;
	}

	public static File createFile2(String dirAndfileName) throws IOException {
		File file = new File(dirAndfileName);
		File parentFile = file.getParentFile();
		if (parentFile.exists()) {
			System.out.println("目录" + parentFile.getAbsolutePath() + "已存在！");
			if (file.exists()) {
				System.out.println("文件" + file.getAbsolutePath() + "已存在！");
			} else {
				System.out.println("文件" + file.getAbsolutePath() + "不存在，开始创建！");
				file.createNewFile();
			}
		} else {
			System.out.println("目录" + parentFile.getAbsolutePath() + "不存在，开始创建！");
			parentFile.mkdirs();
			file.createNewFile();
		}

		return file;
	}
	
	
	public static void main(String[] args) throws IOException {

		File file = createFile2("D:/xx/xx.txt");

		System.out.println(file.isFile());
		System.out.println(file.getAbsolutePath());
	}
}
