from read_data import ReadData
import pandas as pd

from sklearn.pipeline import Pipeline
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.feature_extraction.text import TfidfTransformer
from transformer.feature_transformer import FeatureTransformer
from sklearn.metrics import accuracy_score

from sklearn import svm

training_data = ReadData.readData();
df_train = pd.DataFrame(training_data)

#  test data
test_data = []
test_data.append({"feature": u"hiện tại công nghệ đang phát triển nhanh, công nghệ Var trong sân", "target": ""})
df_test = pd.DataFrame(test_data)

pipe_line = Pipeline([
    ("transformer", FeatureTransformer()),
    ("vect", CountVectorizer()),#bag-of-words
    ("clf", svm.SVC(C=1.0, kernel='linear', gamma='auto', probability=True))
])

clf = pipe_line.fit(df_train["feature"], df_train.target)

predicted = clf.predict(df_test["feature"])

print(clf.predict_proba(df_test["feature"]));

# Print predicted result
print(predicted)

