import {useState} from "react";

export default function TopArtists() {

    const [userTopArtists, setUserTopArtists] = useState();

    useEffect(() => {
        fetch("http://localhost:8080/api/user-top-artists")
            .then(response => response.json())
            .then(data => {
                console.log(data)
                setUserTopArtists(data)
            })
    })

    return (
        <div>
            {userTopArtists ? (
                userTopArtists.map((artist) => {
                    return <h1 key={artist.name}>{artist.name}</h1>
                })
            ): (
                <h1>loading....</h1>
            )}

        </div>
    );

}