import Head from 'next/head'
import Link from 'next/link'
import styles from '../styles/Home.module.css'

export default function Home() {
  return (
    <div className={styles.container}>
        <Head>
            <title>Welcome!</title>
            <link rel="icon" href="/icon.svg" />
        </Head>
      <main className={styles.main}>
          <h1>
              Welcome to my simple application.
          </h1>
          <h2>
              Please sign into spotify to sync your playlists.
          </h2>
          <button type="button" className={styles.button} onClick={getSpotifyUserLogin}><p>Sign into Spotify</p></button>
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

const getSpotifyUserLogin = () => {
    fetch("http://localhost:8080/api/login")
        .then((response) => response.text())
        .then( response => {
            window.location.replace(response);})
}

