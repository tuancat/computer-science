#!/usr/bin/env python
# -*- coding: utf-8 -*-
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



class TextClassificationPredict(object):
    def __init__(self):
        self.test = None

    def get_train_data():
        #  train data
        train_data = []
        train_data.append({"feature": u"Hôm nay trời đẹp không ?", "target": "hoi_thoi_tiet"})
        train_data.append({"feature": u"Hôm nay thời tiết thế nào ?", "target": "hoi_thoi_tiet"})
        train_data.append({"feature": u"Hôm nay mưa không ?", "target": "hoi_thoi_tiet"})
        train_data.append({"feature": u"Chào em gái", "target": "chao_hoi"})
        train_data.append({"feature": u"Chào bạn", "target": "chao_hoi"})
        train_data.append({"feature": u"Hello bạn", "target": "chao_hoi"})
        train_data.append({"feature": u"Hi kimi", "target": "chao_hoi"})
        train_data.append({"feature": u"Hi em", "target": "chao_hoi"})
        df_train = pd.DataFrame(train_data)

        #  test data
        test_data = []
        test_data.append({"feature": u"Nóng quá", "target": ""})
        df_test = pd.DataFrame(test_data)

        # init model naive bayes
        # model = SVMModel()

        pipe_line = Pipeline([
            ("transformer", FeatureTransformer()),
            ("vect", CountVectorizer()),
            ("tfidf", TfidfTransformer()),
            ("clf",  svm.SVC(C=1.0, kernel='linear', degree=3, gamma='auto'))
        ])

        clf =pipe_line.fit(df_train["feature"], df_train.target)

        predicted = clf.predict(df_test["feature"])

        # Print predicted result
        print (predicted)
        # print (clf.predict_proba(df_test["feature"]))
        # print("SVM Accuracy Score -> ", accuracy_score(predictions_SVM, Test_Y) * 100)


if __name__ == '__main__':
    tcp = TextClassificationPredict
    tcp.get_train_data()