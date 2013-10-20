#!/usr/bin/env python3
from bs4 import BeautifulSoup
from urllib import urlretrieve

wiki_file = './caldwell_wiki_data.html'
with open(wiki_file) as file_:
    data = file_.read()

soup = BeautifulSoup(data, "lxml")
rows = soup.findAll('tr')
image_count = 1
images = {}
# print(rows[1].findAll('td')[3].find('a').find('img').get('src'))
for row in rows:
    cells = row.findAll('td')
    image_name = cells[3].find('a')
    if image_name:
        image_name = image_name.find('img')
    if image_name:
        dest_name = "caldwell_{0:0>3}.jpg".format(image_count)
        images[dest_name] = "http:"+image_name.get('src')
        image_count += 1

for dest, src in images.items():
    urlretrieve(src, './assets/'+dest)
