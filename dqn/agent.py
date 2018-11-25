from brain import Brain
from memory import Memory
import numpy as np
import random
import math

MEMORY_CAPACITY = 100000
BATCH_SIZE_MAX = 64

DISCOUNT = 0.99

EXPLORATION_MAX = 1
EXPLORATION_MIN = 0.01
EXPLORATION_DECAY = 0.001

class Agent:

    def __init__(self, state_size, action_size):
        self.state_size = state_size
        self.action_size = action_size
        self.step_count = 0
        self.exploration = EXPLORATION_MAX
        self.brain = Brain(state_size, action_size)
        self.memory = Memory(MEMORY_CAPACITY)
    
    # Decides what action to take in the state
    def act(self, state):
        if random.random() <= self.exploration:
            return random.randint(0, self.action_size-1)
        else:
            return np.argmax(self.brain.predict_one_dim(state))

    # Adds sample (state, action, reward, state_) to memory, and adjust the exploration rate
    def observe(self, state, action, reward, state_):
        self.memory.add((state, action, reward, state_))        
        # slowly decrease exploration rate based on our eperience
        self.exploration = EXPLORATION_MIN + (EXPLORATION_MAX - EXPLORATION_MIN) * math.exp(-EXPLORATION_DECAY * self.step_count)
        self.step_count += 1
        print("Exploration rate: ", self.exploration)

    # Replays memories and make improvement
    def replay(self):
        # each experence from batch has following format: (state, action, reward, state_)
        batch = self.memory.sample(BATCH_SIZE_MAX)
        batch_size = len(batch)
        if batch_size == 0: # nothing to review
            return

        no_state = np.zeros(self.state_size)
        states = np.array([ exp[0] for exp in batch ])
        states_ = np.array([ (no_state if exp[3] is None else exp[3]) for exp in batch ])

        p = self.brain.predict(states)
        p_ = self.brain.predict(states_)

        data = np.zeros((batch_size, self.state_size))
        labels = np.zeros((batch_size, self.action_size))
        
        for i in range(batch_size):
            exp = batch[i]
            state = exp[0]
            action = exp[1]
            reward = exp[2]
            state_ = exp[3]
            
            target = p[i]
            if state_ is None:
                target[action] = reward
            else:
                target[action] = reward + DISCOUNT * np.amax(p_[i])

            data[i] = state
            labels[i] = target

        self.brain.train(data, labels, batch_size)
