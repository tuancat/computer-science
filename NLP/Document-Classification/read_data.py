import sys
import re
import unicodedata as ud
import pandas as pd
import xlrd



class ReadData(object):
    @staticmethod
    def readData():
        # data = pd.read_excel(r'C:\Users\SamFisher\Downloads\training-set.xlsx');
        # df = pd.DataFrame(data, columns=['data', 'label'])
        # print(df.head(1))
        wb = xlrd.open_workbook(r'C:\Users\SamFisher\Downloads\training-set.xlsx')
        firstSheet = wb.sheet_by_index(0);
        #  train data
        train_data = [];
        for row_idx in range(0, firstSheet.nrows):
            if (row_idx != 0):
                train_data.append({'feature': str(firstSheet.cell(row_idx, 0).value), "target": firstSheet.cell(row_idx, 1).value});

        return train_data;
