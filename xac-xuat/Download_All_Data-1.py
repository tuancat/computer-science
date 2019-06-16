import pandas as pd

pd.core.common.is_list_like = pd.api.types.is_list_like
import yfinance  as yf
from pandas_datareader import data as pdr
import csv

yf.pdr_override()

# 2013-01-02 ~ 2018-11-28
start_date = "2013-02-01"
end_date = "2018-11-28"

with open(r'C:\Users\SamFisher\Downloads\SP500\List.csv', 'r') as f:
    type = csv.reader(f)

    for stock in type:
        if not stock:
            pass
        else:
            ticker = stock[0]
            print(ticker)
            # name = stock[1]
            df = pdr.get_data_yahoo(ticker, start_date, end_date)
            df.to_csv(r'C:\Users\\SamFisher\Downloads\SP500\a.csv')
            print('a.csv is saved')
