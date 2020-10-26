names={}

with open("female-names.txt") as r:
    for line in r:
        if line[0].lower() not in names:
            names[line[0].lower()]=0
        else:
            names[line[0].lower()]+=1

print(names)      

with open("initials4redis.txt","w") as writer:
    for key, value in names.items():
        str="SET {} {} \n".format(key,value)
        #print(str)
        writer.write(f" SET {key} {value}\n")
