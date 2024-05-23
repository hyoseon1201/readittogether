import csv

from surprise import Dataset
from surprise import Reader

from collections import defaultdict

class BookUserData:

    bookID_to_name = {}
    name_to_bookID = {}
    ratingsPath = 'reviews.csv'

    def loadBookLatestSmall(self):
        self.bookID_to_name = {}
        self.name_to_bookID = {}

        reader = Reader(line_format='user item rating timestamp', sep=',', skip_lines=1)

        ratingsDataset = Dataset.load_from_file(self.ratingsPath, reader=reader)
        return ratingsDataset

    def getPopularityRanks(self):
        ratings = defaultdict(int)
        rankings = defaultdict(int)
        with open(self.ratingsPath, newline='') as csvfile:
            ratingReader = csv.reader(csvfile)
            next(ratingReader)
            for row in ratingReader:
                bookId = int(row[1])
                ratings[bookId] += 1
        rank = 1
        for bookId, ratingCount in sorted(ratings.items(), key=lambda x: x[1], reverse=True):
            rankings[bookId] = rank
            rank += 1
        return rankings
