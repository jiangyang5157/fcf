from environment import Environment
from agent import Agent
 
def main():
    # https://gym.openai.com/envs/CartPole-v1/
    PROBLEM = 'CartPole-v1'
    env = Environment(PROBLEM)

    state_size  = env.observation_space.shape[0]
    action_size = env.action_space.n
    memory_capacity = 10000
    batch_size_max = 64
    print("Observation space shape: {}".format(state_size))
    print("Action space size: {}".format(action_size))

    agent = Agent(state_size, action_size, memory_capacity, batch_size_max)
    
    while True:
        env.run(agent)

    # agent.brain.load(PROBLEM + ".w")
    # try:
    #     while True:
    #         env.run(agent)
    # finally:
    #     agent.brain.save(PROBLEM + ".w")

if __name__ == "__main__":
    main()
