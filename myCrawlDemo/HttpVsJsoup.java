package myCrawlDemo;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class HttpVsJsoup {
	public static void main(String[] args) throws Exception {
	
		
		// 首先用Httpclient 获取网页内容
		for (int j = 0; j < 250; j = j + 25) {
			CloseableHttpClient client = HttpClients.createDefault();
			HttpGet get = new HttpGet("https://movie.douban.com/top250?start=" + j);
			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			String content = EntityUtils.toString(entity, "utf-8");
			// 使用Jsoup解析网页
			Document doc = Jsoup.parse(content);
			
			List<Integer> list=new ArrayList<>();
			List<String> list1=new ArrayList<>();
			List<Double> list2=new ArrayList<>();

			for (int i = 1; i <= 25; i++) {
				Elements elements = doc.select("img[alt]");
				Element element = elements.get(i);
				String name = element.attr("alt");
				Elements elements2 = doc.select("em[class]");
				String rank1 = elements2.get(i - 1).text();
				Integer rank=Integer.parseInt(rank1);
				Elements element3 = doc.select("span[class=rating_num][property=v:average]");
				String score1 = element3.get(i - 1).text();
				Double score=Double.parseDouble(score1);
				//System.out.println("豆瓣电影排名第" + rank + ": " + name + " 评分为 " + score);
				list.add(rank);
				list1.add(name);
				list2.add(score);	
			}
//			for(int k=0;k<list.size();k++)
//				System.out.println(list.get(k));
			//将数据存储到数据库中
	       new DataStore().store(list,list1,list2);
	       
		}
	}
}