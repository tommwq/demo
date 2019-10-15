"""
测试不同层数对模型训练效果的影响。

单次测试结果：
层数    损失    精度
1       0.0955  0.9715
2       0.1205  0.9637
3       0.1058  0.9679
4       0.1007  0.9706

结论：
就这个数据集合来说，根据本次测试结果，随着层数的增加，模型的准确率并没有明显变化。

"""
import gzip
import tensorflow as tf
from tensorflow import keras
import numpy as np
import matplotlib.pyplot as plt
import os

def load_mnist_data(data_directory):
    data_files = [
        os.path.join(data_directory, "train-labels-idx1-ubyte.gz"),
        os.path.join(data_directory, "train-images-idx3-ubyte.gz"),
        os.path.join(data_directory, "t10k-labels-idx1-ubyte.gz"),
        os.path.join(data_directory, "t10k-images-idx3-ubyte.gz")
    ]

    with gzip.open(data_files[0], 'rb') as lbpath:
        y_train = np.frombuffer(lbpath.read(), np.uint8, offset=8)

    with gzip.open(data_files[1], 'rb') as imgpath:
        x_train = np.frombuffer(
            imgpath.read(), np.uint8, offset=16).reshape(len(y_train), 28, 28)

    with gzip.open(data_files[2], 'rb') as lbpath:
        y_test = np.frombuffer(lbpath.read(), np.uint8, offset=8)

    with gzip.open(data_files[3], 'rb') as imgpath:
        x_test = np.frombuffer(
            imgpath.read(), np.uint8, offset=16).reshape(len(y_test), 28, 28)

    return (x_train, y_train), (x_test, y_test)

data_directory="c:/Users/WangQian/Workspace/Project/dataset/mnist/"
(train_images, train_labels), (test_images, test_labels) = load_mnist_data(data_directory)

class_names = ["T-shirt/top", "Trouser", "Pullover", "Dress", "Coat",
               "Sandal", "Shirt", "Sneaker", "Bag", "Ankle boot"]

train_images = train_images / 255.0
test_images = test_images / 255.0

def create_model(layer_number):
    layers = [keras.layers.Flatten(input_shape=(28,28))]
    node_number = 64
    for x in range(layer_number):
        layers.append(keras.layers.Dense(node_number, activation=tf.nn.relu))

    layers.append(keras.layers.Dense(10, activation=tf.nn.softmax))
    return keras.Sequential(layers)

def test_model(model):
    model.compile(
        optimizer="adam",
        loss="sparse_categorical_crossentropy",
        metrics=["accuracy"]
    )
    
    # 训练模型
    model.fit(train_images, train_labels, epochs=5)
    
    # 评估模型
    test_loss, test_acc = model.evaluate(test_images, test_labels)
    return (test_loss, test_acc)

resultList = []
layer_list = [1, 2, 3, 4]
for layer_number in layer_list:
    loss, accu = test_model(create_model(layer_number))
    resultList.append((layer_number, loss, accu))

print("层数\t损失\t精度")
for result in resultList:
    print("%d\t%.4f\t%.4f" % result)
