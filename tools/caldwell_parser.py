#!/usr/bin/env python3
from string import strip

data_file = './caldwell_data'
with open(data_file) as file_:
    data_list = file_.readlines()

split_data_list = [map(strip, i.split('\t')) for i in data_list]

data_attributes = ['id', 'ngc_id', 'constellation', 'type',
                   'right_ascension', 'declination',
                   'magnitude', 'size', 'description']

data_dict_list = [dict(zip(data_attributes, i)) for i in split_data_list]
