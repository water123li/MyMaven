package cn.shaviation.mymaven.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;

/**
 * 压缩、解压工具类
 * 
 * @author rli
 *
 */
public abstract class CompressUtil {

	/**
	 * 将一个文件打包成ZIP格式
	 * 
	 * @param zipFile
	 *            zip文件路径
	 * @param sourceFilefolder
	 *            文件夹的路径
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void compressZipFile(File zipFile, File sourceFilefolder) throws FileNotFoundException, IOException {
		OutputStream outputStream = new FileOutputStream(zipFile);
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
		ZipArchiveOutputStream zipArchiveOutputStream = new ZipArchiveOutputStream(bufferedOutputStream);
		zipArchiveOutputStream.setEncoding("GBK");
		packFileToZip(zipArchiveOutputStream, sourceFilefolder, "");
		zipArchiveOutputStream.flush();
		zipArchiveOutputStream.close();
		bufferedOutputStream.flush();
		bufferedOutputStream.close();
		outputStream.flush();
		outputStream.close();
	}

	/**
	 * 解压zip文件
	 * 
	 * @param zipFile
	 *            待解压文件路径
	 * @param unZipFolder
	 *            解压后的文件路径
	 * @throws Exception
	 */
	public static void unZipFile(File zipFile, File unZipFolder) throws Exception {
		ZipFile compressedFile = null;
		try {
			compressedFile = new ZipFile(zipFile, "GBK");
			FileUtils.forceMkdir(unZipFolder);
			Enumeration<ZipArchiveEntry> zipArchiveEntrys = compressedFile.getEntries();
			while (zipArchiveEntrys.hasMoreElements()) {
				ZipArchiveEntry zipArchiveEntry = zipArchiveEntrys.nextElement();
				if (zipArchiveEntry.isDirectory()) {
					FileUtils.forceMkdir(new File(unZipFolder, zipArchiveEntry.getName()));
				} else {
					File file = new File(unZipFolder, zipArchiveEntry.getName());
					FileUtils.forceMkdir(file.getParentFile());
					file.setWritable(true);
					FileOutputStream fos = new FileOutputStream(file);
					InputStream is = compressedFile.getInputStream(zipArchiveEntry);
					IOUtils.copy(is, fos);
					is.close();
					fos.close();
				}
			}
			compressedFile.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("解压失败！");
		} finally {
			if (compressedFile != null) {
				try {
					compressedFile.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}

	/**
	 * 解压rar文件
	 * 
	 * @param zipFile
	 * @param unRarFolder
	 * @param winrarExePath
	 * @throws Exception
	 */
	public static void unRarFile(File rarFile, File unRarFolder, String winrarExePath) throws Exception {
		int count = 0;
		try {
			unRarFolder.mkdirs();
			String cmdDir = winrarExePath;
			String exeStr = "cmd.exe /C WinRAR.exe";
			String[] cmdStr = new String[]{exeStr + " x -r -p -o+ -inul " + " \"" + rarFile.getAbsolutePath() + "\" \"" + unRarFolder.getAbsolutePath() + "\""};
			System.out.println(cmdStr[0]);
			String batName = "temp.bat";
			FileWriter writer = new FileWriter(new File(cmdDir, batName).getAbsolutePath());
			for (int i = 0; i < cmdStr.length; i++) {
				writer.write(cmdStr[i] + "\r\n");
			}
			writer.close();
			Process process = Runtime.getRuntime().exec("cmd.exe /C" + batName, null, new File(cmdDir));
			InputStreamReader isr = new InputStreamReader(process.getInputStream());
			BufferedReader bf = new BufferedReader(isr);
			String line = null;
			while ((line = bf.readLine())!= null) {
				line = line.trim();
			}
			bf.close();
			count = process.waitFor();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("解压缩RAR失败！");
		}
		System.out.println(count);
		if (count != 0) {
			throw new Exception("解压缩RAR失败！");
		}
	}

	/**
	 * 递归调用，将文件压缩至zip包中
	 * @param zipArchiveOutputStream	zip档案输出流
	 * @param sourceFilefolder			文件夹路径
	 * @param relativePath				zip文件中的相对路径
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void packFileToZip(ZipArchiveOutputStream zipArchiveOutputStream, File sourceFilefolder, String relativePath) throws FileNotFoundException, IOException{
		File[] files = sourceFilefolder.listFiles();
		if (files == null || files.length < 1) {
			zipArchiveOutputStream.putArchiveEntry(new ZipArchiveEntry(relativePath));
			zipArchiveOutputStream.closeArchiveEntry();
			return;
		} 
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				packFileToZip(zipArchiveOutputStream, files[i], relativePath + files[i].getName() + File.separator);
			} else {
				zipArchiveOutputStream.putArchiveEntry(new ZipArchiveEntry(relativePath + files[i].getName()));
				FileInputStream source = new FileInputStream(files[i].getAbsolutePath());
				IOUtils.copy(source, zipArchiveOutputStream);
				source.close();
				zipArchiveOutputStream.closeArchiveEntry();
			}
		}
	}

	public static void main(String[] args) throws Exception{
		compressZipFile(new File("E:\\test6\\a.zip"), new File("E:\\test6\\a"));
	}
}
