import styles from '../styles/Box.module.css'

export default function PlaylistBox({playlists}) {
    return (
        <div className={styles.container}>
            <div className={styles.listContainer}>
                <h1>Current Playlists</h1>
                <ol>
                    {playlists.map((playlist) => (
                        <li key={playlist.id}>
                            <p>
                                {playlist.name}
                            </p>
                        </li>
                    ))}
                </ol>
            </div>
        </div>
    );
}