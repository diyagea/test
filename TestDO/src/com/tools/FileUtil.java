package com.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Random;

public class FileUtil {

	private static SimpleDate sd = new SimpleDate();

	/***************************************************************************
	 * 创建空白文件
	 * 
	 * @param fileName
	 *            文件名
	 * @param dir
	 *            保存文件的目录
	 * @return
	 */
	public static File createNewFile(String fileName, String dir) {
		File dirs = new File(dir);
		// 看文件夹是否存在，如果不存在新建目录
		if (!dirs.exists())
			dirs.mkdirs();
		// 拼凑文件完成路径
		File file = new File(dir + File.separator + fileName);
		try {
			// 判断是否有同名名字，如果有同名文件加随机数改变文件名
			while (file.exists()) {
				int ran = getRandomNumber();
				String prefix = getFileNamePrefix(fileName);
				String suffix = getFileNameSuffix(fileName);
				String name = prefix + ran + "." + suffix;
				file = new File(dir + File.separator + name);
			}
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	public static File fileCreate(String fileFoder) {
		File foder = new File(fileFoder);
		File file = new File(fileFoder);
		// 如果文件夹不存在，则创建文件夹
		if (foder.exists() == false) {
			foder.mkdirs();// 多级目录
			// foder.mkdir();//只创建一级目录
		}

		// 如果文件不存在，则创建文件
		if (file.exists() == false) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * 获得随机数
	 * 
	 * @return
	 */

	private static int getRandomNumber() {

		Random random = new Random(new Date().getTime());
		return Math.abs(random.nextInt());
	}

	/**
	 * 分割文件名 如a.txt 返回 a
	 * 
	 * @param fileName
	 * @return
	 */

	private static String getFileNamePrefix(String fileName) {
		int dot = fileName.lastIndexOf(".");
		return fileName.substring(0, dot);
	}

	/**
	 * 获得文件后缀
	 * 
	 * @param fileName
	 * @return
	 */
	private static String getFileNameSuffix(String fileName) {
		int dot = fileName.lastIndexOf(".");
		return fileName.substring(dot + 1);
	}

	// /**
	// * 上传文件
	// *
	// * @param file
	// * @param dir
	// * @return
	// */
	// public static String uploadFile(File file, String dir) {
	// // 获得文件名
	// String fileName = "";
	// if (file != null) {
	// fileName = file.getName();
	// InputStream in = null;
	// OutputStream out = null;
	// try {
	// in = new BufferedInputStream(new FileInputStream(file));// 构造输入流
	// File f = createNewFile(fileName, dir);
	// fileName = f.getName();// 获取更改后名字
	// // System.out.println(f.getName());
	// out = new BufferedOutputStream(new FileOutputStream(f));// 构造文件输出流
	// byte[] buffered = new byte[8192];// 读入缓存
	// int size = 0;// 一次读到的真实大小
	// while ((size = in.read(buffered, 0, 8192)) != -1) {
	// out.write(buffered, 0, size);
	// }
	// out.flush();
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// } finally {
	// try {
	// if (in != null)
	// in.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// try {
	// if (out != null)
	// out.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// return fileName;
	// }

	// 删除指定文件夹下所有文件
	public void deleteFile(File oldPath) {
		if (oldPath.isDirectory()) {
			File[] files = oldPath.listFiles();
			for (File file : files) {
				deleteFile(file);
			}
		} else {
			oldPath.delete();
		}
	}

	/**
	 * 创建固定后缀的文件
	 * 
	 * @param file
	 * @param dir
	 * @param fileType
	 * @return
	 * @return String
	 */
	public static String createFile(File file, String dir, String fileType) {

		InputStream is = null;
		OutputStream os = null;

		// 设置目标文件 创建新的文件名已时间戳命名
		String newFileName = sd.getSimpleDateYMDHMSS() + fileType;
		File toFile = FileUtil.createNewFile(newFileName, dir);
		try {
			// 基于excelFile创建一个文件输入流
			is = new FileInputStream(file);

			// 创建一个输出流
			os = new FileOutputStream(toFile);
			// 设置缓存
			byte[] buffer = new byte[1024];
			int length = 0;
			// 读取myFile文件输出到toFile文件中
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
			// 关闭输出流
			os.close();
			// 关闭输入流
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
		return toFile.getAbsolutePath();
	}
}