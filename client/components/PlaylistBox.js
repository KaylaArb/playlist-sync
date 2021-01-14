import styles from '../styles/Box.module.css'
import React from 'react';
import Router from "next/router";

export default function PlaylistBox({playlists}) {
    return (
        <div className={styles.container}>
            <div className={styles.listContainer}>
                <h1 className={styles.boxTitle}>Your Current Playlists</h1>
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

// call the Delete-playlist endpoint
function deletePlaylist(id) {
    let result = null;
    fetch("https://playlist-sync-backend.herokuapp.com/spotify/delete-playlist", {
        method: 'DELETE',
        headers: {'Content-Type': 'text/plain'},
        body: id})
        .then((response) => response.text())
        .then( response => {
            result = response;
            return (result !== null ? Router.reload(window.location.pathname) : alert("The playlist could not be deleted."));
        })
}


