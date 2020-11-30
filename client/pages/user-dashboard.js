import Head from 'next/head';
import styles from '../styles/UserDashboard.module.css';
import ArtistBox from '../components/ArtistBox.js';
import PlaylistBox from '../components/PlaylistBox'

export default function Dashboard({artists, user, playlists}) {

    return (
        <div className={styles.container}>
            <Head>
                <title>Welcome {user.displayName}</title>
                <link rel="icon" href="/icon.svg" />
            </Head>
            <main className={styles.main}>
                <h1>Howdy {user.displayName}!</h1>
                <div className={styles.buttons}>
                    <button className={styles.button}><p>Create a Playlist</p></button>
                    <button className={styles.button}><p>Add songs to a Playlist</p></button>
                </div>
                <div className={styles.boxes}>
                    <ArtistBox artists={artists}/>
                    <PlaylistBox playlists={playlists}/>
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

