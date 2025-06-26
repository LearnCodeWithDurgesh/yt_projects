console.log("This is my json operations");
const json = `{
    "name":"Durgesh",
    "phone":"9839466732",
    "skills":["Java","Python"],
    "address":{
        "city":"LKO",
        "pincode":2455
    }

}`;

//parse this json to perform operations
const user = JSON.parse(json);

console.log(user.name);
console.log(user.phone);
console.log(user.skills);
console.log(user.address.city);

const todo = {
  title: "learn django",
  isCompleted: true,
};

//convert js object to json string.

const jsonTodo = JSON.stringify(todo);
console.log(jsonTodo);
