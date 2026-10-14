FROM mcr.microsoft.com/playwright:v1.60.0-noble

WORKDIR /automation-hbi

RUN npm install --global pnpm@11.19.0

COPY package.json pnpm-lock.yaml .npmrc ./
RUN pnpm install --frozen-lockfile

COPY . .

ENTRYPOINT ["pnpm", "exec", "playwright", "test"]
