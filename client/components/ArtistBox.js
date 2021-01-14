import styles from '../styles/Box.module.css'

export default function ArtistBox({artists}) {
    return (
        <div className={styles.container}>
            <div className={styles.listContainer}>
                <h1 className={styles.boxTitle}>Your Top 10 Spotify Artists</h1>
                <ol className={styles.artists}>
                    {artists.map((artist) => (
                        <li key={artist.id}>
                            <p>
                                {artist.name}
                            </p>
                        </li>
                    ))}
                </ol>
            </div>

        </div>
    );

}