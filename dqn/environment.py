import gym

class Environment:
    
    def __init__(self, problem):
        self.problem = problem
        self.env = gym.make(problem)

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
