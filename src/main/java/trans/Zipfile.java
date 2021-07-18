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

public class Zipfile {  //ѹ���ͽ�ѹ�ļ�

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

	public static void dozip(String source, String dest) throws IOException {  //����ѹ��ָ���ļ�������������ļ����ļ���
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
			zipfile(sourceFile, basePath, zos); // ѹ���ļ������ļ���
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
					pathName = file.getPath().substring(basePath.length() + 1) + "/";// ��Ŀ¼��·��,ע���á�/����β����ʾΪĿ¼(��ȡʱ�ж��Ƿ�Ϊ�ļ��о��Ǹ�������ж�)
					zos.putNextEntry(new ZipEntry(pathName));
					System.out.println("directory: "+ pathName);
					zipfile(file, basePath, zos);
				} else {
					pathName = file.getPath().substring(basePath.length() + 1); // �õ��ļ���Ը�Ŀ¼��·��
					is = new FileInputStream(file);
					BufferedInputStream bis = new BufferedInputStream(is);
					zos.putNextEntry(new ZipEntry(pathName));
					System.out.println("file: " + pathName);
					while ((length = bis.read(buff)) > 0) {
						zos.write(buff, 0, length); //д��ѹ���ļ��У��������½���һ��Entry�У�
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
				String name = ze.getName(); //����õ����ļ����������source��Ŀ¼��
				if (ze.isDirectory()) { //����ж���һ��Ŀ¼����ôҪ�½�һ����Ŀ¼����ֹ�������ļ���ʱ���Ҳ���·��
					File sub = new File(destdirectory + "\\" + name);
					if (!sub.exists()) {
						sub.mkdirs();
					}
					System.out.println("directory: " + name);
					continue;
				}
				File child = new File(destdirectory, name); // ��ѹ�ļ�����ͬ����Ŀ¼
				System.out.println("file: " + name);
				FileOutputStream os = new FileOutputStream(child);
				while ((length = zis.read(buff)) > 0) { // zip�е��ļ���һ����entry���ֵģ�ÿ�ζ�һ��entry
					os.write(buff, 0, length);
				}
				os.flush();
				os.close();
			}
		}
		System.out.println("ok!");
	}

}
