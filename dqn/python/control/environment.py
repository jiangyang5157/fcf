import gym
import numpy as np

class Environment:
    
    def __init__(self, problem):
        self.problem = problem
        self.env = gym.make(problem)
        self.observation_space = self.env.observation_space # Discrete
        self.action_space = self.env.action_space # eg: Box(210, 160, 3), Box(4,)

    # Runs one episode
    def run(self, agent):
        state = self.env.reset()
        reward_sum = 0

        while True:            
            self.env.render()

            action = agent.act(state)
            state_, reward, done, info = self.env.step(action)
            if done:
                state_ = None

            agent.observe(state, action, reward, state_)
            agent.replay()            

            state = state_
            reward_sum += reward

            if done:
                break

        print("Total reward: ", reward_sum)

    def to_grayscale(self, img):
        return np.mean(img, axis=2).astype(np.uint8)

    def downsample(self, img):
        return img[::2, ::2]

    def preprocess(self, img):
        return self.to_grayscale(self.downsample(img))
    
    def transform_reward(self, reward):
        return np.sign(reward)
