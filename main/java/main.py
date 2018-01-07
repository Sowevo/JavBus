import pymysql
import requests
import json
import random

#通用的参数
headers = {
    'cookie': "stay_login=1; id=Fo03qEeFaMONgC7LWN09761",
    'x-syno-token': "DoHNAxhfnClzw"
    }
url = "http://10.0.0.2:5000/webapi/entry.cgi" 


def getAllDB():
	conn = pymysql.connect(host='rx9fvp4k.884.dnstoo.com', port=5510, user='javbus_f', passwd='passwd',db='javbus',  charset="utf8")
	cur = conn.cursor()
	cur.execute("SELECT NUM,TITLE,CENSORED,`RELEASE`,RUNTIME,STARS,DIRECTOR,STUDIO,LABEL,GENRES,SERIES from JavBus ORDER BY UPDATETIME DESC;")
	content = cur.fetchall()
	dic = {}
	for row in content:
		r={}
		r['NUM']		=row[0]
		r['TITLE']		=row[1]
		r['CENSORED']	=row[2]
		r['RELEASE']	=row[3]
		r['RUNTIME']	=row[4]
		r['STARS']		=row[5]
		r['DIRECTOR']	=row[6]
		r['STUDIO']		=row[7]
		r['LABEL']		=row[8]
		r['GENRES']		=row[9]
		r['SERIES']		=row[10]
		dic[row[0]]=r
	return dic
def getAllNas():
	payload = {
	'offset':'0',
	'limit':'1111',
	'sort_by':'"added"',
	'sort_direction':'"desc"',
	'library_id':'0',
	'additional':'["summary","collection","poster_mtime","watched_ratio"]',
	'api':'SYNO.VideoStation2.HomeVideo',
	'method':'list',
	'version':'1'
	}
	response = requests.request("POST", url, data=payload, headers=headers)
	print(response.text)
	data = json.loads(response.text)
	return(data)

def saveInfo(id,title,record_date_date,record_date_time,certificate,rating,genre,actor,writer,director,summary):
	payload = {
		'library_id':'0',						#不知道
		'target':'"video"',
		'id':id,								#id
		'title':"\""+title+"\"",							#标题
		'record_date_date':record_date_date,		#录制时间
		'record_date_time':record_date_time,		#录制时间
		'certificate':certificate,					#级别
		'rating':rating,							#评分,百分制??
		'summary':"\""+summary+"\"",					#摘要
		'genre':genre,			#类型
		'actor':actor,			#演员
		'writer':writer,				#作者
		'director':director,			#导演
		'record_date':"\""+record_date_date+" "+record_date_time+"\"",	#录制时间
		'api':'SYNO.VideoStation2.HomeVideo',
		'method':'edit',
		'version':'1'
		}
	response = requests.request("POST", url, data=payload, headers=headers)
	data = json.loads(response.text)
	if data['success']:
		print(str(id)+'信息更新成功')
	else :
		print(response.text)
		print(payload)
AllDB = getAllDB()

videos = getAllNas()['data']['video']
for video in videos:
	sharepath = video['additional']['file'][0]['sharepath']

	title = sharepath[sharepath.rindex("/")+1:len(sharepath)]



	id = video['id']

	num = title[13:title.index(')')].upper()

	
	movieInfo = AllDB[num]

	record_date_date = movieInfo['RELEASE']

	CENSORED = movieInfo['CENSORED']
	if CENSORED=='無碼':
		CENSORED = '无码'
	else :
		CENSORED = '有码'

	GENRES = movieInfo['GENRES']
	genres = GENRES.split(",")
	genres.insert(0,CENSORED)
	genre = str(genres).replace("'","\"")

	STARS = movieInfo['STARS']
	stars = STARS.split(",")
	actor = str(stars).replace("'","\"")

	WRITER = [];
	STUDIO = movieInfo['STUDIO']
	LABEL = movieInfo['LABEL']
	if STUDIO!='null':
		WRITER.append(STUDIO)
	if LABEL!=' null' and LABEL!=STUDIO:
		WRITER.append(LABEL)
	writer = str(WRITER).replace("'","\"").replace(" ","")

	DIRECTOR = movieInfo['DIRECTOR']
	directors = [];
	if DIRECTOR!='null':
		directors.append(DIRECTOR)
	directors.append(" ")
	director = str(directors).replace("'","\"").replace(" ","")

	
	newTitle = movieInfo['TITLE']
	maxLen = 255-len(("("+num+")").encode('utf-8'))
	
	#标题最长255个字
	title =  newTitle.encode("utf-8")[0:maxLen-maxLen%3].decode("utf-8")+"("+num+")"


	record_date_time = "00:00:00"
	certificate = "R18"
	rating = str(random.randint(0,100))

	summary = num

	
	#print(num)
	#print(CENSORED)
	#print(id)
	#print(title)
	#print(record_date_date)
	#print(genre)
	#print(actor)
	#print(writer)
	#print(director)
	#print(summary)


	saveInfo(id, title, record_date_date, record_date_time, certificate, rating, genre, actor, writer, director,summary)

