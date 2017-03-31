package com.javbus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.alibaba.fastjson.JSON;
import com.javbus.entity.Magnet;
import com.javbus.entity.MovieInfo;
import com.javbus.entity.Star;

public class Main {
	// �����ļ���Ƶ�ļ���
	static String ROOT = "F:\\____temp";
	static String BASEURL = "https://www.javbus.com/";

	String[] suffix = { ".jpg", ".png", ".avi", ".wmv", ".mp4", ".mpg", ".mpeg", ".flv", ".mkv", ".rmvb", ".mov",
			".iso", ".nfo" };

	public static void main(String[] args) throws Exception {
		File root = new File(ROOT);
		File[] listFiles = root.listFiles();
//		for (File file : listFiles) {
//			if (file.isFile()) {
//				String filename = file.getName();
//				String num = filename.substring(0, filename.lastIndexOf("."));
//				// getInfo(num);
//			}
//		}
		MovieInfo info = getInfo("MIaE-015");
		moveMovie(info);
		System.out.println(JSON.toJSONString(info));
		
		
	}
	/**
	 * ���ݷ��Ż�ȡ��Ϣ
	 * @param num
	 * @return
	 * @throws Exception
	 */
	public static MovieInfo getInfo(String num) throws Exception {
		
		num = num.toUpperCase();
		MovieInfo movie = new MovieInfo();
		
		Document doc;
		try {
			doc = Jsoup.connect(BASEURL + num).get();

			String title = doc.select("h3").text();
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
			List<Star> stars = new ArrayList<>();
			Elements star = infos.select("span[onmouseover]");
			
			for (Element element : star) {
				stars.add(getStarInfo(element.select("a").attr("href")));
				title = title.replaceAll(element.text(), "");
			}
			
			movie.setStars(stars);
			movie.setTitle(title.trim());

			//������Ϣ
			List<Magnet> magnets = getMagnets(doc,num);
			movie.setMagnet(magnets);
			return movie;
		} catch (HttpStatusException e) {
			System.out.println(num + "������");
		}
		return null;
	}
	
	
	
	
	
	
	/**
	 * ����<script>��ǩ��ȡvar�Ĳ���
	 * @param e
	 * @return
	 */
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
	
	/**
	 * ������������
	 * @param document
	 * @return 
	 * @throws IOException 
	 */
	public static List<Magnet> getMagnets(Document doc,String num) throws IOException {
		List<Magnet> magnetList = new ArrayList<>();

		Elements e = doc.getElementsByTag("script").eq(8);
		Map<String, String> jsParams = getJsParams(e);
		String url = "https://www.javbus.com/ajax/uncledatoolsbyajax.php";
		Connection con = Jsoup.connect(url);
		con.data(jsParams);
		con.header("referer", BASEURL + num);
		Document document = con.get();
		// ��������
		Elements magnets = document.select("a");
		
		Magnet magnet = new Magnet();
		boolean skip = false;
		int index = 0;
		
		for (int i = 0; i < magnets.size(); i++) {
			if (!"����".equals(magnets.get(i).text())) {
				switch (index) {
				case 0:
					magnet.setMagnetTitle(magnets.get(i).text());
					index++;
					break;
				case 1:
					magnet.setMagnetSize(magnets.get(i).text());
					index++;
					break;
				case 2:
					magnet.setMagnetData(magnets.get(i).text());
					
					index=0;
					//��ȫȱ�ٵ�ֵ
					magnet.setMagnetNum(num);
					String magnetsUrl = magnets.get(i).attr("href");
					magnet.setMagnetUrl(magnetsUrl.substring(0,magnetsUrl.lastIndexOf("&")));
					//������
					magnetList.add(magnet);
					
					//����magnet����
					magnet=new Magnet();
					break;
				default:
					System.out.println(index);
					break;
				}
			}else {
				magnet.setIsHD(true);
			}
		}
		return magnetList;
	}
	/**
	 * ��ȡ��Ա��Ϣ...
	 * @param StarUrl
	 * @return 
	 * @throws IOException 
	 */
	public static Star getStarInfo(String StarUrl) throws IOException {
		Star star = new Star();
		
		Document document = Jsoup.connect(StarUrl).get();
		
		Elements img = document.select(".avatar-box > .photo-frame img");
		
		star.setImage(img.attr("src"));
		
		Elements select = document.select(".avatar-box .photo-info > *");
		for (Element element : select) {
			if ("span".equals(element.tagName())) {
				star.setName(element.text());
			} else {
				String[] split = element.text().split(": ");
				switch (split[0]) {
				case "����":
					star.setBirthday(split[1]);
					break;
				case "���g":
					star.setAge(split[1]);
					break;
				case "���":
					star.setHeight(split[1]);
					break;
				case "�ֱ�":
					star.setCup(split[1]);
					break;
				case "�؇�":
					star.setBust(split[1]);
					break;
				case "����":
					star.setWaist(split[1]);
					break;
				case "�·�":
					star.setHips(split[1]);
					break;
				case "������":
					star.setHometown(split[1]);
					break;
				case "�ۺ�":
					star.setHometown(split[1]);
					break;
				default:
					System.err.println(StarUrl+":");
					System.err.println("����֮��Ĳ���:"+element.text());
					break;
				}
			}
		}
		return star;
	}
	
	/**
	 * �����ļ�
	 * @param urlString ���ص�����
	 * @param filename	������ļ���
	 * @param savePath	�����λ��
	 * @throws Exception
	 */
	public static void download(String urlString, String filename, String savePath) throws Exception {
		// ����URL
		URL url = new URL(urlString);
		// ������
		URLConnection con = url.openConnection();
		// ��������ʱΪ5s
		con.setConnectTimeout(5 * 1000);
		// ������
		InputStream is = con.getInputStream();

		// 1K�����ݻ���
		byte[] bs = new byte[1024];
		// ��ȡ�������ݳ���
		int len;
		// ������ļ���
		File sf = new File(savePath);
		if (!sf.exists()) {
			sf.mkdirs();
		}
		OutputStream os = new FileOutputStream(sf.getPath() + "\\" + filename);
		// ��ʼ��ȡ
		while ((len = is.read(bs)) != -1) {
			os.write(bs, 0, len);
		}
		// ��ϣ��ر���������
		os.close();
		is.close();
		System.out.println(savePath+"\\"+filename+"����ɹ�");
	}
	
	/**
	 * �ƶ��ļ�,�����ļ���,����ͼƬ
	 * @param info
	 * @throws Exception 
	 */
	public static void moveMovie(MovieInfo info) throws Exception {
		List<String> previews = info.getPreviews();
		for (int i = 0; i < previews.size(); i++) {
			String previewUrl = previews.get(i);
			String suffix = previewUrl.substring(previewUrl.lastIndexOf("."),previewUrl.length());
			download(previews.get(i), info.getTitle()+(previews.get(i)).hashCode()+suffix, "f://img");
		}
	}
}
