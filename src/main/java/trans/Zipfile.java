package trans;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Zipfile {  //压缩和解压文件

	public static void main(String[] args) throws IOException {
		// File files = new File("C:\\zip");
		// String s = file.getPath().substring(files.getPath().length() + 1) +
		// "/";
		// System.out.println(s);
		String s = "C:\\zip";
		String d = "C:\\a.zip";
//		dozip(s, d);
		unzipfile(d, "C:\\a");
	}

	public static void dozip(String source, String dest) throws IOException {  //可以压缩指定文件夹下面的所有文件和文件夹
		File sourceFile = new File(source);
		File zipFile = new File(dest);
		ZipOutputStream zos = null;
		try {
			OutputStream os = new FileOutputStream(zipFile);
			BufferedOutputStream bos = new BufferedOutputStream(os);
			zos = new ZipOutputStream(bos);
			String basePath = null;
			if (sourceFile.isDirectory()) {
				basePath = sourceFile.getPath();
			} else {
				basePath = sourceFile.getParent();
			}
			zipfile(sourceFile, basePath, zos); // 压缩文件或者文件夹
		} finally {
			if (zos != null) {
				zos.closeEntry();
				zos.close();
			}
			System.out.println("OK!");
			// TODO: handle finally clause
		}
	}

	public static void zipfile(File source, String basePath, ZipOutputStream zos) throws IOException {
		File[] files;
		if (source.isDirectory()) {
			files = source.listFiles();
		} else {
			files = new File[1];
			files[0] = source;
		}
		InputStream is = null;
		String pathName;
		int length;
		byte[] buff = new byte[1024];
		try {
			for (File file : files) {
				if (file.isDirectory()) {
					pathName = file.getPath().substring(basePath.length() + 1) + "/";// 子目录的路径,注意用“/”结尾，表示为目录(读取时判断是否为文件夹就是根据这个判断)
					zos.putNextEntry(new ZipEntry(pathName));
					System.out.println("directory: "+ pathName);
					zipfile(file, basePath, zos);
				} else {
					pathName = file.getPath().substring(basePath.length() + 1); // 得到文件相对根目录的路径
					is = new FileInputStream(file);
					BufferedInputStream bis = new BufferedInputStream(is);
					zos.putNextEntry(new ZipEntry(pathName));
					System.out.println("file: " + pathName);
					while ((length = bis.read(buff)) > 0) {
						zos.write(buff, 0, length); //写到压缩文件中，即上面新建的一个Entry中；
					}
				}
			}
		} finally {
			is.close();
			// TODO: handle finally clause
		}
	}

	public static void unzipfile(String source, String destdirectory) throws  IOException {
		File desFile = new File(destdirectory);
		if (!desFile.exists()) {
			desFile.mkdirs();
		}
		ZipEntry ze;
		byte[] buff = new byte[1024];
		int length;
		try (ZipInputStream zis = new ZipInputStream(new FileInputStream(source))) {
			while ((ze = zis.getNextEntry()) != null) {
				String name = ze.getName(); //这里得到的文件名字是相对source根目录的
				if (ze.isDirectory()) { //如果判断是一个目录，那么要新建一个子目录，防止解析子文件的时候找不到路径
					File sub = new File(destdirectory + "\\" + name);
					if (!sub.exists()) {
						sub.mkdirs();
					}
					System.out.println("directory: " + name);
					continue;
				}
				File child = new File(destdirectory, name); // 解压文件到不同的子目录
				System.out.println("file: " + name);
				FileOutputStream os = new FileOutputStream(child);
				while ((length = zis.read(buff)) > 0) { // zip中的文件按一个个entry区分的，每次读一个entry
					os.write(buff, 0, length);
				}
				os.flush();
				os.close();
			}
		}
		System.out.println("ok!");
	}

}
