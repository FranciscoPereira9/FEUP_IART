import sqlite3
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
from sklearn.linear_model import LinearRegression

# Create your connection

#Database Has : Country, League, Match, Player, Player_Attributes, Team_attributes, Team

#Select League from database
team = pd.read_csv("Team.csv")
team_attributes = pd.read_csv("Team_attributes.csv")
match = pd.read_csv("Match.csv")
player = pd.read_csv("Player.csv")
player_attributes = pd.read_csv("Player_attributes.csv")
match_to_keep = ["id", "league_id", "home_team_api_id", "away_team_api_id", "home_team_goal",
                 "away_team_goal", "home_player_1", "home_player_2", "home_player_3", "home_player_4", "home_player_5",
                 "home_player_6", "home_player_7", "home_player_8", "home_player_9", "home_player_10", "home_player_11",
                 "away_player_1", "away_player_2", "away_player_3", "away_player_4", "away_player_5", "away_player_6",
                 "away_player_7", "away_player_8", "away_player_9", "away_player_10", "away_player_11"]

def analyseHomePlayer(row, number):
    new = player["player_api_id"].isin([row["home_player_"+"%s" % number]])
    new2 = player_attributes["player_api_id"].isin(
        [row["home_player_"+"%s" % number]])
    #print("This -> ", row["home_player_"+"%s" % number], " is this Player :  ", player[new]
        #['player_name'].tolist()[0], "with overall_rating: ", player_attributes[new2]['overall_rating'].tolist()[0])
    return player_attributes[new2]['overall_rating'].tolist()[0]
    
def analyseAwayPlayer(row, number):
    new = player["player_api_id"].isin([row["away_player_"+"%s" % number]])
    new2 = player_attributes["player_api_id"].isin(
        [row["away_player_"+"%s" % number]])
    #print("This -> ", row["away_player_"+"%s" % number], " is this Player :  ", player[new]
    #      ['player_name'].tolist()[0], "with overall_rating: ", player_attributes[new2]['overall_rating'].tolist()[0])
    return player_attributes[new2]['overall_rating'].tolist()[0]

def analyseHomePlayers(row):
    sum = 0
    for index in range(1, 12):
       sum += analyseHomePlayer(row, index)
    return sum
    
def analyseAwayPlayers(row):
    sum=0
    for index in range(1, 12):
        sum += analyseAwayPlayer(row, index)
    return sum

def analyseHomePlayersHeight(row):
    sum = 0
    for index in range(1, 12):
       new = player["player_api_id"].isin([row["home_player_"+"%s" % index]])
       sum += player[new]["height"].tolist()[0]
    return round(sum/11,1)

def analyseAwayPlayersHeight(row):
    sum = 0
    for index in range(1, 12):
       new = player["player_api_id"].isin([row["away_player_"+"%s" % index]])
       sum += player[new]["height"].tolist()[0]
    return round(sum/11,1)

def analyseHomePlayersWeight(row):
    sum = 0
    for index in range(1, 12):
       new = player["player_api_id"].isin([row["home_player_"+"%s" % index]])
       sum += player[new]["weight"].tolist()[0]
    return round(sum/11,1)

def analyseAwayPlayersWeight(row):
    sum = 0
    for index in range(1, 12):
       new = player["player_api_id"].isin([row["away_player_"+"%s" % index]])
       sum += player[new]["weight"].tolist()[0]
    return round(sum/11,1)

def analyseTeamRates(row):
      print("\n\n************ HOME TEAM ************")
      analyseHomeTeamRates(row)
      print("\n\n************ AWAY TEAM ************")
      analyseAwayTeamRates(row)
      print("\n\n************ Average ODS ************")
      print("Home Team Wins: ", analyseTeamOdd(row,"H"), "\t Draw: ", analyseTeamOdd(row,"D"), "\t Away Team Wins: ",analyseTeamOdd(row,"A"))

def analyseTeamOdd(row, string):
    #10 websites
    sum=0
    sum+=match["B365"+"%s" %string].tolist()[0]
    sum+=match["BW"+"%s" %string].tolist()[0]
    sum+=match["IW"+"%s" %string].tolist()[0]
    sum+=match["LB"+"%s" %string].tolist()[0]
    sum+=match["PS"+"%s" %string].tolist()[0]
    sum+=match["WH"+"%s" %string].tolist()[0]
    sum+=match["SJ"+"%s" %string].tolist()[0]
    sum+=match["VC"+"%s" %string].tolist()[0]
    sum+=match["GB"+"%s" %string].tolist()[0]
    sum+=match["BS"+"%s" %string].tolist()[0]
    return round(sum/10, 2)


def analyseHomeTeamRates(row):
    
    print("Players Average Overall Rating : ", round(analyseHomePlayers(match[match_to_keep].iloc[0])/11, 1),"\n")
    new = team_attributes["team_api_id"].isin([row["home_team_api_id"]])
    passing = team_attributes[new]["chanceCreationPassing"].tolist()[0]
    crossing = team_attributes[new]["chanceCreationCrossing"].tolist()[0]
    shooting = team_attributes[new]["chanceCreationShooting"].tolist()[0]
    print("Passing: ", passing, "\tCrossing: ", crossing, "\tShooting", shooting)
    print("---- Average Offense Rates: ", round((passing+shooting+crossing)/3,1), "----\n")
    dribling = team_attributes[new]["buildUpPlayDribbling"].tolist()[0]
    BUpassing = team_attributes[new]["buildUpPlayPassing"].tolist()[0]
    speed = team_attributes[new]["buildUpPlaySpeed"].tolist()[0]
    print("Dribling: ", dribling, "\tBuild Up Passing: ", BUpassing, "\tSpeed", speed)
    print("---- Average Center Rates: ", round((dribling+BUpassing+speed)/3,1), "----\n")
    Agression = team_attributes[new]["defenceAggression"].tolist()[0]
    width = team_attributes[new]["defenceTeamWidth"].tolist()[0]
    pressure = team_attributes[new]["defencePressure"].tolist()[0]
    print("Aggression: ", dribling, "\t Width: ", width, "\tPressure", pressure)
    print("---- Average Defence Rates: ", round((dribling+width+pressure)/3,1), "----\n")
    print("Average Players Height: ", analyseHomePlayersHeight(row))
    print("Average Players Weight: ", analyseHomePlayersWeight(row))

def analyseAwayTeamRates(row):
    print("Average Players Overall Rating : ", round(analyseAwayPlayers(match[match_to_keep].iloc[0])/11, 1), "\n")
    new = team_attributes["team_api_id"].isin([row["away_team_api_id"]])
    passing = team_attributes[new]["chanceCreationPassing"].tolist()[0]
    crossing = team_attributes[new]["chanceCreationCrossing"].tolist()[0]
    shooting = team_attributes[new]["chanceCreationShooting"].tolist()[0]
    print("Passing: ", passing, "\tCrossing: ", crossing, "\tShooting", shooting)   
    print("---- Average Offense Rates: ", round((passing+shooting+crossing)/3,1), "----\n")  
    dribling = team_attributes[new]["buildUpPlayDribbling"].tolist()[0]
    BUpassing = team_attributes[new]["buildUpPlayPassing"].tolist()[0]
    speed = team_attributes[new]["buildUpPlaySpeed"].tolist()[0]
    print("Dribling: ", dribling, "\tBuild Up Passing: ", BUpassing, "\tSpeed", speed)
    print("---- Average Center Rates: ", round((dribling+BUpassing+speed)/3,1), "----\n")
    Aggression = team_attributes[new]["defenceAggression"].tolist()[0]
    width = team_attributes[new]["defenceTeamWidth"].tolist()[0]
    pressure = team_attributes[new]["defencePressure"].tolist()[0]
    print("Aggression: ", dribling, "\t Width: ", width, "\tPressure", pressure)
    print("---- Average Defence Rates: ", round((dribling+width+pressure)/3,1), "----\n")
    print("Average Players Height: ", analyseAwayPlayersHeight(row))
    print("Average Players Weight: ", analyseAwayPlayersWeight(row))
 

analyseTeamRates(match[match_to_keep].iloc[0])


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