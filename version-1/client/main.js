import * as htmlToImage from "html-to-image"
// import download from "downloadjs"

const canvasContainer = document.querySelector(".canvas-container");
const videoContainer = document.querySelector(".video-container");
const loading = document.querySelector(".loading");
const button = document.querySelector("button");

videoContainer.style.display = "none";
loading.style.display = "none";

const CANVAS_WIDTH = 640;
const CANVAS_HEIGHT = 320;

const canvas = document.querySelector('canvas');
canvas.width = CANVAS_WIDTH;
canvas.height = CANVAS_HEIGHT * 2;

// populate canvas
const ctx = canvas.getContext('2d');
const ctx2 = canvas.getContext('2d');

ctx.fillStyle = "rgb(0, 0, 0)"
ctx.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
ctx.font = "bold 2rem Verdana";
ctx.fillStyle = "White";
ctx.fillText("Text Goes Here", (CANVAS_WIDTH / 3.595), (CANVAS_HEIGHT / 1.895));

ctx2.fillStyle = "rgb(10, 10, 10)"
ctx2.fillRect(0, CANVAS_HEIGHT, CANVAS_WIDTH, CANVAS_HEIGHT);
ctx2.font = "bold 2rem Verdana";
ctx2.fillStyle = "White";
ctx.fillText("Text Goes Here", (CANVAS_WIDTH / 3.595), (CANVAS_HEIGHT * 1.525));

//generate image name
const generateName = () => {
    return window.crypto.randomUUID()
}

//download image
button.addEventListener("click", () => {
        htmlToImage.toPng(canvas).then(function (base64img) {
            const data = {
                imgName: `${generateName()}`,
                base64img
            }
            canvasContainer.style.display = "none";
            loading.style.display = "block";
            fetch("http://localhost:8080/api/v1/b64-image/create", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data),
            })
            .then(response => response.json())
            .then(data => {
                button.style.display = "none";
                console.log(data)
                if(data.status === "success") {
                    loading.style.display = "none";
                    videoContainer.style.display = "block";
                    alert("Image Downloaded Successfully")
                }else{
                    alert("failed")
                }
            })
            .catch((error) => {
                console.error('An Error Occurred: ', error);
            })
            // download(url, `${generateName()}.png`)
        })
    })
