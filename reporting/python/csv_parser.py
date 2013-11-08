
import csv
from measurement import *

class CsvParser:

    def __init__(self, file_name, delimiter=";"):
        f = open(file_name, "rb")
        self.reader = csv.reader(f, delimiter=delimiter)
        self.header = self.reader.next()

    def __parse_header(self, q_names):
        header_len = len(self.header)
        q_pos = []
        for q_name in q_names:
            for col_num in range(0,header_len):
                if(self.header[col_num] == q_name):
                    q_pos.append(col_num)
                    break
        return q_pos
        

    def getMeasurementSet(self, *q_names):
        measurements = []
        for q in q_names:
            measurements.append(Measurement(q))

        q_pos = self.__parse_header(q_names)
        # TODO assert that len(q_pos) == len(measurements)

        for row in self.reader:
            i = 0
            for pos in q_pos:
                # print "quantity, value: %s, %s" % (q_names[i],row[pos])
                measurements[i].add(row[pos])
                i += 1
        return measurements

    def close(self):
        self.reader.close()


        

