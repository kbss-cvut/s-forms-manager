#!/bin/sh

# Start the RDF4J server
catalina.sh run &

# Wait for the server to start
sleep 5

# Copy the RDF4J data directory to the container
mkdir /var/rdf4j/server/repositories

# Copy the RDF4J data directory to the container if it does not exist
if [ ! -d "/var/rdf4j/server/repositories/s-forms-manager" ]; then
    echo "INFO: Copying RDF4J data directory to the container..."
    cp -r /temp/s-forms-manager /var/rdf4j/server/repositories
fi


# Keep the container running
tail -f /dev/null