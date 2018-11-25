from keras.models import Sequential
from keras.layers import Dense
from keras.optimizers import RMSprop

LEARNING_RATE = 0.00025

class Brain:

    def __init__(self, state_size, action_size):
        self.state_size = state_size
        self.action_size = action_size
        self.model = self._create_model()

    def _create_model(self):
        # https://www.tensorflow.org/guide/keras
        # https://keras.io/models/model/
        model = Sequential()
        model.add(Dense(output_dim=64, input_dim=self.state_size, activation='relu'))
        model.add(Dense(output_dim=self.action_size, activation='linear'))
        model.compile(optimizer=RMSprop(lr=LEARNING_RATE), loss='mse')
        return model

    def load(self, file_path):
        self.model.load_weights(file_path)

    def save(self, file_path):
        self.model.save_weights(file_path)

    def train(self, data, labels, batch_size):
        self.model.fit(data, labels, batch_size, epochs=1, verbose=0)

    def predict(self, state):
        return self.model.predict(state)

    def predict_one_dim(self, state):
        return self.predict(state.reshape(1, self.state_size)).flatten()
