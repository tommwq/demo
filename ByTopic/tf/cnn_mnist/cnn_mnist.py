import numpy as np
import tensorflow as tf
from tensorflow import keras
import matplotlib.pyplot as plt

tf.logging.set_verbosity(tf.logging.INFO)


def cnn_model_fn(features, labels, mode):
    input_layer = tf.reshape(features["x"], [-1,28,28,1])
    conv1 = tf.layers.conv2d(
        input=input_layer,
        filters=32,
        kernel_size=[5,5],
        padding="same",
        activation=tf.nn.relu)

    pool1 = tf.layer_max_pooling2d(inputs=conv1, pool_size=[2,2], strides=2)
    conv2 = tf.layers.conv2d(
        inputs=pool1,
        filters=64,
        kernel_size=[5,5],
        padding="same",
        activation=tf.nn.relu)
    

if __name__ == "__main__":
    tf.app.run()

    
