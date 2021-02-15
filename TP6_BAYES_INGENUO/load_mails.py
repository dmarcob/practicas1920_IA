# Example of loading emails from Enron
# Juan D. Tardos
# 28-Nov-2018

######################################################
# Imports
######################################################

import numpy as np
import json
import glob
import matplotlib.pyplot as plt
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.feature_extraction.text import TfidfTransformer
from sklearn.naive_bayes import MultinomialNB
from sklearn.naive_bayes import BernoulliNB
from sklearn.utils import shuffle
from sklearn import metrics

from kfoldCrossValidation import kfold, pruebas_laplace

######################################################
# Functions for loading mails
######################################################

def read_folder(folder):
    mails = []
    file_list = glob.glob(folder)  # List mails in folder
    num_files = len(file_list)
    for i in range(0, num_files):
        i_path = file_list[i]
        # print(i_path)
        i_file = open(i_path, 'rb')
        i_str = i_file.read()
        i_text = i_str.decode('utf-8', errors='ignore')  # Convert to Unicode
        mails.append(i_text)  # Append to the mail structure
        i_file.close()
    return mails


def load_enron_folders(datasets):
    path = '/home/diego/Escritorio/practicas1920_IA/TP6_BAYES_INGENUO/'
    ham = []
    spam = []
    for j in datasets:
        ham  = ham  + read_folder(path + 'enron' + str(j) + '/ham/*.txt')
        spam = spam + read_folder(path + 'enron' + str(j) + '/spam/*.txt')
    num_ham  = len(ham)
    num_spam = len(spam)
    print("mails:", num_ham+num_spam)
    print("ham  :", num_ham)
    print("spam :", num_spam)

    mails = ham + spam
    labels = [0]*num_ham + [1]*num_spam
    mails, labels = shuffle(mails, labels, random_state=0)
    return mails, labels


######################################################
# Main
######################################################

def print_resultados(classifier, best_size, X_test, y_test):
    print("----------")
    print("RESULTADOS")
    print("----------")
    #Prediccion de clases a las que pertenecen los correos electronicos
    y_pred = classifier.predict(X_test)
    #Prediccion de probabilidades de pertenecer a cada clase
    y_pred_proba = classifier.predict_proba(X_test)
    print("Mejor hiper parámetro suavizado Laplace: " + str(best_size))
    
    f1_score = metrics.f1_score( y_test, y_pred)
    print("f1-score: " + str(f1_score))
     
    confusion_matrix = metrics.confusion_matrix( y_test, y_pred)
    print("matriz de confusión:")
    print(confusion_matrix)
    
    print("curva precision-recall:")
    precision, recall, thresholds = metrics.precision_recall_curve(y_test, y_pred_proba[:,1])
    
    plt.plot(recall, precision)
    plt.xlabel("recall")
    plt.ylabel("precision")
    plt.show()

print("Loading files...")

print("------Loading train and validation data--------")
mails, y = load_enron_folders([1,2,3,4,5])



print("--------------Loading Test data----------------")
mails_test, y_test = load_enron_folders([6])

print("-----Example of obtaining BOWs from emails-----")
vectorizer  = CountVectorizer(ngram_range=(1, 1))  # Initialize BOW structure
X = vectorizer.fit_transform(mails)                # BOW with word counts
X_test = vectorizer.transform(mails_test)          # BOW with word counts
#print("A Bag of Words is represented as a sparse matrix:" )
#print(X)

print("\nEleccion mejor clasificador, distribucion multinomial")
classifier, best_size = kfold("multinomial", 10, X, y)
print_resultados(classifier, best_size, X_test, y_test)

print("\nEleccion mejor clasificador, distribucion bernuilli")
classifier, best_size = kfold("bernuilli", 10, X, y)
print_resultados(classifier, best_size, X_test, y_test)

print("\nPruebas para distintos valores del hiperparametro de Laplace")
list = [0.0, 0.01, 0.1, 0.5, 1, 5, 10, 15, 20, 50, 80, 100]
pruebas_laplace(X, y, X_test, y_test, list)
