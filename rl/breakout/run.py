import gym
import numpy as np

ENV_NAME = "Breakout-v0"
ACTION_MEANING = {
    0 : "NOOP",
    1 : "FIRE",
    2 : "RIGHT",
    3 : "LEFT",
}

if __name__ == "__main__":
    env = gym.make(ENV_NAME)

    for e in range(1000):
        state = env.reset()
        for time in range(500):
            env.render()
            b = ACTION_MEANING.keys()[len(ACTION_MEANING)-1]
            a = ACTION_MEANING.keys()[0]
            action_int = int(((b+1) - a) * np.random.random_sample() + a)
            next_state, reward, done, _ = env.step(action_int)