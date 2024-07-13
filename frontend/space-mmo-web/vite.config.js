import { defineConfig } from 'vite'
import crossOriginIsolation from 'vite-plugin-cross-origin-isolation'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react(), crossOriginIsolation()],
  server:
  {
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
            target: 'http://localhost:8080',
            changeOrigin: true,
            secure: false,
            ws: true
        }
    }
  }
})
