import pandas as pd
from sklearn.preprocessing import LabelEncoder
import time


startT = time.time()


df = pd.read_csv('https://archive.ics.uci.edu/ml/machine-learning-databases/breast-cancer-wisconsin/wdbc.data', header =None);
df.head();
X = df.loc[:, 2:].values
# y là target
y = df.loc[:, 1].values
le = LabelEncoder();
# Convert target dạng chữ về số
y = le.fit_transform(y)

le.transform(['M', 'B'])

from sklearn.model_selection import train_test_split

# Chia data thành 2 phần: training data, test data
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=1)

from sklearn.preprocessing import StandardScaler
from sklearn.decomposition import PCA
from sklearn.linear_model import LogisticRegression
from sklearn.pipeline import Pipeline

pipe_lr = Pipeline([('scl', StandardScaler()),
                    ('pca', PCA(n_components=2)),
                    ('clf', LogisticRegression(random_state=1))
                   ])

pipe_lr.fit(X_train, y_train)

'Test Accuracy: %.3f' % pipe_lr.score(X_test, y_test)