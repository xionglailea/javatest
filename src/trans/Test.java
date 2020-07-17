package trans;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;

public class Test {

	public static void main(String[] args) throws ClassNotFoundException, IOException  {
		// TODO Auto-generated method stub
//		first();
//		transDirectory("C:\\Users\\arctest\\Desktop\\action","C:\\Users\\arctest\\Desktop\\transaction");
        System.out.println(~0);
    }
	public static void copyFile(File f, String outputDirectory) throws IOException{
//		File inputFile = new File("C:\\CastSkill.cs");
		String className = f.getName().split("\\.")[0];
		FileReader inputReader = new FileReader(f);
		BufferedReader inputBuffer = new BufferedReader(inputReader);
		//输出文件
		String outputFileName = outputDirectory + "\\" + f.getName();
		File outputFile = new File(outputFileName);
		if(!outputFile.exists()){
			outputFile.createNewFile();
		}
		BufferedWriter outPutBuffer = new BufferedWriter(new FileWriter(outputFile));
		String content;
		int keep = 0;
		int bracketNum = 0;
		int delete = 0;
		while((content = inputBuffer.readLine())!=null){
			if(content.contains("using") && !content.contains("UnityEngine")){
				continue;
			}
			if(keep==0 && content.contains(className) && !content.contains("class")){
				keep = 1;
				outPutBuffer.write(content+"\n");
				continue;
			}
			if(keep == 1){ //保留构造函数
				if(content.trim().equals("{")){
					bracketNum++;
				}
				if(content.trim().equals("}")){
					bracketNum--;
				}
				if(bracketNum == 0){
					keep = -1;
					outPutBuffer.write(content+"\n");
					continue;
				}
			}
			if(keep == -1){ //非构造函数的函数体不保存，只保存声明
				if(content.trim().equals("{")){
					delete++;
					if(delete == 1){
						outPutBuffer.write(content+"\n");
					}
				}
				if(content.trim().equals("}")){
					delete--;
				}
			}
			if(delete > 0){
				continue;
			}
			outPutBuffer.write(content+"\n");
		}
		inputReader.close();
		inputBuffer.close();
		outPutBuffer.close();
	}
	public static void transDirectory (String filePath, String outputPath) throws IOException{
		File fileDir = new File(filePath);
		File outDir = new File(outputPath);
		if(!outDir.exists()){
			outDir.mkdirs();
		}
		if(fileDir.isDirectory()){
			for(File f : fileDir.listFiles()){
				copyFile(f, outputPath);
			}
		}else {
			System.out.println("文件夹路径不合法");
		}
		System.out.println("OK!");
	}
	
//	public void trans() throws ClassNotFoundException{
//		this.getClass().getClassLoader().loadClass("trans.CastSkill");
//		Class<?> c = Class.forName("CastSkill");
//		Method[] methods = c.getMethods();
//		for(Method method : methods){
//			System.out.println(method.toString());
//		}
//	}
}
