import Head from 'next/head';
import Link from 'next/link';
import {useState} from 'react';
import styles from '../styles/UserDashboard.module.css';
import ArtistBox from '../components/ArtistBox.js';
import PlaylistBox from '../components/PlaylistBox.js';
import Router from 'next/router'

export default function Dashboard({artists, user, playlists}) {
    const [visible, setVisible] = useState(false);
    const [playlistName, setPlaylistName] = useState(null);

    let handleClick = () => {
        setVisible(false);
        setPlaylistName(null);
        Router.reload(window.location.pathname);
    }

    return (
        <div className={styles.container}>
            <Head>
                <title>Welcome {user.displayName}</title>
                <link rel="icon" href="/icon.svg" />
            </Head>
            <main className={styles.main}>
                <h1>Howdy {user.displayName}!</h1>
                <div className={styles.buttons}>
                    <button className={styles.button} onClick={() => setVisible(true)}><p>Create a Playlist</p></button>
                    <Link href="/add-songs"><button className={styles.button}><p>Add songs to a Playlist</p></button></Link>
                </div>
                <div className={styles.boxes}>
                    <ArtistBox artists={artists}/>
                    <PlaylistBox playlists={playlists}/>
                </div>
                <div className={`${styles.modal} ${visible ? styles.open : ''}`}>
                    <div className={styles.modalBox}>
                        <div className={styles.modalContent}>
                            <div className={styles.navSearchContainer}>
                                <input id="id-input" type="text" placeholder="Enter Playlist Song Name Here" className={styles.searchInput} />
                                <button type="submit" className={styles.formButton} onClick={() => setPlaylistName(createPlaylist)}> Create </button>
                            </div>
                            <div className={`${styles.messageContainer} ${playlistName !== null ? styles.open : ''}`}>
                                <p>The playlist was created!</p>
                            </div>
                            <div className={styles.closeContainer}>
                                <button className={styles.close} onClick={handleClick}>Close</button>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
            <footer className={styles.footer}>
                <div className={styles.content}>
                    <p>Developed by Kayla Arbez</p>
                    <img src='/icon.svg' className={styles.logo}/>
                </div>
            </footer>
        </div>
    )
}

// this enables to pre-render this page and fetches data at request time
export async function getServerSideProps() {
    const [artists, user, playlists] = await Promise.all([
        fetch(`http://localhost:8080/spotify/user-top-artists`).then(r => r.json()),
        fetch(`http://localhost:8080/spotify/user`).then(r => r.json()),
        fetch(`http://localhost:8080/spotify/user-playlists`).then(r => r.json())
    ])
    return {props: {artists, user, playlists}}
}

function createPlaylist() {
    let result = "";
        let value = document.getElementById("id-input").value;
        console.log("Value = " + value)
        fetch("http://localhost:8080/spotify/create-playlist/" + value)
            .then((response) => response.text())
            .then( response => {
                result = response;
            })
    console.log("results :" + result)
    return result;
}





