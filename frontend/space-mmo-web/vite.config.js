import { defineConfig } from 'vite'
import crossOriginIsolation from 'vite-plugin-cross-origin-isolation'
import react from '@vitejs/plugin-react'
import fs from 'fs'
import mkcert from 'vite-plugin-mkcert'
// https://vitejs.dev/config/
var debug = true

function getCert()
{
    if(debug == true)
    {
        return true
    }
    else
    {
        return {
            key: fs.readFileSync('/etc/letsencrypt/live/winapimonitoring.com/privkey.pem'),
            cert: fs.readFileSync('/etc/letsencrypt/live/winapimonitoring.com/fullchain.pem')
        }
    }
}

export default defineConfig({
  plugins: [react(), crossOriginIsolation(), mkcert()
  ],



server:
{
    host: true,
    port: 3000,
    https: getCert() ,
    proxy:
    {
        '/api':
        {
            target: 'http://localhost:8080',
            changeOrigin: true,
            secure: false,
            ws: true
        },
        '/public':
        {
            target: 'http://localhost:8080',
            changeOrigin: true,
            secure: false,
            ws: true
        },
        '/openGameSession':
        {
            target: 'ws://localhost:8080',
            changeOrigin: false,
            ws: true,
            secure: true,

                configure: (proxy, _options) => {
            proxy.on('error', (err, _req, _res) => {
              console.log('proxy error', err);
            });
            proxy.on('proxyReq', (proxyReq, req, _res) => {
              console.log('Sending Request to the Target:', req.method, req.url);
            });
            proxy.on('proxyRes', (proxyRes, req, _res) => {
              console.log('Received Response from the Target:', proxyRes.statusCode, req.url);
            });
          }
        }
    }
  }
})

