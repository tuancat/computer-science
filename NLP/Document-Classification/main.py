from read_data import ReadData
import pandas as pd
from model.svm_model import SVMModel
from model.naive_bayes_model import NaiveBayesModel

from sklearn.pipeline import Pipeline
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.feature_extraction.text import TfidfTransformer
from transformer.feature_transformer import FeatureTransformer
from sklearn.linear_model import SGDClassifier
from sklearn.naive_bayes import MultinomialNB
from sklearn import model_selection, naive_bayes, svm
from sklearn.metrics import accuracy_score

training_data = ReadData.readData();
df_train = pd.DataFrame(training_data)

#  test data
test_data = []
test_data.append({"feature": u"cầu thủ bóng đá", "target": ""})
df_test = pd.DataFrame(test_data)

pipe_line = Pipeline([
    ("transformer", FeatureTransformer()),
    ("vect", CountVectorizer()),
    ("tfidf", TfidfTransformer()),
    ("clf", svm.SVC(C=1.0, kernel='linear', degree=3, gamma='auto'))
])

clf = pipe_line.fit(df_train["feature"], df_train.target)

predicted = clf.predict(df_test["feature"])

# Print predicted result
print(predicted)
