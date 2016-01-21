package IctclasWord;
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
			System.out.println("���ŷ��ͷ���");
			System.out.println(sender);
			System.out.println("���ų�����");
			System.out.println(sence);
		}
		else
			System.out.println("��ƥ��ģ�����");

		
	}

	/*public static String readFormConsole(String prompt)
	{
		Console console = System.console();
		if (console == null) 
		{
            throw new IllegalStateException("Console is not available!");
        }
        return console.readLine(prompt);		
	}	*/
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
	/*public static void segementWords() throws IOException
	{
		System.out.println("segementWords function");
		wordsList = new ArrayList<String>();
		bookInfo = new ArrayList<bookKeyWords>();
		wordsValueMap = new HashMap<String, Integer>();
		bookList = new ArrayList<String>();	
		nullList = new ArrayList<String>();
		//����ͼ�����ݼ�����ݣ����ִ�ϵͳ
				String filepath = "F:\\thesisWorkSpace\\test\\testData\\bookContent";		
				String nullpath = "F:\\thesisWorkSpace\\test\\testData\\";
				File file = new File(filepath);
				if(file.isDirectory())
				{
					String [] filelist = file.list();
					for(int i=0; i<filelist.length; i++)
					{
						String filename = filelist[i];
						String filepath1 = filepath+"\\"+filename;
						File file1 = new File(filepath1);
						if(!file1.isDirectory())
						{
							FileInputStream fis = new FileInputStream(file1);
							BufferedReader br = new BufferedReader(new InputStreamReader(fis));
							String content = "";
							String tempStr = "";
							while((tempStr = br.readLine())!=null)
								content+=tempStr;							
							//�ж�ͼ�������Ƿ�Ϊ�գ��ڶ��ι���ͼ�� 
							if(content.equals(""))
							{
								nullList.add(filename.replaceAll(".txt", ""));
								//System.out.println(filename+" content is null");
								File nullfile = new File(nullpath+"nullfile.txt");
								if(nullfile.exists())
								{
									BufferedWriter bw  = new BufferedWriter(new FileWriter(nullfile,true));
									bw.write(filename);
									bw.newLine();
									bw.flush();
									bw.close();
								}
								else
								{
									BufferedWriter bw = new BufferedWriter(new FileWriter(nullfile));
									bw.write(filename);
									bw.newLine();
									bw.flush();
									bw.close();
								}
								continue;
							}
							bookList.add(filename.replaceAll(".txt", ""));
						//	content+=bookNameMap.get(filename.replaceAll(".txt", "")); 
						//	System.out.println(filename+" content is not null");
							//���÷ִ�ϵͳ
							SegTag segTag = new SegTag(1);
							SegResult segResult;
							String classifyContent;
							try {
								segResult = segTag.split(content);
								classifyContent = segResult.getFinalResult();
								StopWordFilter swf = new StopWordFilter(classifyContent, stopwordsSet);
								String wordsFiltered = swf.getStopWordFiltered();    //����ͣ�ô�
								wordSetConstruct(wordsFiltered);
								storeKeyWords(filename.replaceAll(".txt", ""), wordsFiltered);				
							} catch (Exception e) {
								// TODO Auto-generated catch block
								System.out.println("segment error fileName: "+filename);
								e.printStackTrace();
							}
					//		classifyContent = segResult.getFinalResult();
							StopWordFilter swf = new StopWordFilter(classifyContent, stopwordsSet);
							String wordsFiltered = swf.getStopWordFiltered();    //����ͣ�ô�
							wordSetConstruct(wordsFiltered);
							storeKeyWords(filename.replaceAll(".txt", ""), wordsFiltered);							
						}//if(!file1.isDirectory())
			    	}//for(int i=0; i<filelist.length; i++)
				}//if(file.isDirectory())
				else
				{
					System.out.println("��һ����ļ�����");
				}	
	}	
	//get bookName
	public static void getBookName() throws IOException
	{
		bookNameMap = new HashMap<String, String>();
		String path = "F:\\thesisWorkSpace\\test\\isbnToNameTest.txt";
		File file = new File(path);
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String str = "";
		while((str=br.readLine())!=null)
		{
			String [] strs = str.split(",");
			bookNameMap.put(strs[0], strs[1]);			
		}
		br.close();		
	}
	//store every paper filtered key words and write information to designated file
	public static void storeKeyWords(String filename, String wordsFiltered) throws IOException
	{	
		//System.out.println("storeKeyWords function");
		bookKeyWords bkw = new bookKeyWords();		
		HashMap<String, Integer> wordToValue = new HashMap();  //������ƪ���� �ִ� - ֵ ��ϵӳ��
		bkw.setFilename(filename);
		String [] words = wordsFiltered.split(" ");
		//���ؼ�������ô�
		List<String> computeWords = new ArrayList<String>();
		String [] computeWord = {"ipv6","web","primer","jsp","eclipse","hibernate","servlet","java","structs","spring",
				"css","it","javascript","html","dreamweaver","linux","php","ajax","c"};
		for(int ic=0; ic<computeWord.length; ic++)
		{
			computeWords.add(computeWord[ic]);
		}
		for(int i=0; i<words.length; i++)
		{			
			String word = words[i].trim().toLowerCase();
			Pattern pattern = Pattern.compile("(([0-9]+��)|([0-9]{1,})|([a-z]{0,}|[A-Z]{1,}[a-z]{1,}|��[0-9]{0,})|[0-9]{0,}.[0-9]{0,})");
			Matcher isnum = pattern.matcher(word);
			if(isnum.matches() && !computeWords.contains(word))
				continue;
			if(word == "\n"||word == null ||word == "")
				continue;						
			if(!wordToValue.containsKey(word))
			{
				wordToValue.put(word, 1);
			}
			else
			{
				int value = wordToValue.get(word)+1;
				wordToValue.put(word,value);
			}				
		}
		bkw.setWordvalues(wordToValue);		
		bookInfo.add(bkw);		 
		
		String filepath = "F:\\thesisWorkSpace\\test\\testData\\wordSegment"; 
		File file = new File(filepath+"\\"+filename+".txt");
		if(file.exists())
		{
			file.createNewFile();
		}
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file),"gbk");
		BufferedWriter bw = new BufferedWriter(osw);
		for(String key:wordToValue.keySet())
		{			
			String word = key.trim().toLowerCase();
			String value = String.valueOf(wordToValue.get(word));
			bw.write(key+", ("+wordsList.indexOf(word)+","+value+")");
			bw.newLine();
		}
		bw.close();
	}	
	// construct filtered word set
	public static void wordSetConstruct(String wordsfiltered)
	{
		//System.out.println("wordSetConstruct function");
		List<String> computeWords = new ArrayList<String>();
		String [] computeWord = {"ipv6","web","primer","jsp","eclipse","hibernate","servlet","java","structs","spring",
				"css","it","javascript","html","dreamweaver","linux","php","ajax","c"};
		for(int ic=0; ic<computeWord.length; ic++)
		{
			computeWords.add(computeWord[ic]);
		}		
		String words[] = wordsfiltered.split(" ");  
		for(int i=0; i<words.length; i++)
		{
			String word = words[i].trim().toLowerCase();
			Pattern pattern = Pattern.compile("(([0-9]+��)|([0-9]{1,})|([a-z]{0,}|[A-Z]{1,}[a-z]{0,}|��[0-9]{0,})|[0-9]{0,}.[0-9]{0,})");
			Matcher isnum = pattern.matcher(word);
			if(isnum.matches() && !computeWords.contains(word))
				continue;
			if(word == "\n"||word == null ||word == "")
				continue;			
			if(!wordsList.contains(word))
				wordsList.add(word);
			if(wordsValueMap.keySet().contains(word))
			{
				int num = wordsValueMap.get(word)+1;
				wordsValueMap.put(word, num);
			}
			else
			{
				wordsValueMap.put(word, 1);
			}
		}
	}*/
}
