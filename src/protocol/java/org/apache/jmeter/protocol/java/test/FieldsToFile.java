package org.apache.jmeter.protocol.java.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

public class FieldsToFile extends AbstractJavaSamplerClient {

	private String dir;
	ArrayList<String> paraNames = new ArrayList<String>();
	FileWriter fw = null;
	BufferedWriter bw = null;
	File targetFile = null;
	

	// 这个方法是用来自定义java方法入参的。
	// params.addArgument("num1","");表示入参名字叫num1，默认值为空。
	@Override
	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		params.addArgument("Dir", "");
		params.addArgument("FieldsToWrite", "");
		params.addArgument("SleepTime", "");
		return params;
	}

	// 每个线程测试前执行一次，做一些初始化工作；
	public void setupTest(JavaSamplerContext arg0) {

		this.dir = arg0.getParameter("dir");
		targetFile = new File(this.dir + "packetId_" + Thread.currentThread().getId() + ".csv");
		Iterator<String> paramIterator = arg0.getParameterNamesIterator();
		StringBuffer paramNamesStr = null;
		while(paramIterator.hasNext()){
			paramNamesStr.append(paramIterator.next()).append(",");	
			paraNames.add(paramIterator.next());
		}
		
		File targetDir = new File(dir);
		if (targetDir.isDirectory() & targetDir.exists()) {

			try {
				fw = new FileWriter(this.targetFile, true);
				bw = new BufferedWriter(fw);	
				bw.write(paramNamesStr.toString());
				bw.write("\n");
				bw.flush();

			} catch (IOException e) {
				if (bw != null) {
					try {
						bw.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}

		}

	}

	// 开始测试，从arg0参数可以获得参数值；
	public SampleResult runTest(JavaSamplerContext arg0) {
		
		SampleResult sr = new SampleResult();
		StringBuffer valueBuffer = new StringBuffer();

		try {
			sr.sampleStart();//
			// 通过下面的操作就可以将被测方法的响应输出到Jmeter的察看结果树中的响应数据里面了。

			if(this.bw!=null){
				for(String paraName :paraNames ){
					String paramValue = arg0.getParameter(paraName, "Not Found Value - xuzh");
					valueBuffer.append(paramValue).append(",");
				}
				bw.write(valueBuffer.toString());
				bw.write("\n");
				bw.flush();
			}

			String resultData = "sdfs";
			if (resultData != null && resultData.length() > 0) {
				sr.setResponseData("结果是：" + resultData, null);
				sr.setDataType(SampleResult.TEXT);
			}
			sr.setSuccessful(true);
		} catch (Throwable e) {
			sr.setSuccessful(false);
			e.printStackTrace();
		} finally {
			sr.sampleEnd();// jmeter 结束统计响应时间标记
		}
		return sr;
	}

	// 测试结束时调用；
	public void teardownTest(JavaSamplerContext arg0) {

		if (this.bw != null) {
			try {
				this.bw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {

		System.out.println("xxx");

	}

}
