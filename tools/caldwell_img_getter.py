#!/usr/bin/env python3
from bs4 import BeautifulSoup

wiki_file = './caldwell_wiki_data.html'
with open(wiki_file) as file_:
    data = file_.read()

soup = BeautifulSoup(data, "lxml")
rows = soup.findAll('tr')
image_count = 1
images = {}
print(rows[1].findAll('td')[3].find('a').get('href'))
for row in rows:
    cells = row.findAll('td')
    image_name = cells[3].find('a')
    if image_name:
        dest_name = "caldwell_{}.jpg".format(image_count)
        images[dest_name] = "https://en.wikipedia.org"+image_name.get('href')
        image_count += 1
