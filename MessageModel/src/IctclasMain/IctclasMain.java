package IctclasMain;
//http://m.blog.csdn.net/blog/michenggang_dami/7315672

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.ictclas4j.bean.SegResult;
import org.ictclas4j.segment.SegTag;
import org.messageEntity.entity;

public class IctclasMain {		

	public static void main(String[] args) throws IOException {		
		ArrayList<entity> modelsEntity = getModels();		
		int quit = 1;
		do{
			System.out.println("请输入短信信息：");		
			Scanner in = new Scanner(System.in);
			String message = in.nextLine();			
			ArrayList<String> splitedMessage = ictclas(message);
			matchModel(modelsEntity, splitedMessage);		
			System.out.println("是否继续：0-否，1-是");
			Scanner in1 = new Scanner(System.in);
			quit = in1.nextInt();			
		}while(quit==1);
		System.out.println("finished");		
	}//main
	
	public static void matchModel(ArrayList<entity> modelsEntity, ArrayList<String> splitedMessage)
	{
		int matchMaxNumber = 0;
		int matchMaxId = -1;
		for(int i=0; i<modelsEntity.size(); i++)
		{
			int matchNumber = 0;
			ArrayList<String> modelWords = modelsEntity.get(i).getSplitedwords();
			for(int j=0; j<splitedMessage.size(); j++)
			{				
				if(modelWords.contains(splitedMessage.get(j)))
					matchNumber++;
			}		
			if(matchNumber>matchMaxNumber)
			{
				matchMaxNumber = matchNumber;
				matchMaxId = i;
			}
		}
		if(matchMaxId > -1)
		{
			String model = modelsEntity.get(matchMaxId).getModel();
			String sender = modelsEntity.get(matchMaxId).getSender();
			String sence = modelsEntity.get(matchMaxId).getSence();
			System.out.println("您所输入短信内容对应的模版为：");
			System.out.println(model);
			System.out.println("短信发送方："+sender);
			System.out.println("短信场景："+sence);
		}
		else
			System.out.println("无匹配模版短信");		
	}
	public static ArrayList<String> ictclas(String message)
	{
		SegTag segTag = new SegTag(1);
		SegResult segResult = segTag.split(message);		
		String classifyContent = segResult.getFinalResult();
		String [] words = classifyContent.split(" ");
		ArrayList<String> wordsList = new ArrayList<String>();
		for(int i=0; i<words.length; i++)
		{
			if(!wordsList.contains(words[i]))
				wordsList.add(words[i]);
		}			
		return wordsList;
	}	
	public static ArrayList<entity> getModels()
	{
		ArrayList<entity> entities = new ArrayList<entity>();		
		String [] models = {"尊敬的客户，截至${截止时间}您当月已产生话费${产生话费}元，话费余额${话费余额}元。【中国移动】",
				                    "您本期账单已出，回复#ZDFQ将本期人民币账单的部分新增消费${新增消费}元分${分期数}期还，"
				                    + "每期本金${每期本金}元，每期手续费${txt_每期手续费}元。【招商银行】",
				                    "【京东】尊敬的客户，您本次登录的验证码为：${txt_验证码}，有效期${txt_有效期}。"};
		String [] senders = {"中国移动","招商银行","京东"};
		String [] sences = {"话费提醒","账单提醒","验证码提醒"};
		
		for(int i=0; i<models.length; i++)
		{
			entity e = new entity();
			ArrayList<String> words = ictclas(models[i]);			
			e.setModel(models[i]);
			e.setSence(sences[i]);
			e.setSender(senders[i]);
			e.setSplitedwords(words);			
			entities.add(e);			
		}		
		return entities;
	}
}
