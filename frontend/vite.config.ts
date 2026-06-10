import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import { VitePWA } from 'vite-plugin-pwa'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    react(),
    VitePWA({
      registerType: 'autoUpdate',   // atualiza o SW automaticamente
      manifest: {
        name: 'PassGen',
        short_name: 'PassGen',
        description: 'Gerador de senhas seguras',
        start_url: '/',
        display: 'standalone',
        background_color: '#0D1117',
        theme_color: '#0D1117',
        icons: [
          { src: '/icons/icon-192.png', sizes: '192x192', type: 'image/png' },
          { src: '/icons/icon-512.png', sizes: '512x512', type: 'image/png' }
        ]
      },
      workbox: {
        globPatterns: ['**/*.{js,css,html,ico,png,svg}'], // arquivos cacheados offline
      }
    })
  ],
})
