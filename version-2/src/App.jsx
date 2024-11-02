import { useEffect, useState } from "react";
import {ScreenSizeWarning, CreditsBuilder} from "./components/index.js"
function App() {
    const minScreenWidth = 1500;
    const [isScreenSupported, setIsScreenSupported] = useState(window.innerWidth > minScreenWidth);

    useEffect(() => {
        const handleResize = () => {
            setIsScreenSupported(window.innerWidth > minScreenWidth);
        };
        handleResize();
        window.addEventListener("resize", handleResize);
        return () => {
            window.removeEventListener("resize", handleResize);
        };
    }, []);

    return (
        isScreenSupported ? (
            <CreditsBuilder/>
        ) : (
            <ScreenSizeWarning />
        )
    );
}

export default App;
