import { blockSong, createPlaylist } from "../api/backendApi";
import { useContext, useState } from "react";
import { AuthContext } from "../App";
import { BlockArtistIcon, BlockSongIcon, RemoveSongIcon } from "./Icons";

export const Tracklist = ({ tracklist }) => {
  const [name, setName] = useState("New playlist");
  const auth = useContext(AuthContext);
  const tracks = tracklist?.tracks;

  const trackList = tracks?.map((track, i) => {
    const duration = new Date(track.duration_ms);
    return (
      <tr key={track.id}>
        <td>{i + 1}</td>
        <td>
          <img src={track.album?.url}></img>
        </td>
        <td className="track-artist-name">
          <div className="track-name">{track.name}</div>{" "}
          <div className="artist-name">{track.artists?.map((a) => a.name)}</div>
        </td>
        <td className="album-name">{track.album?.name}</td>
        <td>
          {duration.getMinutes()}:
          {String(duration.getSeconds()).padStart(2, "0")}
        </td>
        <td>
          {/* <button onClick={() => blockSong(auth, track.id)}>block song</button> */}
          <RemoveSongIcon onClick={() => {}} />
          <BlockSongIcon />
          <BlockArtistIcon />
        </td>
      </tr>
    );
  });
  return (
    <>
      <div className="tracklist">
        <table>
          <thead>
            <tr>
              <th>#</th>
              <th></th>
              <th>Title</th>
              <th>Album</th>
              <th>Duration</th>
            </tr>
          </thead>
          <tbody>{trackList}</tbody>
        </table>
      </div>

      <div className="playlist-name">
        Playlist name:{" "}
        <input value={name} onChange={(e) => setName(e.target.value)} />
      </div>

      <div className="export-button">
        <button
          onClick={() =>
            createPlaylist(
              auth,
              name,
              "Generated by Threemix",
              tracks?.map((track) => track.id)
            )
          }
        >
          Export to Spotify
        </button>
      </div>
    </>
  );
};
