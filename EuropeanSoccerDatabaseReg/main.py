import sqlite3
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
from sklearn.linear_model import LinearRegression
#Database Has : Country, League, Match, Player, Player_Attributes, Team_attributes, Team
#Select League from database

#**********************************************************
#CODE USED TO DATA PROCESSING :
# CREATING A NEW DATAFRAME 
## STARTS HERE:
team = pd.read_csv("Team.csv")
team_attributes = pd.read_csv("Team_attributes.csv")
match = pd.read_csv("Match.csv")
player = pd.read_csv("Player.csv")
player_attributes = pd.read_csv("Player_attributes.csv")

match_depth = match.shape[0]

def analyseDifGoals(row, index):

    return match["home_team_goal"].tolist()[index] - match["away_team_goal"].tolist()[index]


def analyseHomePlayer(row, number, index):
    new = player["player_api_id"].isin([row["home_player_"+"%s" % number]])
    new2 = player_attributes["player_api_id"].isin(
        [row["home_player_"+"%s" % number]])

    return player_attributes[new2]['overall_rating'].tolist()[index]

def analyseAwayPlayer(row, number, index):
    new = player["player_api_id"].isin([row["away_player_"+"%s" % number]])
    new2 = player_attributes["player_api_id"].isin(
        [row["away_player_"+"%s" % number]])

    return player_attributes[new2]['overall_rating'].tolist()[index]


def analyseHomePlayers(row, index):
    sum = 0
    for i in range(1, 12):
       sum += analyseHomePlayer(row, i, index)
    return sum


def analyseAwayPlayers(row):
    sum = 0
    for i in range(1, 12):
        sum += analyseAwayPlayer(row, i, index)
    return sum


def analyseHomePlayersHeight(row):
    sum = 0
    for i in range(1, 12):
       new = player["player_api_id"].isin([row["home_player_"+"%s" % i]])
       sum += player[new]["height"].tolist()[0]
    return round(sum/11, 1)


def analyseAwayPlayersHeight(row):
    sum = 0
    for i in range(1, 12):
       new = player["player_api_id"].isin([row["away_player_"+"%s" % i]])
       sum += player[new]["height"].tolist()[0]
    return round(sum/11, 1)


def analyseHomePlayersWeight(row):
    sum = 0
    for i in range(1, 12):
       new = player["player_api_id"].isin([row["home_player_"+"%s" % i]])
       sum += player[new]["weight"].tolist()[0]
    return round(sum/11, 1)


def analyseAwayPlayersWeight(row):
    sum = 0
    for i in range(1, 12):
       new = player["player_api_id"].isin([row["away_player_"+"%s" % i]])
       sum += player[new]["weight"].tolist()[0]
    return round(sum/11, 1)

def analyseTeamOdd(row, string, index):
    #10 websites
    sum = 0
    sum += match["B365"+"%s" % string].tolist()[index]
    sum += match["BW"+"%s" % string].tolist()[index]
    sum += match["IW"+"%s" % string].tolist()[index]
    sum += match["LB"+"%s" % string].tolist()[index]
    sum += match["PS"+"%s" % string].tolist()[index]
    sum += match["WH"+"%s" % string].tolist()[index]
    sum += match["SJ"+"%s" % string].tolist()[index]
    sum += match["VC"+"%s" % string].tolist()[index]
    sum += match["GB"+"%s" % string].tolist()[index]
    sum += match["BS"+"%s" % string].tolist()[index]
    return round(sum/10, 2)

def analyseOffenseRates(row,index,string):
    new = team_attributes["team_api_id"].isin([row["%s" %string]])
    passing = team_attributes[new]["chanceCreationPassing"].tolist()[0]
    crossing = team_attributes[new]["chanceCreationCrossing"].tolist()[0]
    shooting = team_attributes[new]["chanceCreationShooting"].tolist()[0]
    return round((passing+shooting+crossing)/3, 1)

def analyseCenterRates(row,index,string):
    new = team_attributes["team_api_id"].isin([row["%s" % string]])
    dribling = team_attributes[new]["buildUpPlayDribbling"].tolist()[0]
    BUpassing = team_attributes[new]["buildUpPlayPassing"].tolist()[0]
    speed = team_attributes[new]["buildUpPlaySpeed"].tolist()[0]
    return round((dribling+BUpassing+speed)/3, 1)

def analyseDefenceRates(row,index,string):
    new = team_attributes["team_api_id"].isin([row["%s" % string]])
    agression = team_attributes[new]["defenceAggression"].tolist()[0]
    width = team_attributes[new]["defenceTeamWidth"].tolist()[0]
    pressure = team_attributes[new]["defencePressure"].tolist()[0]
    return round((agression+width+pressure)/3, 1)

new = team_attributes["team_api_id"].isin([match.iloc[0]["home_team_api_id"]])
new_dataset = []
for index in range(0, match_depth):
    new_dataset.append((index,match["home_team_api_id"].iloc[index], match["away_team_api_id"].iloc[index], analyseDifGoals(match.iloc[index], index),
        analyseOffenseRates(match.iloc[index],index, "home_team_api_id"),
        analyseCenterRates(match.iloc[index],index, "home_team_api_id"),
        analyseDefenceRates(match.iloc[index],index, "home_team_api_id"),
        analyseOffenseRates(match.iloc[index],index, "away_team_api_id"),
        analyseCenterRates(match.iloc[index],index, "away_team_api_id"),
        analyseDefenceRates(match.iloc[index],index, "away_team_api_id"),
        analyseHomePlayersHeight(match.iloc[index]), analyseHomePlayersWeight(match.iloc[index]), 
        analyseAwayPlayersHeight(match.iloc[index]), analyseAwayPlayersWeight(match.iloc[index]), 
        analyseTeamOdd(match.iloc[index], "H", index),analyseTeamOdd(match.iloc[index], "D", index),analyseTeamOdd(match.iloc[index], "A", index)))

new_dataframe = pd.DataFrame(new_dataset, columns=['id','home_team_api_id', 'away_team_api_id', 'dif_goals',                                                
                                                   'home_team_offense_rates','home_team_center_rates','home_team_defense_rates',
                                                   'away_team_offense_rates','away_team_center_rates','away_team_defense_rates',
                                                   'home_team_average_height', 'home_team_average_weight',
                                                   'away_team_average_height', 'away_team_average_weight',
                                                   'average_home_odd', 'average_draw_odd','average_away_odd'])
new_dataframe.to_csv("file2.csv")
## ENDS HERE
#**********************************************************
'''

#following the scikit-learn tutorial

import numpy as np
import pandas as pd
 
from sklearn.model_selection import train_test_split
from sklearn import preprocessing
from sklearn.ensemble import RandomForestRegressor
from sklearn.pipeline import make_pipeline
from sklearn.model_selection import GridSearchCV
from sklearn.metrics import mean_squared_error, r2_score
from sklearn.externals import joblib 

#code
match2 = pd.read_csv("Match.csv")
match_to_keep = ["id", "league_id", "home_team_api_id", "away_team_api_id", "home_team_goal",
                 "away_team_goal", "home_player_1", "home_player_2", "home_player_3", "home_player_4", "home_player_5",
                 "home_player_6", "home_player_7", "home_player_8", "home_player_9", "home_player_10", "home_player_11",
                 "away_player_1", "away_player_2", "away_player_3", "away_player_4", "away_player_5", "away_player_6",
                 "away_player_7", "away_player_8", "away_player_9", "away_player_10", "away_player_11"]
match = match2[match_to_keep]

y = match.away_team_goal
X = match.drop('away_team_goal', axis=1)

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=123, stratify=y)

# 5. Declare data preprocessing steps
pipeline = make_pipeline(preprocessing.StandardScaler(), 
                         RandomForestRegressor(n_estimators=100))

# 6. Declare hyperparameters to tune
hyperparameters = { 'randomforestregressor__max_features' : ['auto', 'sqrt', 'log2'],
                  'randomforestregressor__max_depth': [None, 5, 3, 1]}
 
# 7. Tune model using cross-validation pipeline
clf = GridSearchCV(pipeline, hyperparameters, cv=10)
 
clf.fit(X_train, y_train)
 
# 8. Refit on the entire training set
# No additional code needed if clf.refit == True (default is True)
 
# 9. Evaluate model pipeline on test data
pred = clf.predict(X_test)
print(r2_score(y_test, pred))
print(mean_squared_error(y_test, pred))
 
# 10. Save model for future use
joblib.dump(clf, 'rf_regressor.pkl')
# To load: clf2 = joblib.load('rf_regressor.pkl')

'''
