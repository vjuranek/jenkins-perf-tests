
from numpy import average

class MeasurementSet:
    
    def __init__(self, measurements):
        self.measurements = measurements

    def getMeasurements(self):
        return self.measurements

class Measurement:
    
    def __init__(self, quantity, values=[], units=""):
        self.quantity = quantity
        self.values = values
        self.units = units

    def add(self, value):
        self.values.append(value)

    def avg(self):
        return average(self.values, axis=0)



        
