#!/bin/bash

# Exit on any error
set -e

echo "Starting up..."
echo "Building and starting containers..."
docker compose up --build -d

echo "All services are up and running!"
echo "To view logs, run: docker compose logs -f"
echo "To stop the services, run: docker compose down"
