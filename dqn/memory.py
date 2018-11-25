import random

class Memory:

    samples = []

    def __init__(self, capacity):
        self.capacity = capacity

    def add(self, sample):
        self.samples.append(sample)        

        if len(self.samples) > self.capacity:
            self.samples.pop(0)

    # Returns random batch of n samples
    def sample(self, n):
        n = min(n, len(self.samples))
        return random.sample(self.samples, n)
