/// <reference types="vitest" />
import { coverageConfigDefaults, defineConfig } from "vitest/config";
import react from "@vitejs/plugin-react";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  base: "/",
  test: {
    globals: true,
    environment: "jsdom",
    setupFiles: ["./src/config/vitest-setup.js"],
    coverage: {
      exclude: [
        "**/services/**",
        "**/*.css",
        "**/*.scss",
        "**/main.tsx",
        "**/App.tsx",
        "**/styling/theme.ts",
        ...coverageConfigDefaults.exclude,
      ],
    },
  },
});
