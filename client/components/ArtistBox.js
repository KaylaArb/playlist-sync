import styles from '../styles/Box.module.css'

export default function ArtistBox({artists}) {
    return (
        <div className={styles.container}>
            <div className={styles.listContainer}>
                <h1>Top 10 Artists</h1>
                <ol>
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