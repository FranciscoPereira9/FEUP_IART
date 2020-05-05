import sqlite3
import pandas as pd
import matplotlib.pyplot as plt
# Create your connection.
con = sqlite3.connect('database.sqlite')

#Database Has : Country, League, Match, Player, Player_Attributes, Team_attributes, Team

#Select League from database
df = pd.read_sql_query("SELECT * FROM League", con)
pd.set_option('display.max_rows', df.shape[0]+1)
#prints a graph, x: name, y: id
plt.plot(df.loc[:,'name'], df.loc[:,'id'])

df2 = pd.read_sql_query("SELECT * FROM Team", con)
pd.set_option('display.max_rows', df2.shape[0]+1)

df3 = pd.read_sql_query("SELECT * FROM Team_attributes", con)
pd.set_option('display.max_rows', df3.shape[0]+1)