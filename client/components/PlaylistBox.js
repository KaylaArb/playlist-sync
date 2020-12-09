import styles from '../styles/Box.module.css'
import React from 'react';
import Router from "next/router";

export default function PlaylistBox({playlists}) {
    return (
        <div className={styles.container}>
            <div className={styles.listContainer}>
                <h1>Current Playlists</h1>
                <ol className={styles.playlists}>
                    {playlists.map((playlist) => (
                        <li key={playlist.id}>
                            <p>
                                {playlist.name}
                                <button onClick={() => deletePlaylist(playlist.id)}>
                                    Delete
                                </button>
                            </p>
                        </li>
                    ))}
                </ol>
            </div>
        </div>
    );
}

function deletePlaylist(id) {
    let result = null;
    fetch("http://localhost:8080/spotify/delete-playlist/" + id)
            .then((response) => response.text())
            .then( response => {
                result = response;
                return (result !== null ? Router.reload(window.location.pathname) : alert("The playlist could not be deleted."));
            })
}

