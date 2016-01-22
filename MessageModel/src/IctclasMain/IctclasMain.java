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
			System.out.println("�����������Ϣ��");		
			Scanner in = new Scanner(System.in);
			String message = in.nextLine();			
			ArrayList<String> splitedMessage = ictclas(message);
			matchModel(modelsEntity, splitedMessage);		
			System.out.println("�Ƿ������0-��1-��");
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
			System.out.println("��������������ݶ�Ӧ��ģ��Ϊ��");
			System.out.println(model);
			System.out.println("���ŷ��ͷ���"+sender);
			System.out.println("���ų�����"+sence);
		}
		else
			System.out.println("��ƥ��ģ�����");		
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
		String [] models = {"�𾴵Ŀͻ�������${��ֹʱ��}�������Ѳ�������${��������}Ԫ���������${�������}Ԫ�����й��ƶ���",
				                    "�������˵��ѳ����ظ�#ZDFQ������������˵��Ĳ�����������${��������}Ԫ��${������}�ڻ���"
				                    + "ÿ�ڱ���${ÿ�ڱ���}Ԫ��ÿ��������${txt_ÿ��������}Ԫ�����������С�",
				                    "���������𾴵Ŀͻ��������ε�¼����֤��Ϊ��${txt_��֤��}����Ч��${txt_��Ч��}��"};
		String [] senders = {"�й��ƶ�","��������","����"};
		String [] sences = {"��������","�˵�����","��֤������"};
		
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
