import Head from 'next/head';
import Link from 'next/link';
import {useState} from 'react';
import styles from '../styles/AddSongs.module.css';
import Router from 'next/router'

export default function Dashboard({user, playlists}) {

    const [songsAdded, setSongsAdded] = useState(null);

    let handleClick = () => {
        setSongsAdded(null);
        Router.reload(window.location.pathname);
    }

    return (
        <div className={styles.container}>
            <Head>
                <title>Welcome {user.displayName}</title>
                <link rel="icon" href="/icon.svg" />
            </Head>
            <h1>Add a Youtube playlist to your Spotify</h1>
            <main className={styles.main}>
                <div className={styles.inputContainer}>
                    <div className={styles.label}>
                        <label>Youtube URL</label>
                    </div>
                    <div className={styles.input}>
                        <input id="youtubeUrl" type="text" placeholder="Youtube playlist URL here" className={styles.searchInput} />
                        <p> * Insert the url from the Youtube playlist you want to receive songs from.</p>
                    </div>
                </div>
                <div className={styles.inputContainer}>
                    <div className={styles.label}>
                        <label>Spotify Playlist</label>
                    </div>
                    <div className={styles.input}>
                        <select id="spotifyPlaylist" className={styles.searchInput}>
                            {playlists.map((playlist) => (
                                <option value={playlist.name}>{playlist.name}</option>
                            ))}
                        </select>
                        <p> * Choose the Spotify playlist you want to insert the songs into.</p>
                    </div>
                </div>
                <div className={styles.buttonsContainer}>
                    <button type="submit" className={styles.submitButton} onClick={() => setSongsAdded(addPlaylist())}> Add Playlist Songs </button>
                    <Link href="/user-dashboard"><button type="Get " className={styles.backButton}> Back to Dashboard </button></Link>
                </div>
            </main>
            <div className={`${styles.modal} ${songsAdded !== null? styles.open : ''}`}>
                <div className={styles.modalBox}>
                    <div className={styles.modalContent}>
                        <p>Success! The songs were added.</p>
                        <p>Check your Spotify account either on web or your phone to see the results.</p>
                        <a href="https://open.spotify.com/" target="_blank"> > Spotify Web</a>
                        <div className={styles.closeContainer}>
                            <button className={styles.close} onClick={handleClick}>Close</button>
                        </div>
                    </div>
                </div>
            </div>
            <footer className={styles.footer}>
                <div className={styles.content}>
                    <p>Developed by Kayla Arbez</p>
                    <img src='/icon.png' className={styles.logo}/>
                </div>
            </footer>
        </div>
    )
}

// this enables to pre-render this page and fetches data at request time
export async function getServerSideProps() {
    const [user, playlists] = await Promise.all([
        fetch(`https://playlist-sync-backend.herokuapp.com/spotify/user`).then(r => r.json()),
        fetch(`https://playlist-sync-backend.herokuapp.com/spotify/user-playlists`).then(r => r.json())
    ])
    return {props: {user, playlists}}
}

// gets values from both inputs and fetches the backend function addSongs
function addPlaylist() {
    let result = "";
    let youtubeUrl = splitYoutubeUrl();
    let spotifyPlaylist = document.getElementById("spotifyPlaylist").value;
    if(youtubeUrl && spotifyPlaylist) {
        fetch("https://playlist-sync-backend.herokuapp.com/spotify/add-songs", {
            method: 'POST',
            body: JSON.stringify({spotifyPlaylist: spotifyPlaylist, youtubeUrl: youtubeUrl})})
            .then((response) => response.text())
            .then( response => {
                result = response;
            })
        console.log(result);
        return result;
    } else {
        alert("Make sure both fields are filled and the youtube URL is a playlist. It should contain a '&list='.");
        return null;
    }
}

// ensures youtubeURL has '&list=' and then filters the youtube URL to return the playlist ID only
function splitYoutubeUrl() {
    let youtubeUrl = document.getElementById("youtubeUrl").value;
    if (youtubeUrl.includes("&list=")) {
        let splitUrl = youtubeUrl.split("&list=")
        let result = splitUrl[1].split("&index")
        return result[0];
    } else {
        return null;
    }
}







