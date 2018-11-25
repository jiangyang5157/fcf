from environment import Environment
from agent import Agent
 
def main():
    # PROBLEM = 'Breakout-v0'
    PROBLEM = 'CartPole-v0'
    env = Environment(PROBLEM)

    state_size  = env.env.observation_space.shape[0]
    action_size = env.env.action_space.n
    agent = Agent(state_size, action_size)
    
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