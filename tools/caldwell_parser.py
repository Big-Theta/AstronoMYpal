#!/usr/bin/env python3
from string import strip
from bs4 import BeautifulSoup

data_file = './caldwell_html'
with open(data_file) as file_:
    data = file_.read()

soup = BeautifulSoup(data, "lxml")
table = soup.find("table")

data_attributes = ['id', 'ngc_id', 'constellation', 'type',
                   'right_ascension', 'declination',
                   'magnitude', 'size', 'description']

data_list = []
for row in table.findAll('tr'):
    col = row.findAll('td')
    entry = {}
    for field in data_attributes:
        field_ = col[data_attributes.index(field)].string
        try:
            entry[field] = field_.strip()
        except AttributeError:
            entry[field] = ''
    data_list.append(entry)

type_map = {'BN': "Bright nebula", 'GC': "Globular cluster",
            'OC': "Open cluster", 'EG': "Elliptical (type) galaxy",
            'DN': "Dark nebula", 'IG': "Irregular galaxy",
            'PN': "Planetary nebula", 'SN': "Supernova remnant",
            'SG': "Spiral (type) galaxy"}

def type_lookup(type_):
    try:
        return type_map[type_]
    except KeyError:
        return type_

# TODO: Constellation abbreviation transformation

rows = [[None, i['ngc_id'], '', i['description'], type_lookup(i['type']), '',
       i['size'], i['constellation'], i['magnitude'],
       i['right_ascension'], i['declination'], ''] for i in data_list]
