#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Jan 12 12:32:28 2021

@author: diego
"""
import numpy as np
from sklearn.naive_bayes import MultinomialNB
from sklearn.naive_bayes import BernoulliNB
from sklearn import metrics
from sklearn.model_selection import KFold

#Algoritmo kfold. Devuelve el mejor clasificador con modelo "Learner" y el 
#hiper-parametro de Laplace con el que se obtienen los mejores resultados.
#Para medir los resultados de un clasificador se ha utilizado la métrica
#f1_score
def kfold(Learner, k, examples_x, examples_y): 
    best_size = 0.0
    best_f1_score = 0.0
    y = np.array(examples_y)
    # Pruebas para valores del hyper-parametro de Laplace en el rango [0.1,0.2,..,9.9] 
    for size in np.linspace(0.1,10,100):
        #print("Laplace: " + str(size))
        #Separo examples en k-1 partes de entrenamiento y 1 parte de validacion
        kf = KFold(n_splits=k)
        f1_score = 0.0
        for train_index, validation_index in kf.split(examples_x):
            #Creo y entreno clasificador seleccionado
            if Learner == 'multinomial':
                nb = MultinomialNB(alpha = size, fit_prior=True, class_prior=None)
            else:
                nb = BernoulliNB(alpha = size, fit_prior=True, class_prior=None)

            classifier = nb.fit(examples_x[train_index], y[train_index])
            #Evalúo el clasificador con los datos de validacion
            y_pred = classifier.predict(examples_x[validation_index])
            f1_score += metrics.f1_score(y[validation_index], y_pred)
        
        f1_score /= k
        if f1_score > best_f1_score:
            best_f1_score = f1_score
            best_size = size

    #Devuelvo el mejor clasificador y el mejor valor de Laplace
    if Learner == 'multinomial':
        nb = MultinomialNB(alpha = best_size, fit_prior=True, class_prior=None)
    else:
        nb = BernoulliNB(alpha = size, fit_prior=True, class_prior=None)
    return (nb.fit(examples_x, examples_y), best_size)

        


#Imprime la metrica f1_score para cada valor del hiperparametro de Laplace
#indexado en "list"
def pruebas_laplace(X, y, X_test, y_test, list):
    for size in list:
        nb = MultinomialNB(alpha = size, fit_prior=True, class_prior=None)
        classifier = nb.fit(X, y)
        y_pred = classifier.predict(X_test)
        f1_score = metrics.f1_score(y_test, y_pred)
        print("size=" + str(size) +", f1_score=" + str(f1_score))