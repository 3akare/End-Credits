import {useEffect, useState} from "react";

const ScreenSizeWarning = () => {
    const [screenSize, setScreenSize] = useState(0);
    useEffect(() => {
        window.onload = () =>{
            setScreenSize(window.innerWidth)
        }
        window.onresize = () => {
            setScreenSize(window.innerWidth)
        }
    })
    return (
        <div className="h-screen w-screen bg-gray-950 flex flex-col gap-4 justify-center items-center text-white text-center">
            <img src="./screen-size-warning.svg" alt="Screen too small warning" className="w-32 h-32"/>
            <p className="text-4xl w-96">Screen too small. Please switch to a larger screen to use this application (min
                1500).</p>
            <p className={"text-white absolute text-2xl top-0"}>{screenSize}</p>
        </div>
    )
};

export default ScreenSizeWarning;
