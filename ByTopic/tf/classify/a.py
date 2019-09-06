import gzip
import tensorflow as tf
from tensorflow import keras
import numpy as np
import matplotlib.pyplot as plt
import os


# 目标：训练一个神经网络，该网络可以识别单个服装图片。



def load_mnist_data(data_directory):
    # 从https://github.com/zalandoresearch/fashion-mnist下载。
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

# fashion_mnist = keras.datasets.fashion_mnist
# (train_imags, train_labels), (test_images, test_labels) = fashion_mnist.load_data()

data_directory = "c:/Users/WangQian/Desktop/"
# 加载数据集
(train_images, train_labels), (test_images, test_labels) = load_mnist_data(data_directory)

class_names = ["T-shirt/top", "Trouser", "Pullover", "Dress", "Coat",
               "Sandal", "Shirt", "Sneaker", "Bag", "Ankle boot"]

# print(train_images.shape)
# plt.figure()
# plt.imshow(train_images[0])
# plt.colorbar()
# plt.grid(False)
# plt.show()

# 数据初加工
train_images = train_images / 255.0
test_images = test_images / 255.0

# plt.figure(figsize=(10,10))
# for i in range(25):
#     plt.subplot(5, 5, i + 1)
#     plt.xticks([])
#     plt.yticks([])
#     plt.grid(False)
#     plt.imshow(train_images[i], cmap=plt.cm.binary)
#     plt.xlabel(class_names[train_labels[i]])

# plt.show()

# 建立模型
# Sequential在tensorflow/python/keras/engine/sequential.py中。
# Sequential继承自Model，Model在keras.engine.training.py中。
# Model是一个Network。
# 文档中对Sequential的定义是Linear stack of layers。
model = keras.Sequential([
    keras.layers.Flatten(input_shape=(28,28)),
    # Flatten定义在tensorflow/python/keras/layers/core.py。
    # Flatten派生自Layer，Layer在engine.base_layer中。
    # Flatten将28x28的数组转换为28x28个元素的数组。
    keras.layers.Dense(128, activation=tf.nn.relu),
    # Dense也是Layer。Just your regular densely-connected NN layer.
    # Dense.__init__(units, activation, ...)
    # tf.nn.relu等激活函数位于目录tensorflow/python/ops。
    # Dense是全连接层。
    keras.layers.Dense(10, activation=tf.nn.softmax)
])

model.compile(
    # 优化器
    optimizer="adam",
    loss="sparse_categorical_crossentropy",
    # 损失函数定义在/tensorflow/python/keras/losses.py中。
    metrics=["accuracy"]
    # 度量定义在tensorflow/python/ops/metrics_impl.py中。
    # accuracy是准确率。
)

# 训练模型
model.fit(train_images, train_labels, epochs=5)

# 评估模型
test_loss, test_acc = model.evaluate(test_images, test_labels)
print("Test accuracy: ", test_acc)

# 应用模型
predictions = model.predict(test_images)
print(predictions[0])

# 模型可以用save/save_weights/load_weights保存和加载。
