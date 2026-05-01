console.log("This is script: page loaded")

function openConnection(){

    const eventSource= new EventSource("/api/v1/stream");

    eventSource.onmessage=(event)=>{
        console.log(event.data)
    }
}


const button = document.getElementById("connect_button");
button.addEventListener("click", openConnection);
