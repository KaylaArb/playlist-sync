import Head from 'next/head'
import styles from '../styles/Home.module.css'

export default function playlists({data}) {

    return (
        <div className={styles.container}>
            <Head>
                <title>Create Next App</title>
                <link rel="icon" href="/favicon.ico" />
            </Head>
            <main className={styles.main}>
                {/*{data.map((playlist) => (*/}
                {/*        <a>*/}
                {/*            <article key={playlist.id}>*/}
                {/*                {playlist.name}*/}
                {/*            </article>*/}
                {/*        </a>*/}
                {/*))}*/}
                <h1>{data}</h1>
            </main>
            <footer className={styles.footer}>
                Developed by Kayla Arbez
            </footer>
        </div>
    )
}

// this enables the page to pre-render this page and fetches data at request time
export async function getServerSideProps() {
    const res = await fetch(`http://localhost:8080/api/user-playlist/example2`)
    const data = await res.json();

    return {props: {data}}
}

