import json
print("This is python json operations")

jsonUser="""
{
    "name":"Durgesh",
    "phone":"9839466732",
    "skills":["Java","Python"],
    "address":{
        "city":"LKO",
        "pincode":2455
    }

}
"""

# print(jsonUser)
# print(type(jsonUser))

userDict=json.loads(jsonUser)
print(userDict)
print(type(userDict))
print(userDict['name'])
print(userDict['phone'])
print(userDict['address']['city'])

todo={
    'title':"learn python core for ai",
    'isCompleted':False
}

jsonTodo=json.dumps(todo)
print(type(jsonTodo))
print(jsonTodo)