import { CreditsControl, CreditsScreen } from "."

const CreditsBuilder = () => {
    return (
    <div className="h-screen min-w-[1500px] bg-gray-950 flex flex-row items-center justify-center p-8 gap-8">
        <CreditsControl/>
        <CreditsScreen/>
    </div>
    )
}

export default CreditsBuilder;