import json
import os

directory = '/user/research/ptan/data/Twitter/'

testList = []
paths = []
keyword = 'tesla'
for root, dirs, files in os.walk(directory):
    for file in files:
        paths.append(directory+file)

paths
print(len(paths))

for i in range(0,100):
    print('file',i,'of',len(paths))
    with open(paths[i]) as f:
        for line in f:
            try:
                c = json.loads(line)
                # first condition takes care of if tesla is in tweet
                # second takes care of #tesla... or @tesla
                if 'text' in c and any([(word == keyword) or (word[1:6] == keyword) for word in str(c['text']).lower().split()]):
                    testList.append({i: c[i] for i in ('created_at', 'text')})
            except ValueError:
                pass
            except TypeError:
            	pass


a = json.dumps(testList)
with open('final.json', 'w') as outfile:
    json.dump(a, outfile)

