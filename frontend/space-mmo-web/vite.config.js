import { defineConfig } from 'vite'
import crossOriginIsolation from 'vite-plugin-cross-origin-isolation'
import react from '@vitejs/plugin-react'
import fs from 'fs'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react(), crossOriginIsolation()
  ],


server:
{
    host: true,
    port: 3000,
    https:
    {
        key: fs.readFileSync('/etc/letsencrypt/live/winapimonitoring.com/privkey.pem'),
        cert: fs.readFileSync('/etc/letsencrypt/live/winapimonitoring.com/fullchain.pem')
    },
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

