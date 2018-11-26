import gym
import numpy as np
import cv2

# def preprocess(self, state):
#     state = cv2.cvtColor(cv2.resize(state, (80, 80)), cv2.COLOR_BGR2GRAY)
#     # ret, state = cv2.threshold(state, 1, 255, cv2.THRESH_BINARY)
#     return np.reshape(state, (80, 80, 1))
    
def to_grayscale(img):
    return np.mean(img, axis=2).astype(np.uint8)

def downsample(img):
    return img[::2, ::2]

def preprocess(img):
    return to_grayscale(downsample(img))

def transform_reward(reward):
    return np.sign(reward)
    
def main():
    env = gym.make('Breakout-v0')
    # observation_space = env.observation_space # Discrete
    # action_space = env.action_space # eg: Box(210, 160, 3), Box(4,)

    while True:       
        state = env.reset()
        reward_sum = 0
        while True:            
            env.render()

            state_, reward, done, _ = env.step(env.action_space.sample())
            # action = act(state)
            # state_, reward, done, info = env.step(action)

            if done:
                state_ = None

            # observe(state, action, reward, state_)
            # replay()            

            state = state_
            reward_sum += reward

            if done:
                break

        print("Total reward: ", reward_sum)

if __name__ == "__main__":
    main()