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
			System.out.println("短信发送方：");
			System.out.println(sender);
			System.out.println("短信场景：");
			System.out.println(sence);
		}
		else
			System.out.println("无匹配模版短信");

		
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
	/*public static void segementWords() throws IOException
	{
		System.out.println("segementWords function");
		wordsList = new ArrayList<String>();
		bookInfo = new ArrayList<bookKeyWords>();
		wordsValueMap = new HashMap<String, Integer>();
		bookList = new ArrayList<String>();	
		nullList = new ArrayList<String>();
		//读如图书内容简介数据，做分词系统
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
							//判断图书内容是否为空，第二次过滤图书 
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
							//调用分词系统
							SegTag segTag = new SegTag(1);
							SegResult segResult;
							String classifyContent;
							try {
								segResult = segTag.split(content);
								classifyContent = segResult.getFinalResult();
								StopWordFilter swf = new StopWordFilter(classifyContent, stopwordsSet);
								String wordsFiltered = swf.getStopWordFiltered();    //过滤停用词
								wordSetConstruct(wordsFiltered);
								storeKeyWords(filename.replaceAll(".txt", ""), wordsFiltered);				
							} catch (Exception e) {
								// TODO Auto-generated catch block
								System.out.println("segment error fileName: "+filename);
								e.printStackTrace();
							}
					//		classifyContent = segResult.getFinalResult();
							StopWordFilter swf = new StopWordFilter(classifyContent, stopwordsSet);
							String wordsFiltered = swf.getStopWordFiltered();    //过滤停用词
							wordSetConstruct(wordsFiltered);
							storeKeyWords(filename.replaceAll(".txt", ""), wordsFiltered);							
						}//if(!file1.isDirectory())
			    	}//for(int i=0; i<filelist.length; i++)
				}//if(file.isDirectory())
				else
				{
					System.out.println("第一层次文件遍历");
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
		HashMap<String, Integer> wordToValue = new HashMap();  //建立该篇文章 分词 - 值 关系映射
		bkw.setFilename(filename);
		String [] words = wordsFiltered.split(" ");
		//加载计算机常用词
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
			Pattern pattern = Pattern.compile("(([0-9]+年)|([0-9]{1,})|([a-z]{0,}|[A-Z]{1,}[a-z]{1,}|第[0-9]{0,})|[0-9]{0,}.[0-9]{0,})");
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
			Pattern pattern = Pattern.compile("(([0-9]+年)|([0-9]{1,})|([a-z]{0,}|[A-Z]{1,}[a-z]{0,}|第[0-9]{0,})|[0-9]{0,}.[0-9]{0,})");
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
