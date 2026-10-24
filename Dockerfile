FROM mcr.microsoft.com/playwright:v1.60.0-noble

WORKDIR /automation-hbi

RUN npm install --global pnpm@11.24.0

COPY package.json pnpm-lock.yaml pnpm-workspace.yaml .npmrc ./
COPY mcp-xray/package.json ./mcp-xray/package.json
RUN pnpm install --frozen-lockfile

COPY . .

ENTRYPOINT ["pnpm", "exec", "playwright", "test"]
