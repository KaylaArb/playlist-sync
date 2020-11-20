import Head from 'next/head'
import Link from 'next/link'
import styles from '../styles/Home.module.css'

export default function Home() {
  return (
    <div className={styles.container}>
      <Head>
        <title>Create Next App</title>
        <link rel="icon" href="/favicon.ico" />
      </Head>
      <main className={styles.main}>
        <h1 className={styles.title}>
          Welcome to my simple application. Please sign into spotify
        </h1>
        <button type="button" className={styles.button} onClick={getSpotifyUserLogin}> Sign In </button>
      </main>
      <footer className={styles.footer}>
          Developed by Kayla Arbez
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

