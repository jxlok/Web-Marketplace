# AI Model Marketplace - Pawn
Web Development Group Project

## Local Development

### How to Run Application with Docker Compose

Run following command at project root:

1. Build application as Jar file
    ```bash
    mvn clean install
    ```

2. Run docker-compose
    ```bash
    docker-compose up --build
    ```
   if you want to run docker-compose in detach mode (no log shown on console)
   ```bash
    docker-compose up --build -d
    ```

3. Shutdown app
    ```bash
    docker-compose down --remove-orphans
    ```