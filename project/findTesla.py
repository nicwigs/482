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

for i in range(0,1000):
    print('file',i,'of',len(paths))
    with open(paths[i]) as f:
        for line in f:
            try:
                c = json.loads(line)
                if 'text' in c and keyword in str(c['text']).lower():
                    testList.append({i: c[i] for i in ('created_at', 'text')})
            except ValueError:
                pass
            except TypeError:
            	pass


a = json.dumps(testList)
with open('final.json', 'w') as outfile:
    json.dump(a, outfile)

