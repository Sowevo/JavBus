package com.javbus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.javbus.entity.MovieInfo;

public class Main {

	// �����ļ���Ƶ�ļ���
	static String ROOT = "F:\\____temp";

	String[] suffix = { ".jpg", ".png", ".avi", ".wmv", ".mp4", ".mpg", ".mpeg", ".flv", ".mkv", ".rmvb", ".mov",
			".iso", ".nfo" };

	public static void main(String[] args) throws Exception {
		File root = new File(ROOT);
		File[] listFiles = root.listFiles();
		for (File file : listFiles) {
			if (file.isFile()) {
				String filename = file.getName();
				String num = filename.substring(0, filename.lastIndexOf("."));
				// getInfo(num);
			}
		}
		getInfo("MIaE-015");
	}

	public static void getInfo(String num) throws Exception {
		MovieInfo movie = new MovieInfo();
		String baseurl = "https://www.javbus.com/";
		Document doc;
		try {
			doc = Jsoup.connect(baseurl + num).get();

			String title = doc.select("h3").text();
			System.out.println(title);
			// ����
			Elements mainimg = doc.select(".bigImage");
			movie.setCover(mainimg.first().attr("href"));

			// ��ͼ
			Elements Screenshot = doc.select(".sample-box");
			List<String> previews = new ArrayList<String>();
			for (Element element : Screenshot) {
				previews.add(element.attr("href"));
			}
			movie.setPreviews(previews);

			// ��û����
			Elements classificationElements = doc.select("li[class=active]");
			String classification = classificationElements.text();
			movie.setCensored(classification);

			// ��ϸ��Ϣ
			Elements info = doc.select(".info");
			Elements infos = info.select("p");
			for (int i = 0; i < infos.size(); i++) {
				String header = infos.get(i).select(".header").text();
				if ("�R�e�a:".equals(header)) {
					movie.setNum(infos.get(i).text().replaceAll(header + " ", ""));
					title = title.replaceAll(movie.getNum(), "");
				} else if ("�l������:".equals(header)) {
					movie.setRelease(infos.get(i).text().replaceAll(header + " ", ""));
				} else if ("�L��:".equals(header)) {
					movie.setRunTime(infos.get(i).text().replaceAll(header + " ", ""));
				} else if ("����:".equals(header)) {
					movie.setDirector(infos.get(i).text().replaceAll(header + " ", ""));
				} else if ("�u����:".equals(header)) {
					movie.setStudio(infos.get(i).text().replaceAll(header + " ", ""));
				} else if ("�l����:".equals(header)) {
					movie.setLabel(infos.get(i).text().replaceAll(header + " ", ""));
				} else if ("ϵ��:".equals(header)) {
					movie.setSeries(infos.get(i).text().replaceAll(header + " ", ""));
				} else if ("e:".equals(header) || "�݆T".equals(header)) {
					// System.err.println("�����ֶ�");
				} else {
					// System.err.println(header+"δʶ����ֶ�");
				}
			}

			// ���
			List<String> genres = new ArrayList<String>();
			Elements select = infos.select(".genre");
			for (Element element : select) {
				if (!element.html().contains("star")) {
					genres.add(element.text());
				}
			}
			movie.setGenres(genres);

			// ��Ա
			List<String> stars = new ArrayList<>();
			Elements star = infos.select("span[onmouseover]");
			for (Element element : star) {
				stars.add(element.text());
				title = title.replaceAll(element.text(), "");
			}
			movie.setStars(stars);
			movie.setTitle(title.trim());

			System.out.println(movie);
			// System.out.println(info);
			
			
			//��ȡ��������
			Elements e = doc.getElementsByTag("script").eq(8);
			Map<String, String> jsParams = getJsParams(e);
			String url = "https://www.javbus.com/ajax/uncledatoolsbyajax.php";
			Connection con = Jsoup.connect(url);
			con.data(jsParams);
			con.header("referer", baseurl + num);
			Document document = con.get();
			
			// ��������
			Elements Magnets = document.select("a[title=�������I�c��K�x���}�u�B�Y�Wַ��]");
			System.out.println(document);
			for (int j = 0; j < Magnets.size(); j++) {
				if (j%3==0) {
					System.out.println(Magnets.get(j).text());
					
				}
			}

		} catch (HttpStatusException e) {
			System.out.println(num + "������");
		}
	}

	public static Map<String, String> getJsParams(Elements e) {
		/* �Á���bҪ����Ĳ��� */
		Map<String, String> map = new HashMap<String, String>();
		for (Element element : e) {

			/* ȡ��JS�������� */
			String[] data = element.data().toString().split("var");

			/* ȡ�õ���JS���� */
			for (String variable : data) {

				/* ����variableΪ�յ����� */
				if (variable.contains("=")) {

					/* ȡ������������JS���� */
					if (variable.contains("gid") || variable.contains("uc") || variable.contains("color")
							|| variable.contains("img")) {

						String[] kvp = variable.split("=");

						/* ȡ��JS��������map */
						if (!map.containsKey(kvp[0].trim()))
							map.put(kvp[0].trim(), kvp[1].trim().substring(0, kvp[1].trim().length() - 1).toString());
					}
				}
			}
		}
		map.put("lang", "zh");
		return map;
	}
}
