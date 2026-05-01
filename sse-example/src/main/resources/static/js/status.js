console.log("status js file loaded")


//take on job id
const jobId = 'job-101'


async  function openConnection(){

//open connection
    console.log("opening connection")
    const eventSource = new EventSource(`/api/v1/status/subscribe/${jobId}`)

//listen for messages

    eventSource.onmessage = (event) => {
        console.log(event.data)
        const status = document.getElementById("status");
        status.textContent = event.data;
        if(event.data==='COMPLETED'){
            eventSource.close()
            console.log("connection closed")
        }

    }



    eventSource.onerror = (error) => {
        console.log("connection closed")
        console.log(error)
    }
}

async function startJob(id) {
    const response = await fetch(`/api/v1/status/start/${id}`, {
        method: 'POST'
    })

    const data = await response.json()
    console.log(data.message)
    alert(data.message)

}

const connectButton= document.getElementById('connect_button')
connectButton.addEventListener('click', ()=>startJob(jobId))


const openConnectionButton= document.getElementById('openConnection')
openConnectionButton.addEventListener('click', openConnection)