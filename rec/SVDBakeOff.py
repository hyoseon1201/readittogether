from BookUserData import BookUserData
from surprise import SVD
from Evaluator import Evaluator

import random
import numpy as np

def LoadBookData():
    ml = BookUserData()
    print("Loading book ratings...")
    data = ml.loadBookLatestSmall()
    print("\nComputing book popularity ranks so we can measure novelty later...")
    rankings = ml.getPopularityRanks()
    return (ml, data, rankings)

np.random.seed(0)
random.seed(0)

# Load up common data set for the recommender algorithms
(ml, evaluationData, rankings) = LoadBookData()

# Construct an Evaluator to, you know, evaluate them
evaluator = Evaluator(evaluationData, rankings)

# SVD
SVD = SVD()
evaluator.AddAlgorithm(SVD, "SVD")

evaluator.SampleTopNRecs(ml)
