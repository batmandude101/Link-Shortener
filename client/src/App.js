import React, { useState } from 'react';
import axios from 'axios';
import './Styles/urlShortenForm.css';

var UrlShortener = function () {
  const [longUrl, setLongUrl] = useState('');
  const [shortUrl, setShortUrl] = useState({ url: '', expired: false });

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await axios.post('/api/shortenLink', { longUrl });
      setShortUrl({ url: response.data, expired: false });
    } catch (error) {
      console.error(error);
    }
  };

  const handleChange = (event) => {
    setLongUrl(event.target.value);
  };

  function handleCopyToClipboard() {
    navigator.clipboard.writeText(shortUrl.url);
  }

  const handleClick = async () => {
    var response = "";
    try {
      response = await axios.post('/api/redirect', { url: shortUrl.url });
    } catch (error) {
      console.error(error);
    }
    if (response.data === "Expired") {
      setShortUrl({ url: shortUrl.url, expired: true });
    } else {
      window.open(response.data, '_blank');
    }

  };


  return (
    <body class="page">
    <div class="container">
      <h1>URL Shortener</h1>
      <form onSubmit={handleSubmit}>
        <label>
          <input class="input-box" type="text" placeholder="Enter a URL..." value={longUrl} onChange={handleChange} />
        </label>
        <br />
        <button class="button" type="submit">Shorten</button>
        {
        <div class="shortened-url">
          {shortUrl.expired ? (
            <p>{shortUrl.url}... has expired, Please enter the fresh original URL</p>
          ) : shortUrl.url && (
            <p>
              Click <a href="#" onClick={handleClick}>here</a> to go to {shortUrl.url}.
            </p>
          )}
          <button class="copy-button" onClick={() => handleCopyToClipboard()}>Copy to Clipboard</button>
        </div>
      }
      </form>
      
    </div>
    </body>
  );

}

export default UrlShortener;
